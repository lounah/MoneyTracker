package com.lounah.moneytracker.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lounah.wallettracker.BuildConfig
import com.lounah.wallettracker.R
import kotlinx.android.synthetic.main.fragment_about_app.*
import ru.popov.bodya.core.mvwhatever.AppFragment

class AboutFragment : AppFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         return inflater.inflate(R.layout.fragment_about_app, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        tv_app_version.text =  resources.getString(R.string.version_code,  BuildConfig.VERSION_NAME)
        tv_copyright.text =  resources.getString(R.string.copyright)
    }
}