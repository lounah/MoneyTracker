package ru.popov.bodya.di.common.modules

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.popov.bodya.data.network.api.CurrenciesRateApi
import ru.popov.bodya.data.network.api.ExchangeRateApiWrapper
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideExchangeRateApiWrapper(currenciesRateApi: CurrenciesRateApi): ExchangeRateApiWrapper =
            ExchangeRateApiWrapper(currenciesRateApi)

    @Singleton
    @Provides
    fun provideExchangeRateApi(okHttpClient: OkHttpClient,
                               converterFactory: Converter.Factory,
                               adapterFactory: CallAdapter.Factory): CurrenciesRateApi =
            Retrofit.Builder()
                    .baseUrl(CurrenciesRateApi.FIXER_BASE_URL)
                    .addConverterFactory(converterFactory)
                    .addCallAdapterFactory(adapterFactory)
                    .client(okHttpClient)
                    .build()
                    .create(CurrenciesRateApi::class.java)

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Singleton
    @Provides
    fun provideCallAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

    @Singleton
    @Provides
    internal fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }
}