package ru.popov.bodya.data.database.transactions.converters.entities

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import ru.popov.bodya.domain.currency.model.Currency

/**
 * @author popovbodya
 */
class CurrencyTypeConverterTest {

    private lateinit var currencyTypeConverter: CurrencyTypeConverter

    @Before
    fun setUp() {
        currencyTypeConverter = CurrencyTypeConverter()
    }

    @Test
    fun testFromType() {
        assertThat(currencyTypeConverter.fromType(Currency.RUB), `is`("RUB"))
        assertThat(currencyTypeConverter.fromType(Currency.EUR), `is`("EUR"))
        assertThat(currencyTypeConverter.fromType(Currency.USD), `is`("USD"))
    }

    @Test
    fun testToCurrency() {
        assertThat(currencyTypeConverter.toCurrency("RUB"), `is`(Currency.RUB))
        assertThat(currencyTypeConverter.toCurrency("EUR"), `is`(Currency.EUR))
        assertThat(currencyTypeConverter.toCurrency("USD"), `is`(Currency.USD))
    }

}