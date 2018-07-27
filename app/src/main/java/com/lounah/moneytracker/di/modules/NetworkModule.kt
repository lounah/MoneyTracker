package com.lounah.moneytracker.di.modules

import com.lounah.moneytracker.data.datasource.remote.CurrencyApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    companion object {
        private const val API_URL = "https://free.currencyconverterapi.com/api/"
    }

    @Provides
    fun provideApi(): CurrencyApi {
        return Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(CurrencyApi::class.java)
    }
}