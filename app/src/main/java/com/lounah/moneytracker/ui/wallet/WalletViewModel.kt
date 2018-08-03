package com.lounah.moneytracker.ui.wallet

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.lounah.moneytracker.data.entities.Resource
import com.lounah.moneytracker.data.entities.Transaction
import com.lounah.moneytracker.data.entities.Wallet
import com.lounah.moneytracker.util.AbsentLiveData
import io.reactivex.disposables.CompositeDisposable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.popov.bodya.domain.account.WalletInteractor
import javax.inject.Inject

class WalletViewModel @Inject constructor(private val interactor: WalletInteractor) : ViewModel() {

    private lateinit var exchangeRateGetDisposable: CompositeDisposable
    private val refreshState = MutableLiveData<Boolean>()
    val firstFieldExchangeRate = MutableLiveData<Resource<Double>>()
    val secondFieldExchangeRate = MutableLiveData<Resource<Double>>()
    val currentBalance: LiveData<Resource<List<Wallet>>> =
            Transformations.switchMap(refreshState) { shouldRefresh ->
                if (shouldRefresh) getWallets() else AbsentLiveData.create()
            }
    val transactions: LiveData<List<Transaction>> =
            Transformations.switchMap(refreshState) { shouldRefresh ->
                if (shouldRefresh) fetchTransactions() else AbsentLiveData.create()
            }

    override fun onCleared() {
        super.onCleared()
        if (::exchangeRateGetDisposable.isInitialized && !exchangeRateGetDisposable.isDisposed) {
            exchangeRateGetDisposable.dispose()
        }
    }

    fun refreshCurrentBalance() {
        refreshState.value = true
    }

    fun fetchFirstFieldExchangeRate(from: String, to: String) {
        interactor.getExchangeRate(from, to).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                firstFieldExchangeRate.postValue(Resource.error("", 0.0))
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                val jsonResponse = response?.body()?.string()
                firstFieldExchangeRate.postValue(Resource.success(convertJsonToExchangeRate(jsonResponse)))
            }
        })
    }

    fun fetchSecondFieldExchangeRate(from: String, to: String) {
        interactor.getExchangeRate(from, to).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                secondFieldExchangeRate.postValue(Resource.error("", 0.0))
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                val jsonResponse = response?.body()?.string()
                secondFieldExchangeRate.postValue(Resource.success(convertJsonToExchangeRate(jsonResponse)))
            }
        })
    }

    private fun getWallets() = interactor.getWallets()

    private fun fetchTransactions() = interactor.getTransactions()

    // json -- {"USD_RUB":{"value":xxx.xx}}
    private fun convertJsonToExchangeRate(json: String?): Double? =
            json?.substringAfterLast(":")?.dropLast(1)?.toDouble()

}