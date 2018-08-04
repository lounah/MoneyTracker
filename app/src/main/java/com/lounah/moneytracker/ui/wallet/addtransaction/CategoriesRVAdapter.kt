package com.lounah.moneytracker.ui.wallet.addtransaction

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ru.popov.bodya.domain.transactions.models.ExpenseCategory
import com.lounah.moneytracker.util.ResourcesSelector
import com.lounah.wallettracker.R
import ru.popov.bodya.domain.transactions.models.TransactionsCategory
import ru.popov.bodya.presentation.addtransaction.AddTransactionFragment

/*
    За адаптер мне также стыдно -- попытался сделать selector в ресайклере
    Самый главный минус -- он подвязан на вшитых категориях, которые нельзя добавлять или изменять
    На момент коммита этого я не успею переписать, поэтому начну менять структуру entity сразу после
    ПР
 */


class CategoriesRVAdapter(private val callback: AddTransactionFragment.OnItemSelectedCallback)
    : RecyclerView.Adapter<CategoriesRVAdapter.ViewHolder>() {

    private val categories = mutableListOf(TransactionsCategory.Expense(ExpenseCategory.OTHER_EXPENSE), TransactionsCategory.Expense(ExpenseCategory.FAMILY))

    private var selectedIndex = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction_category,
                parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category, position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val categoryName: TextView by lazy {
            itemView.findViewById<TextView>(R.id.tv_category)
        }

        private val categoryIcon: ImageView by lazy {
            itemView.findViewById<ImageView>(R.id.iv_category)
        }

        fun bind(item: TransactionsCategory, position: Int) = with(itemView) {

            val iconImageResource = ResourcesSelector.fromTransactionCategoryToDrawable(item)
            categoryName.text = itemView.resources.getString( ResourcesSelector.fromTransactionCategoryToString(item))
            categoryIcon.setImageResource(iconImageResource)

            if (position == selectedIndex)
                itemView.setBackgroundColor(resources.getColor(R.color.greyWarm)) else
                itemView.setBackgroundColor(resources.getColor(android.R.color.transparent))

            itemView.setOnClickListener {
                selectedIndex = position
                callback.onCategorySelected(categories[adapterPosition])
                notifyDataSetChanged()
            }
        }
    }
}