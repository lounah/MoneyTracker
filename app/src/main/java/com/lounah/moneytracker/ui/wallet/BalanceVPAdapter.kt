package com.lounah.moneytracker.ui.wallet

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lounah.moneytracker.data.entities.Balance
import com.lounah.wallettracker.R
import kotlinx.android.synthetic.main.item_balance.view.*

class BalanceVPAdapter : PagerAdapter() {

    private var amount = mutableListOf<Balance>()

    override fun getCount() = amount.size

    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val mLayoutInflater = container.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val itemView = mLayoutInflater
                .inflate(R.layout.item_balance, container, false)


        itemView.tv_currency.text = amount[position].currency.toString()
        itemView.tv_balance.text = amount[position].amount.toString()
        itemView.tv_last_update_time.text = DateFormat.format("MM/dd/yyyy hh:mm", amount[position].lastUpdate)

        container.addView(itemView)

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeAllViews()
    }

    fun updateDataSet(amount: List<Balance>?) {
        if (amount != null) {
            this.amount.clear()
            this.amount.addAll(amount)
            notifyDataSetChanged()
        }
    }

}