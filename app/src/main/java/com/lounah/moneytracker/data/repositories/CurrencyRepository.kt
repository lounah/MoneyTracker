package com.lounah.moneytracker.data.repositories

import com.lounah.moneytracker.data.datasource.remote.CurrencyApi
import javax.inject.Inject

class CurrencyRepository @Inject constructor(private val api: CurrencyApi) {
    fun getExchangeRate(from: String, to: String) = api.getExchangeRate("${from}_$to")
}