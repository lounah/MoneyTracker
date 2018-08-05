package ru.popov.bodya.data.database.currencies.converters.beans

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.popov.bodya.data.database.currencies.entities.RatesEntity
import ru.popov.bodya.data.network.beans.RatesBean

/**
 * @author popovbodya
 */
class RatesEntityBeanConverterTest {

    private companion object {
        const val USD_RATE = 0.015789
        const val EUR_RATE = 0.014321
    }

    private lateinit var ratesEntityBeanConverter: RatesEntityBeanConverter

    @Before
    fun setUp() {
        ratesEntityBeanConverter = RatesEntityBeanConverter()
    }

    @Test
    fun testConvert() {
        val expected = createRatesEntity()
        val actual = ratesEntityBeanConverter.convert(createRatesBean())
        assertThat(actual, `is`(expected))
    }

    @Test
    fun testReverse() {
        val expected = createRatesBean()
        val actual = ratesEntityBeanConverter.reverse(createRatesEntity())
        assertThat(actual, `is`(expected))
    }

    private fun createRatesBean() = RatesBean(USD_RATE, EUR_RATE)
    private fun createRatesEntity() = RatesEntity(USD_RATE, EUR_RATE)
}


