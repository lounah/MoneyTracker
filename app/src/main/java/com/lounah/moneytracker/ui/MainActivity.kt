package com.lounah.moneytracker.ui

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.lounah.moneytracker.ui.common.BaseFragment
import com.lounah.moneytracker.ui.settings.SettingsFragment
import com.lounah.moneytracker.ui.wallet.WalletFragment
import com.lounah.wallettracker.R
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : DaggerAppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        BaseFragment.Navigation,
        FragNavController.RootFragmentListener {

    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var fragNavController: FragNavController

    companion object {
        private const val BALANCE_FRAGMENT_ID = 0
        private const val FRAGMENT_CONTAINER_ID = R.id.container_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI(savedInstanceState, supportFragmentManager)
    }

    private fun initToggle() {
        if (!fragNavController.isRootFragment) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        } else {
            toggle = ActionBarDrawerToggle(
                    this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
            drawer_layout.addDrawerListener(toggle)
            toggle.syncState()
        }
    }

    private fun initUI(savedInstanceState: Bundle?, supportFragmentManager: FragmentManager) {
        nav_view.setNavigationItemSelectedListener(this)
        initFragmentNavigationController(savedInstanceState, supportFragmentManager)
        toolbar.title = resources.getString(R.string.wallet)
        setSupportActionBar(toolbar)
        initToggle()
    }

    private fun initFragmentNavigationController(savedInstanceState: Bundle?, supportFragmentManager: FragmentManager) {
        fragNavController = FragNavController.newBuilder(savedInstanceState, supportFragmentManager, FRAGMENT_CONTAINER_ID)
                .transactionListener(object : FragNavController.TransactionListener {
                    override fun onFragmentTransaction(p0: Fragment?, p1: FragNavController.TransactionType?) {
                        if (supportActionBar != null) {
                            supportActionBar?.setDisplayHomeAsUpEnabled(!fragNavController.isRootFragment)
                        }
                    }

                    override fun onTabTransaction(p0: Fragment?, p1: Int) {
                        if (supportActionBar != null) {
                            supportActionBar?.setDisplayHomeAsUpEnabled(!fragNavController.isRootFragment)
                        }
                    }

                })
                .rootFragments(listOf(WalletFragment()))
                .rootFragmentListener(this, 1)
                .selectedTabIndex(BALANCE_FRAGMENT_ID)
                .build()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            if (!fragNavController.isRootFragment) {
                fragNavController.popFragment()
            } else
                super.onBackPressed()
        }
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        fragNavController.onSaveInstanceState(outState)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> fragNavController.popFragment()
        }
        return true
    }

    fun onUpdateToolbarTitle(resId: Int) {
        toolbar.title = getString(resId)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        if (fragNavController.isRootFragment) {
            initToggle()
            drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        } else drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_wallet -> fragNavController.switchTab(BALANCE_FRAGMENT_ID)
            R.id.nav_settings -> pushFragment(SettingsFragment(), false)
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun pushFragment(fragment: Fragment, animationsAllowed: Boolean) {
        if (animationsAllowed) {
            fragNavController.pushFragment(fragment, FragNavTransactionOptions.newBuilder()
                    .customAnimations(R.anim.fade_in,
                            R.anim.fade_out)
                    .build())
        } else {
            fragNavController.pushFragment(fragment, FragNavTransactionOptions.newBuilder()
                    .build())
        }
    }

    override fun getRootFragment(i: Int): Fragment? {
        var root: Fragment? = null
        when (i) {
            BALANCE_FRAGMENT_ID -> root = WalletFragment()
        }
        return root
    }
}
