package ru.popov.bodya.domain.currency

import io.reactivex.Single
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import ru.popov.bodya.data.repositories.CurrenciesRepository
import ru.popov.bodya.domain.currency.model.ExchangeRates
import ru.popov.bodya.domain.currency.model.Rates

/**
 * @author popovbodya
 */
class CurrencyInteractorTest {

    private companion object {
        const val USD_RATE = 0.015789
        const val EUR_RATE = 0.014321
        const val TIMESTAMP = 123456789L
        const val BASE = "RUB"
        const val DATE = "06.08.2018"
    }

    private lateinit var currencyInteractor: CurrencyInteractor
    private lateinit var currenciesRepository: CurrenciesRepository

    @Before
    fun setUp() {
        currenciesRepository = mock(CurrenciesRepository::class.java)
        currencyInteractor = CurrencyInteractor(currenciesRepository)
    }

    @Test
    fun testGetExchangeRate() {
        val expected = createExchangeRates()
        `when`(currenciesRepository.getExchangeRate()).thenReturn(Single.just(expected))
        currencyInteractor.getExchangeRate()
                .test()
                .assertValue(expected)
        verify(currenciesRepository).getExchangeRate()
        verifyNoMoreInteractions(currenciesRepository)
    }

    @Test
    fun testGetCachedExchangeRate() {
        val expected = createExchangeRates()
        `when`(currenciesRepository.getCachedExchangeRate()).thenReturn(Single.just(expected))
        currencyInteractor.getCachedExchangeRate()
                .test()
                .assertValue(expected)
        verify(currenciesRepository).getCachedExchangeRate()
        verifyNoMoreInteractions(currenciesRepository)
    }

    @Test
    fun testGetUsdRate() {
        val exchangeRates = createExchangeRates()
        val expected = 1 / exchangeRates.rates.usd
        val actual = currencyInteractor.getUsdRate(exchangeRates)
        assertThat(actual, `is`(expected))
    }

    @Test
    fun testGetEurRate() {
        val exchangeRates = createExchangeRates()
        val expected = 1/ exchangeRates.rates.eur
        val actual  = currencyInteractor.getEurRate(exchangeRates)
        assertThat(actual, `is`(expected))
    }

    private fun createExchangeRates() = ExchangeRates(TIMESTAMP, BASE, DATE, createRates())
    private fun createRates() = Rates(USD_RATE, EUR_RATE)
}