package ru.popov.bodya.data.database.currencies.converters.models

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import ru.popov.bodya.data.database.currencies.entities.ExchangeRatesEntity
import ru.popov.bodya.data.database.currencies.entities.RatesEntity
import ru.popov.bodya.domain.currency.model.ExchangeRates
import ru.popov.bodya.domain.currency.model.Rates

/**
 *  @author popovbodya
 */
class ExchangeRatesEntityConverterTest {

    private companion object {
        const val ID = 1
        const val USD_RATE = 0.015789
        const val EUR_RATE = 0.014321
        const val TIMESTAMP = 123456789L
        const val BASE = "RUB"
        const val DATE = "06.08.2018"
    }

    private lateinit var exchangeRatesEntityConverter: ExchangeRatesEntityConverter

    @Before
    fun setUp() {
        exchangeRatesEntityConverter = ExchangeRatesEntityConverter(RatesEntityConverter())
    }

    @Test
    fun testConvert() {
        val expected = createExchangeRates()
        val actual = exchangeRatesEntityConverter.convert(createExchangeRatesEntity())
        assertThat(actual, `is`(expected))

    }

    private fun createExchangeRatesEntity() = ExchangeRatesEntity(ID, TIMESTAMP, BASE, DATE, createRatesEntity())
    private fun createRatesEntity() = RatesEntity(USD_RATE, EUR_RATE)
    private fun createExchangeRates() = ExchangeRates(TIMESTAMP, BASE, DATE, createRates())
    private fun createRates() = Rates(USD_RATE, EUR_RATE)
}