package com.lounah.moneytracker.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lounah.moneytracker.ui.MainActivity
import com.lounah.moneytracker.ui.common.BaseFragment
import com.lounah.wallettracker.R
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : BaseFragment() {

    override val TAG: String
        get() = "SETTINGS_FRAGMENT"



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_settings, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbarTitle(R.string.settings)
        tv_about.setOnClickListener {
            mFragmentNavigator.pushFragment(AboutFragment(), true)
        }
    }


    override fun setUpToolbarTitle(resId: Int) {
        (activity as MainActivity).onUpdateToolbarTitle(resId)
    }

}