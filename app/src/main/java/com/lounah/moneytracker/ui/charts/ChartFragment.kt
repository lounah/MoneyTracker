package com.lounah.moneytracker.ui.charts

import android.os.Bundle
import android.view.View
import com.lounah.moneytracker.ui.MainActivity
import com.lounah.moneytracker.ui.common.BaseFragment
import com.lounah.wallettracker.R

class ChartFragment : BaseFragment() {
    override val layoutRes: Int
        get() = R.layout.fragment_charts

    override val TAG: String
        get() = "CHART_FRAGMENT"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        setUpToolbarTitle(R.string.statistics)
    }

    override fun setUpToolbarTitle(resId: Int) {
        (activity as MainActivity).onUpdateToolbarTitle(resId)
    }

}