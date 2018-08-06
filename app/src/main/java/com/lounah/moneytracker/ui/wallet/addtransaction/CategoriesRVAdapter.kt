package com.lounah.moneytracker.ui.wallet.addtransaction

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.popov.bodya.domain.transactions.models.ExpenseCategory
import com.lounah.moneytracker.util.ResourcesSelector
import com.lounah.wallettracker.R
import kotlinx.android.synthetic.main.item_transaction_category.view.*
import ru.popov.bodya.domain.transactions.models.TransactionsCategory
import ru.popov.bodya.presentation.addtransaction.AddTransactionFragment


class CategoriesRVAdapter(private val callback: AddTransactionFragment.OnItemSelectedCallback)
    : RecyclerView.Adapter<CategoriesRVAdapter.CategoriesViewHolder>() {

    private val categories = mutableListOf<TransactionsCategory>()
    private var selectedIndex = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction_category,
                parent, false)
        return CategoriesViewHolder(view)
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holderCategories: CategoriesViewHolder, position: Int) {
        holderCategories.bind(categories[position], position)
    }

    fun setCategoriesList(categoriesList: List<TransactionsCategory>) {
        categories.clear()
        categories.addAll(categoriesList)
        notifyDataSetChanged()
    }

    inner class CategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: TransactionsCategory, position: Int) = with(itemView) {

            val iconImageResource = ResourcesSelector.fromTransactionCategoryToDrawable(item)
            category_text_view.text = itemView.resources.getString( ResourcesSelector.fromTransactionCategoryToString(item))
            category_image_view.setImageResource(iconImageResource)

            if (position == selectedIndex)
                itemView.setBackgroundColor(resources.getColor(R.color.greyWarm)) else
                itemView.setBackgroundColor(resources.getColor(android.R.color.transparent))

            itemView.setOnClickListener {
                if (selectedIndex != position) {
                    notifyItemChanged(selectedIndex)
                    notifyItemChanged(position)
                    selectedIndex = position
                    callback.onCategorySelected(categories[adapterPosition])
                }
            }
        }
    }
}