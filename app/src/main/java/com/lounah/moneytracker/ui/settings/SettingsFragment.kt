package com.lounah.moneytracker.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lounah.wallettracker.R
import kotlinx.android.synthetic.main.fragment_settings.*
import ru.popov.bodya.core.mvwhatever.AppFragment


class SettingsFragment : AppFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_about.setOnClickListener {

        }
    }
}