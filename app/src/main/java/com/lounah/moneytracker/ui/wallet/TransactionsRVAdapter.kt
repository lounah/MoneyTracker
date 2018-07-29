package com.lounah.moneytracker.ui.wallet

import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lounah.moneytracker.data.entities.Transaction
import com.lounah.moneytracker.data.entities.TransactionType
import com.lounah.wallettracker.R
import org.fabiomsr.moneytextview.MoneyTextView

class TransactionsRVAdapter : RecyclerView.Adapter<TransactionsRVAdapter.ViewHolder>() {

    private val transactions = mutableListOf<Transaction>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = transactions.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val action = transactions[position]
        holder.bind(action)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val date: TextView by lazy {
            itemView.findViewById<TextView>(R.id.tv_date)
        }

        private val icon: ImageView by lazy {
            itemView.findViewById<ImageView>(R.id.iv_currency_type)
        }

        private val time: TextView by lazy {
            itemView.findViewById<TextView>(R.id.tv_time)
        }
        private val currency: TextView by lazy {
            itemView.findViewById<TextView>(R.id.tv_currency)
        }
        private val transactionType: TextView by lazy {
            itemView.findViewById<TextView>(R.id.tv_transaction_category)
        }

        private val description: TextView by lazy {
            itemView.findViewById<TextView>(R.id.tv_description)
        }

        private val amount: MoneyTextView by lazy {
            itemView.findViewById<MoneyTextView>(R.id.tv_amount)
        }


        fun bind(item: Transaction) = with(itemView) {
            if (adapterPosition == 0) {
                date.text = DateFormat.format("dd/MM/yyyy", item.date)
            } else {
                if (item.date.day > transactions[adapterPosition-1].date.day) {
                    date.text = DateFormat.format("dd/MM/yyyy hh:mm", item.date)
                } else {
                    date.visibility = View.GONE
                }
            }
            description.text = item.description
            time.text = DateFormat.format("hh:mm", item.date)

            // ужасный блок кода, который нужно исправить, наверное

            transactionType.text = when(item.type) {
                TransactionType.AUTO -> itemView.resources.getString(R.string.auto)
                TransactionType.TREATMENT -> itemView.resources.getString(R.string.treatment)
                TransactionType.REST -> itemView.resources.getString(R.string.rest)
                TransactionType.FAMILY -> itemView.resources.getString(R.string.family)
                TransactionType.CLOTHES -> itemView.resources.getString(R.string.clothes)
                TransactionType.EDUCATION -> itemView.resources.getString(R.string.education)
                TransactionType.COMMUNAL_PAYMENTS -> itemView.resources.getString(R.string.communal_payments)
                TransactionType.HOME -> itemView.resources.getString(R.string.home)
                TransactionType.FOOD -> itemView.resources.getString(R.string.food)
                TransactionType.OTHER -> itemView.resources.getString(R.string.other)
                TransactionType.SALARY -> itemView.resources.getString(R.string.salary)
            }

            val iconImageResource = when(item.type) {
                TransactionType.AUTO -> R.drawable.ic_auto
                TransactionType.TREATMENT -> R.drawable.ic_treatment
                TransactionType.REST -> R.drawable.ic_rest
                TransactionType.FAMILY -> R.drawable.ic_family
                TransactionType.CLOTHES -> R.drawable.ic_clothes
                TransactionType.EDUCATION -> R.drawable.ic_education
                TransactionType.COMMUNAL_PAYMENTS -> R.drawable.ic_communal_payments
                TransactionType.HOME -> R.drawable.ic_home
                TransactionType.FOOD -> R.drawable.ic_food
                TransactionType.OTHER -> R.drawable.ic_other
                TransactionType.SALARY -> R.drawable.ic_salary
            }

            icon.setImageResource(iconImageResource)

            if (item.amount < 0) {
                amount.setBaseColor(itemView.resources.getColor(R.color.colorExpense))
                amount.setDecimalsColor(itemView.resources.getColor(R.color.colorExpense))
            }
            else {
                amount.setBaseColor(itemView.resources.getColor(R.color.colorIncome))
                amount.setDecimalsColor(itemView.resources.getColor(R.color.colorIncome))
            }
            amount.amount = item.amount.toFloat()
            amount.setSymbol("")
            currency.text = item.currency.toString()
        }
    }

    fun addTransaction(transaction: Transaction) {
        transactions.add(transaction)
        notifyDataSetChanged()
    }

    fun addTransactions(transactions: MutableList<Transaction>) {
        transactions.addAll(transactions)
        notifyDataSetChanged()
    }

    // TODO: USE DIFF UTIL
    fun updateDataSet(transactions: List<Transaction>?) {
        if (transactions != null) {
            this.transactions.clear()
            this.transactions.addAll(transactions)
            notifyDataSetChanged()
        }
    }
}