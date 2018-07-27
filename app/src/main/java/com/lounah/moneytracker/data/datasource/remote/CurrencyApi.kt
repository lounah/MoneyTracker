package com.lounah.moneytracker.data.datasource.remote

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("v6/convert?compact=ultra")
    fun getExchangeRate(@Query("q") query: String): Call<ResponseBody>
}