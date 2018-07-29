package com.lounah.moneytracker.data.datasource.remote

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


// {"USD_RUB":{"val":323.43}}

interface CurrencyApi {

    @GET("v6/convert?compact=ultra")
    fun getExchangeRate(@Query("q") query: String): Call<ResponseBody>
}