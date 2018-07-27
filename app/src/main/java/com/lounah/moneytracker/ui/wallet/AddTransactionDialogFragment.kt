package com.lounah.moneytracker.ui.wallet

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.jakewharton.rxbinding2.widget.RxTextView
import com.lounah.moneytracker.data.entities.Currency
import com.lounah.moneytracker.data.entities.Transaction
import com.lounah.moneytracker.data.entities.TransactionType
import com.lounah.moneytracker.data.entities.WalletType
import com.lounah.wallettracker.R
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.fragment_add_transaction.*
import java.util.*


class AddTransactionDialogFragment : DialogFragment() {

    // Должен быть nullable, чтобы я мог занулить его в onDetach
    private lateinit var callback: AddTransactionCallback
    private lateinit var inputObserver: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callback = parentFragment as AddTransactionCallback
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_add_transaction, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_create_transaction.setOnClickListener { buildAndPassTransaction() }
        initializeSpinners()
        initializeInputObserver()
    }

    private fun initializeSpinners() {
        spinner_transaction_category.adapter = ArrayAdapter<String>(context!!,
                android.R.layout.simple_list_item_1, context!!.resources.getStringArray(R.array.transaction_categories))
        spinner_transaction_currency.adapter = ArrayAdapter<String>(context!!,
                android.R.layout.simple_list_item_1, context!!.resources.getStringArray(R.array.currencies))
        spinner_transaction_wallet.adapter = ArrayAdapter<String>(context!!,
                android.R.layout.simple_list_item_1, context!!.resources.getStringArray(R.array.wallets))
    }

    private fun initializeInputObserver() {
        val isAdditionEnabled: Observable<Boolean> = Observable.combineLatest(
                RxTextView.textChanges(et_transaction_sum),
                RxTextView.textChanges(et_comment_on_transaction),
                BiFunction { amount: CharSequence, comment ->
                    (amount.toString().isNotEmpty() && amount.toString().toDouble() != 0.0) && (comment.isNotEmpty())
                })
        inputObserver = isAdditionEnabled.subscribe(btn_create_transaction::setEnabled)
    }

    private fun buildAndPassTransaction() {

        val category = when(spinner_transaction_category.selectedItem) {
            getStringFromResources(R.string.other) -> TransactionType.OTHER
            getStringFromResources(R.string.clothes) -> TransactionType.CLOTHES
            getStringFromResources(R.string.food) -> TransactionType.FOOD
            getStringFromResources(R.string.rest) -> TransactionType.REST
            getStringFromResources(R.string.family) -> TransactionType.FAMILY
            getStringFromResources(R.string.auto) -> TransactionType.AUTO
            getStringFromResources(R.string.treatment) -> TransactionType.TREATMENT
            getStringFromResources(R.string.home) -> TransactionType.HOME
            getStringFromResources(R.string.salary) -> TransactionType.SALARY
            getStringFromResources(R.string.education) -> TransactionType.EDUCATION
            else -> TransactionType.OTHER
        }

        val wallet = when(spinner_transaction_wallet.selectedItem) {
            getStringFromResources(R.string.bank_account) -> WalletType.BANK_ACCOUNT
            getStringFromResources(R.string.cash) -> WalletType.CASH
            getStringFromResources(R.string.credit_card) -> WalletType.CREDIT_CARD
            else -> WalletType.CASH
        }

        val amount = et_transaction_sum.text.toString().toDouble()

        val currency = when(spinner_transaction_currency.selectedItem) {
            getStringFromResources(R.string.rub) -> Currency.RUB
            getStringFromResources(R.string.eur) -> Currency.EUR
            getStringFromResources(R.string.usd) -> Currency.USD
            else -> Currency.RUB
        }
        val comment = et_comment_on_transaction.text.toString()
        val transaction = Transaction(date = Date(), wallet = wallet, description = comment,
                type = category, amount = amount, currency = currency)
        callback.onTransactionCreated(transaction)
    }

    private fun getStringFromResources(resId: Int) =
            context!!.resources.getString(resId)

    override fun onDetach() {
        super.onDetach()
        if (::inputObserver.isInitialized && !inputObserver.isDisposed)
            inputObserver.dispose()
        // idk how to fix this
        //   callback = null
    }

    interface AddTransactionCallback {
        fun onTransactionCreated(transaction: Transaction)
    }
}