package ru.popov.bodya.data.database.currencies.converters.models

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import ru.popov.bodya.data.network.beans.ExchangeRatesBean
import ru.popov.bodya.data.network.beans.RatesBean
import ru.popov.bodya.domain.currency.model.ExchangeRates
import ru.popov.bodya.domain.currency.model.Rates

/**
 *  @author popovbodya
 */
class ExchangeRatesBeanConverterTest {

    private companion object {
        const val ID = 1
        const val USD_RATE = 0.015789
        const val EUR_RATE = 0.014321
        const val TIMESTAMP = 123456789L
        const val BASE = "RUB"
        const val DATE = "06.08.2018"
    }

    private lateinit var exchangeRatesBeanConverter: ExchangeRatesBeanConverter

    @Before
    fun setUp() {
        exchangeRatesBeanConverter = ExchangeRatesBeanConverter(RatesBeanConverter())
    }

    @Test
    fun testConvert() {
        val expected = createExchangeRates()
        val actual = exchangeRatesBeanConverter.convert(createExchangeRatesBean())
        MatcherAssert.assertThat(actual, CoreMatchers.`is`(expected))

    }

    private fun createExchangeRatesBean() = ExchangeRatesBean(true, TIMESTAMP, BASE, DATE, createRatesBean())
    private fun createRatesBean() = RatesBean(USD_RATE, EUR_RATE)
    private fun createExchangeRates() = ExchangeRates(TIMESTAMP, BASE, DATE, createRates())
    private fun createRates() = Rates(USD_RATE, EUR_RATE)
}