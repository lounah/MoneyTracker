package com.lounah.moneytracker.data.datasource.remote

import io.reactivex.Single

interface CurrencyApi {
    fun getExchangeRate(from: String, to: String): Single<Double>
}