package ru.popov.bodya.data.database.currencies.converters.models

import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import ru.popov.bodya.data.network.beans.RatesBean
import ru.popov.bodya.domain.currency.model.Rates

/**
 *  @author popovbodya
 */
class RatesBeanConverterTest {

    private companion object {
        const val USD_RATE = 0.015789
        const val EUR_RATE = 0.014321
    }

    private lateinit var ratesBeanConverter: RatesBeanConverter

    @Before
    fun setUp() {
        ratesBeanConverter = RatesBeanConverter()
    }

    @Test
    fun testConvert() {
        val expected = createRates()
        val actual = ratesBeanConverter.convert(createRatesBean())
        assertThat(actual, `is`(expected))
    }


    private fun createRatesBean() = RatesBean(USD_RATE, EUR_RATE)
    private fun createRates() = Rates(USD_RATE, EUR_RATE)

}