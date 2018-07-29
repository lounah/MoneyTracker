package com.lounah.moneytracker.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lounah.moneytracker.ui.MainActivity
import com.lounah.moneytracker.ui.common.BaseFragment
import com.lounah.wallettracker.BuildConfig
import com.lounah.wallettracker.R
import kotlinx.android.synthetic.main.fragment_about_app.*

class AboutFragment : BaseFragment() {

    override val TAG: String
        get() = "ABOUT_FRAGMENT"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_about_app, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        setUpToolbarTitle(R.string.about_application)
        tv_app_version.text =  resources.getString(R.string.version_code,  BuildConfig.VERSION_NAME)
        tv_copyright.text =  resources.getString(R.string.copyright)
    }

    override fun setUpToolbarTitle(resId: Int) {
        (activity as MainActivity).onUpdateToolbarTitle(resId)
    }

}