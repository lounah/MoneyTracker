package ru.popov.bodya.presentation.addtransaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jakewharton.rxbinding2.widget.RxTextView
import com.lounah.moneytracker.data.entities.Currency
import com.lounah.moneytracker.data.entities.Transaction
import com.lounah.moneytracker.data.entities.TransactionType
import com.lounah.moneytracker.data.entities.WalletType
import com.lounah.moneytracker.ui.wallet.addtransaction.AddTransactionView
import com.lounah.moneytracker.ui.wallet.addtransaction.CategoriesRVAdapter
import com.lounah.wallettracker.R
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.fragment_add_transaction.*
import ru.popov.bodya.core.mvwhatever.AppFragment
import java.util.*

/*
    Мне очень стыдно за этот фрагмент
    Для того, чтобы не создавать multable поля в объекте Transaction,
    этот объект собирается по кусочкам, которые мы получаем из полей
    Посмотрите на UI этого фрагмента в приложении
 */

class AddTransactionFragment : AppFragment(), AddTransactionView {

    private lateinit var categoriesAdapter: CategoriesRVAdapter
    private lateinit var inputDisposable: Disposable

    private lateinit var selectedWallet: WalletType
    private lateinit var selectedCurrency: Currency
    private lateinit var selectedCategory: TransactionType
    private lateinit var comment: String
    private val date = Date()
    private var amount: Double = 0.0
    private var isIncome = false

    companion object {
        const val INCOME_KEY = "IS_INCOME"
        fun newInstance(isIncome: Boolean) = AddTransactionFragment().apply {
            val args = Bundle()
            args.putBoolean(INCOME_KEY, isIncome)
            arguments = args
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleRes = when (arguments?.getBoolean(INCOME_KEY)) {
            true -> {
                isIncome = true
                R.string.new_income
            }
            false -> {
                isIncome = false
                R.string.new_expense
            }
            else -> R.string.create_transaction
        }
        initUI()
    }

    private fun initUI() {
        initCategoriesList()
        initInputListeners()
        initCreateTransactionButton()
        initRadioGroups()
    }

    private fun initCategoriesList() {
        categoriesAdapter = CategoriesRVAdapter(object : OnItemSelectedCallback {
            override fun onCategorySelected(type: TransactionType) {
                selectedCategory = type
            }
        })
        rv_categories.adapter = categoriesAdapter
    }

    private fun initInputListeners() {
        val inputObserver: Observable<Boolean> = Observable.combineLatest(
                RxTextView.textChanges(et_transaction_sum),
                RxTextView.textChanges(et_comment_on_transaction),
                BiFunction { amount, comment ->
                    amount.isNotEmpty()
                            && comment.isNotEmpty()
                            && rg_currencies.checkedRadioButtonId != -1
                            && rg_wallets.checkedRadioButtonId != -1
                })
        inputDisposable = inputObserver.subscribe(btn_create_transaction::setEnabled)
    }

    private fun initCreateTransactionButton() {
        btn_create_transaction.setOnClickListener {
            amount = et_transaction_sum.text.toString().toDouble()
            if (!isIncome)
                amount = -amount
            comment = et_comment_on_transaction.text.toString()
            val transaction = Transaction(date = date, wallet = selectedWallet,
                    description = comment, type = selectedCategory,
                    amount = amount, currency = selectedCurrency)
        }
    }

    private fun initRadioGroups() {

        rg_currencies.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.btn_rub -> selectedCurrency = Currency.RUB
                R.id.btn_usd -> selectedCurrency = Currency.USD
                R.id.btn_eur -> selectedCurrency = Currency.EUR
            }
        }
        rg_wallets.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.btn_cash -> selectedWallet = WalletType.CASH
                R.id.btn_bank_account -> selectedWallet = WalletType.BANK_ACCOUNT
                R.id.btn_credit_card -> selectedWallet = WalletType.CREDIT_CARD
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (::inputDisposable.isInitialized && !inputDisposable.isDisposed)
            inputDisposable.dispose()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onTransactionCreated() {
       Toast.makeText(activity, "onTransactionCreated", Toast.LENGTH_LONG).show()
    }

    interface OnItemSelectedCallback {
        fun onCategorySelected(type: TransactionType)
    }
}