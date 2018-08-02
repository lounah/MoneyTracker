package com.lounah.moneytracker.ui.common

import android.content.Context
import android.support.v4.app.Fragment
import dagger.android.support.DaggerFragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View

abstract class BaseFragment: Fragment() {

    lateinit var mFragmentNavigator: Navigation

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Navigation) {
            mFragmentNavigator = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View?
            = inflater.inflate(layoutRes, container, false)

    abstract fun setUpToolbarTitle(resId : Int)
    abstract val TAG : String
    abstract val layoutRes : Int

    interface Navigation {
        fun pushFragment(fragment: Fragment, animationsAllowed: Boolean)
        fun showDialogFragment(fragment: Fragment)
    }

}