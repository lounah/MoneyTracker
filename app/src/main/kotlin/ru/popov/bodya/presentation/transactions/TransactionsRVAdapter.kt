package ru.popov.bodya.presentation.transactions

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lounah.wallettracker.R
import ru.popov.bodya.domain.transactions.models.Transaction

class TransactionsRVAdapter : RecyclerView.Adapter<TransactionViewHolder>() {

    private val transactions = mutableListOf<Transaction>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun getItemCount() = transactions.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val action = transactions[position]
        holder.bind(action)
    }

    // TODO: USE DIFF UTIL
    fun updateDataSet(transactionList: List<Transaction>) {
        this.transactions.clear()
        transactions.addAll(transactionList)
        notifyDataSetChanged()
    }
}

