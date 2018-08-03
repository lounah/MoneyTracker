package ru.popov.bodya.presentation.transactions

import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.View
import com.lounah.moneytracker.data.entities.Transaction
import com.lounah.moneytracker.util.ResourcesSelector
import com.lounah.wallettracker.R
import kotlinx.android.synthetic.main.item_transaction.view.*

/**
 *
 * @author Popov Bogdan
 */
class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: Transaction) = with(itemView) {
        tv_description.text = item.description
        tv_time.text = DateFormat.format("hh:mm", item.date)
        tv_currency.text = item.currency.toString()
        tv_transaction_category.text = ResourcesSelector.fromTransactionTypeToString(item.type, itemView)
        iv_currency_type.setImageResource(ResourcesSelector.fromTransactionTypeToDrawable(item.type))

        if (item.amount < 0) {
            tv_amount.setBaseColor(itemView.resources.getColor(R.color.colorExpense))
            tv_amount.setDecimalsColor(itemView.resources.getColor(R.color.colorExpense))
        } else {
            tv_amount.setBaseColor(itemView.resources.getColor(R.color.colorIncome))
            tv_amount.setDecimalsColor(itemView.resources.getColor(R.color.colorIncome))
        }
        tv_amount.amount = item.amount.toFloat()
        tv_amount.setSymbol("")
    }
}