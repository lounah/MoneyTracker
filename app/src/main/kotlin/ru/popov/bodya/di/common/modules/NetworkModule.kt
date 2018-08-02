package ru.popov.bodya.di.common.modules

import com.lounah.moneytracker.data.datasource.remote.CurrencyApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    companion object {
        private const val API_URL = "https://free.currencyconverterapi.com/api/"
    }

    val okhttpclient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

    @Provides
    fun provideApi(): CurrencyApi {
        return Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .callFactory(okhttpclient.newBuilder().build())
                .build()
                .create(CurrencyApi::class.java)
    }
}