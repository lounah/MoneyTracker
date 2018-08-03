package com.lounah.moneytracker.ui.wallet

import android.arch.lifecycle.MutableLiveData
import com.lounah.moneytracker.data.entities.Resource
import ru.popov.bodya.core.mvwhatever.AppViewModel
import ru.popov.bodya.core.rx.RxSchedulersTransformer
import ru.popov.bodya.domain.currency.CurrencyInteractor
import javax.inject.Inject

class WalletViewModel @Inject constructor(
        private val currencyInteractor: CurrencyInteractor,
        private val rxSchedulersTransformer: RxSchedulersTransformer) : AppViewModel() {

//    val currentBalance: LiveData<Resource<List<Wallet>>> = getWallets()
    //    val transactions: LiveData<List<Transaction>> = fetchTransactions()
    val firstFieldExchangeRate = MutableLiveData<Resource<Double>>()
    val secondFieldExchangeRate = MutableLiveData<Resource<Double>>()


    fun fetchFirstFieldExchangeRate() {

        currencyInteractor.getExchangeRate()
                .flatMap { currencyInteractor.getUsdRate(it) }
                .compose(rxSchedulersTransformer.ioToMainTransformerSingle())
                .doOnSubscribe{firstFieldExchangeRate.postValue(Resource.loading(0.0))}
                .subscribe(
                        { usdRate: Double -> firstFieldExchangeRate.postValue(Resource.success(usdRate)) },
                        { firstFieldExchangeRate.postValue(Resource.error("", 0.0)) }
                )
    }

    fun fetchSecondFieldExchangeRate() {
        currencyInteractor.getExchangeRate()
                .flatMap { currencyInteractor.getEurRate(it) }
                .compose(rxSchedulersTransformer.ioToMainTransformerSingle())
                .doOnSubscribe{firstFieldExchangeRate.postValue(Resource.loading(0.0))}
                .subscribe(
                        { eurRate: Double -> secondFieldExchangeRate.postValue(Resource.success(eurRate)) },
                        { secondFieldExchangeRate.postValue(Resource.error("", 0.0)) }
                )
    }

//    private fun getWallets() = interactor.getWallets()

//    private fun fetchTransactions() = interactor.getTransactions()

    // json -- {"USD_RUB":{"value":xxx.xx}}
    private fun convertJsonToExchangeRate(json: String?): Double? =
            json?.substringAfterLast(":")?.dropLast(1)?.toDouble()

}