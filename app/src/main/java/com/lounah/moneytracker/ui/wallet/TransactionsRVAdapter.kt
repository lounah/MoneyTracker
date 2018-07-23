package com.lounah.moneytracker.ui.wallet

import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lounah.moneytracker.R
import com.lounah.moneytracker.data.entities.Transaction


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
            itemView.findViewById<TextView>(R.id.tv_action_date)
        }
        private val actionType: TextView by lazy {
            itemView.findViewById<TextView>(R.id.tv_action_type)
        }
        private val amount: TextView by lazy {
            itemView.findViewById<TextView>(R.id.tv_amount)
        }
        private val currency: TextView by lazy {
            itemView.findViewById<TextView>(R.id.tv_currency)
        }

        fun bind(item : Transaction) = with(itemView) {
            date.text = DateFormat.format("MM/dd/yyyy hh:mm", item.date)
            actionType.text = item.type.toString()
            amount.text = item.amount.toString()
            currency.text = item.currency.toString()
        }
    }

    fun addTransaction(transaction : Transaction) {
        transactions.add(transaction)
        notifyDataSetChanged()
    }

    fun addTransactions(transactions : MutableList<Transaction>) {
        transactions.addAll(transactions)
        notifyDataSetChanged()
    }

    fun updateDataSet(transactions : List<Transaction>?) {
        if (transactions != null) {
            this.transactions.clear()
            this.transactions.addAll(transactions)
            notifyDataSetChanged()
        }
    }
}