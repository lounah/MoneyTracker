package ru.popov.bodya.domain.currency.model

/**
 * @author popovbodya
 */
data class ExchangeRates(val timestamp: Long, val base: String, val date: String, val rates: Rates)
