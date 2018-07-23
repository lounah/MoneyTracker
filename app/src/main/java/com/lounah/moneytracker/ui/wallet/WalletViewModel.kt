package com.lounah.moneytracker.ui.wallet

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.lounah.moneytracker.data.entities.Balance
import com.lounah.moneytracker.data.entities.Resource
import com.lounah.moneytracker.data.entities.Transaction
import com.lounah.moneytracker.data.repositories.WalletRepository
import com.lounah.moneytracker.util.AbsentLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class WalletViewModel @Inject constructor(private val repository : WalletRepository) : ViewModel() {

    private val refreshState = MutableLiveData<Boolean>()
    val addingTransactionResult = MutableLiveData<Boolean>()

    private lateinit var transactionAddingDisposable : Disposable

    val currentBalance : LiveData<Resource<List<Balance>>> =
            Transformations.switchMap(refreshState) { shouldRefresh ->
                if (shouldRefresh) updateCurrentBalance() else AbsentLiveData.create()
            }

    val transactions : LiveData<List<Transaction>> =
            Transformations.switchMap(refreshState) { value ->
                if (value) fetchTransactions() else AbsentLiveData.create()
            }


    private fun updateCurrentBalance() = repository.getBalance()

    fun refreshCurrentBalance() {
        refreshState.value = true
    }

    fun addTransaction(transaction: Transaction) {
        transactionAddingDisposable = repository.addTransaction(transaction)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({addingTransactionResult.postValue(true)}, {
                    _ -> addingTransactionResult.postValue(false)
                })
    }

    private fun fetchTransactions() = repository.getTransactions()

    override fun onCleared() {
        super.onCleared()
        if (::transactionAddingDisposable.isInitialized && !transactionAddingDisposable.isDisposed)
            transactionAddingDisposable.dispose()
    }

}