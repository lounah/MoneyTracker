package ru.popov.bodya.data.database.currencies.converters.models

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import ru.popov.bodya.data.database.currencies.entities.RatesEntity
import ru.popov.bodya.domain.currency.model.Rates

/**
 *  @author popovbodya
 */
class RatesEntityConverterTest {

    private companion object {
        const val USD_RATE = 0.015789
        const val EUR_RATE = 0.014321
    }

    private lateinit var ratesEntityConverter: RatesEntityConverter

    @Before
    fun setUp() {
        ratesEntityConverter = RatesEntityConverter()
    }

    @Test
    fun testConvert() {
        val expected = createRates()
        val actual = ratesEntityConverter.convert(createRatesEntity())
        assertThat(actual, `is`(expected))
    }

    private fun createRatesEntity() = RatesEntity(USD_RATE, EUR_RATE)
    private fun createRates() = Rates(USD_RATE, EUR_RATE)
}