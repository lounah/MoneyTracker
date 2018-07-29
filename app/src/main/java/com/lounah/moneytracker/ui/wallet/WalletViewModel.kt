package com.lounah.moneytracker.ui.wallet

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.lounah.moneytracker.data.entities.Balance
import com.lounah.moneytracker.data.entities.Resource
import com.lounah.moneytracker.data.entities.Transaction
import com.lounah.moneytracker.domain.interactors.WalletInteractor
import com.lounah.moneytracker.util.AbsentLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ScheduledThreadPoolExecutor
import javax.inject.Inject


class WalletViewModel @Inject constructor(private val interactor: WalletInteractor) : ViewModel() {

    private val refreshState = MutableLiveData<Boolean>()

    private lateinit var transactionAddingDisposable: Disposable
    private lateinit var exchangeRateGetDisposable: CompositeDisposable

    val addingTransactionResult = MutableLiveData<Boolean>()

    val currentBalance: LiveData<Resource<List<Balance>>> =
            Transformations.switchMap(refreshState) { shouldRefresh ->
                if (shouldRefresh) getBalance() else AbsentLiveData.create()
            }

    val transactions: LiveData<List<Transaction>> =
            Transformations.switchMap(refreshState) { value ->
                if (value) fetchTransactions() else AbsentLiveData.create()
            }

    val firstFieldExchangeRate = MutableLiveData<Resource<Double>>()
    val secondFieldExchangeRate = MutableLiveData<Resource<Double>>()

    private fun getBalance() = interactor.getAccountBalance()

    private fun fetchTransactions() = interactor.getTransactions()

    fun refreshCurrentBalance() {
        refreshState.value = true
    }

    fun addTransaction(transaction: Transaction) {
        interactor.createTransaction(transaction)
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

    private fun convertJsonToExchangeRate(json: String?): Double? =
            json?.substringAfterLast(":")?.dropLast(1)?.toDouble()

    override fun onCleared() {
        super.onCleared()
        if (::transactionAddingDisposable.isInitialized && !transactionAddingDisposable.isDisposed) {
            transactionAddingDisposable.dispose()
        }
        if (::exchangeRateGetDisposable.isInitialized && !exchangeRateGetDisposable.isDisposed) {
            exchangeRateGetDisposable.dispose()
        }
    }

}