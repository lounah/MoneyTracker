package ru.popov.bodya.data.database.currencies.converters.entities

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import ru.popov.bodya.data.database.currencies.entities.RatesEntity

/**
 *  @author popovbodya
 */
class RatesTypeConverterTest {

    private companion object {
        const val USD_RATE = 0.015789
        const val EUR_RATE = 0.014321
    }

    private lateinit var ratesTypeConverter: RatesTypeConverter

    @Before
    fun setUp() {
        ratesTypeConverter = RatesTypeConverter()
    }

    @Test
    fun testFromType() {
        val expected = "$USD_RATE:$EUR_RATE"
        val actual = ratesTypeConverter.fromType(createRatesEntity())
        assertThat(actual, `is`(expected))
    }

    @Test
    fun testToRates() {
        val expected = createRatesEntity()
        val actual = ratesTypeConverter.toRates("$USD_RATE:$EUR_RATE")
        assertThat(actual, `is`(expected))
    }

    private fun createRatesEntity() = RatesEntity(USD_RATE, EUR_RATE)

}