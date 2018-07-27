package com.lounah.moneytracker.ui.common

import android.content.Context
import android.support.v4.app.Fragment
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {

    lateinit var mFragmentNavigator: Navigation

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Navigation) {
            mFragmentNavigator = context
        }
    }

    abstract fun setUpToolbarTitle(resId : Int)
    abstract val TAG : String


    interface Navigation {
        fun pushFragment(fragment: Fragment, animationsAllowed: Boolean)
    }

}