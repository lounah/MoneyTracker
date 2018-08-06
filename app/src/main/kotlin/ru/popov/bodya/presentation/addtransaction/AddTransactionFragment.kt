package ru.popov.bodya.presentation.addtransaction

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jakewharton.rxbinding2.widget.RxTextView
import com.lounah.moneytracker.data.entities.Status
import com.lounah.moneytracker.ui.wallet.addtransaction.AddTransactionView
import com.lounah.moneytracker.ui.wallet.addtransaction.CategoriesRVAdapter
import com.lounah.wallettracker.R
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.fragment_add_transaction.*
import ru.popov.bodya.core.mvwhatever.AppFragment
import ru.popov.bodya.domain.currency.model.Currency
import ru.popov.bodya.domain.transactions.models.TransactionsCategory
import ru.popov.bodya.domain.transactions.models.WalletType
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

class AddTransactionFragment : AppFragment(), AddTransactionView {

    @Inject
    lateinit var router: Router
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    @Inject
    lateinit var viewModel: AddTransactionViewModel

    private lateinit var categoriesAdapter: CategoriesRVAdapter
    private lateinit var inputDisposable: Disposable

    private lateinit var selectedWallet: WalletType
    private lateinit var selectedCurrency: Currency
    private lateinit var selectedCategory: TransactionsCategory
    private lateinit var comment: String
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

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        isIncome = when (arguments?.getBoolean(INCOME_KEY)) {
            true -> true
            false -> false
            null -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val parentView = inflater.inflate(R.layout.fragment_add_transaction, container, false)
        setHasOptionsMenu(true)
        initToolbar(parentView)
        return parentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun onStart() {
        super.onStart()
        subscribeToViewModel()
        viewModel.fetchTransactionCategories(isIncome)
    }

    override fun onStop() {
        super.onStop()
        inputDisposable.dispose()
        removeObservers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                router.exit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onTransactionCreated() {
        Toast.makeText(activity, "onTransactionCreated", Toast.LENGTH_LONG).show()
    }

    private fun subscribeToViewModel() {
        viewModel.transactionCategoriesLiveData.observe(this, Observer { categoriesList ->
            categoriesList?.apply { categoriesAdapter.setCategoriesList(this) }
        })

        viewModel.transactionAddStatus.observe(this, Observer { resource ->
            when (resource?.status) {
                Status.SUCCESS -> router.exitWithMessage("Transaction added!")
                Status.ERROR -> router.showSystemMessage("Transaction addition failed")
                Status.LOADING -> router.showSystemMessage("Loading")
                null -> router.showSystemMessage("Transaction addition failed")
            }
        })
    }

    private fun initToolbar(parentView: View) {
        val toolbar = parentView.findViewById<Toolbar>(R.id.toolbar)
        val activity = activity as AppCompatActivity
        toolbar.title = when (isIncome) {
            true -> getString(R.string.new_income)
            false -> getString(R.string.new_expense)
        }
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initUI() {
        initCategoriesList()
        initInputListeners()
        initCreateTransactionButton()
        initRadioGroups()
    }

    private fun initCategoriesList() {
        categoriesAdapter = CategoriesRVAdapter(object : OnItemSelectedCallback {
            override fun onCategorySelected(type: TransactionsCategory) {
                selectedCategory = type
                Timber.d("selectedCategory= $selectedCategory")
            }
        })
        categories_recycler_view.adapter = categoriesAdapter
    }

    private fun initInputListeners() {
        val inputObserver: Observable<Boolean> = Observable.combineLatest(
                RxTextView.textChanges(transaction_sum_edit_text),
                RxTextView.textChanges(comment_edit_text),
                BiFunction { amount, comment ->
                    amount.isNotEmpty()
                            && this::selectedCurrency.isInitialized
                            && comment.isNotEmpty()
                            && rg_currencies.checkedRadioButtonId != -1
                            && wallets_segmented_group.checkedRadioButtonId != -1
                })
        inputDisposable = inputObserver.subscribe(btn_create_transaction::setEnabled)
    }

    private fun initCreateTransactionButton() {
        btn_create_transaction.setOnClickListener {
            amount = transaction_sum_edit_text.text.toString().toDouble()
            comment = comment_edit_text.text.toString()
            viewModel.onAddTransactionButtonClick(selectedWallet, selectedCategory, selectedCurrency, amount, comment)
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
        wallets_segmented_group.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.cash_radio_button -> selectedWallet = WalletType.CASH
                R.id.bank_account_radio_button -> selectedWallet = WalletType.BANK_ACCOUNT
                R.id.credit_radio_button -> selectedWallet = WalletType.CREDIT_CARD
            }
        }
    }

    private fun removeObservers() {
        viewModel.transactionCategoriesLiveData.removeObservers(this)
        viewModel.transactionAddStatus.removeObservers(this)
    }

    interface OnItemSelectedCallback {
        fun onCategorySelected(type: TransactionsCategory)
    }
}