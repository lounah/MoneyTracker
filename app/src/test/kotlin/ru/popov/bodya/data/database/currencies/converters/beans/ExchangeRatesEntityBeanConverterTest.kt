package ru.popov.bodya.data.database.currencies.converters.beans

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import ru.popov.bodya.data.database.currencies.entities.ExchangeRatesEntity
import ru.popov.bodya.data.database.currencies.entities.RatesEntity
import ru.popov.bodya.data.network.beans.ExchangeRatesBean
import ru.popov.bodya.data.network.beans.RatesBean

/**
 *  @author popovbodya
 */
class ExchangeRatesEntityBeanConverterTest {

    private companion object {
        const val ID = 1
        const val USD_RATE = 0.015789
        const val EUR_RATE = 0.014321
        const val TIMESTAMP = 123456789L
        const val BASE = "RUB"
        const val DATE = "06.08.2018"
    }

    private lateinit var exchangeRatesEntityBeanConverter: ExchangeRatesEntityBeanConverter

    @Before
    fun setUp() {
        exchangeRatesEntityBeanConverter = ExchangeRatesEntityBeanConverter(RatesEntityBeanConverter())
    }

    @Test
    fun testConvert() {
        val expected = createExchangeRatesEntity()
        val actual = exchangeRatesEntityBeanConverter.convert(createExchangeRatesBean())
        assertThat(actual, `is`(expected))
    }

    @Test
    fun testReverse() {
        val expected = createExchangeRatesBean()
        val actual = exchangeRatesEntityBeanConverter.reverse(createExchangeRatesEntity())
        assertThat(actual, `is`(expected))
    }

    private fun createExchangeRatesEntity() = ExchangeRatesEntity(ID, TIMESTAMP, BASE, DATE, createRatesEntity())
    private fun createExchangeRatesBean() = ExchangeRatesBean(true, TIMESTAMP, BASE, DATE, createRatesBean())
    private fun createRatesBean() = RatesBean(USD_RATE, EUR_RATE)
    private fun createRatesEntity() = RatesEntity(USD_RATE, EUR_RATE)
}