package ru.popov.bodya.presentation.init

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import dagger.android.AndroidInjection
import ru.popov.bodya.core.mvwhatever.AppActivity
import ru.popov.bodya.presentation.account.AccountActivity
import ru.popov.bodya.presentation.common.Screens.ACCOUNT_ACTIVITY
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.SupportAppNavigator
import javax.inject.Inject

/**
 *  @author popovbodya
 */
class InitActivity : AppActivity() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder
    @Inject
    lateinit var initViewModel: InitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        subscribeToViewModel()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
        initViewModel.isAllReadyLiveData.removeObservers(this)
    }

    private fun subscribeToViewModel() {
        initViewModel.isAllReadyLiveData.observe(this, Observer { isReady ->
            isReady?.let { initViewModel.onEverythingIsReady() }
        })
        initViewModel.fetchInitialData()
    }

    private val navigator = object : SupportAppNavigator(this, supportFragmentManager, 0) {
        override fun createActivityIntent(context: Context, screenKey: String, data: Any?): Intent? {
            return when (screenKey) {
                ACCOUNT_ACTIVITY -> Intent(this@InitActivity, AccountActivity::class.java)
                else -> null
            }
        }

        override fun createFragment(screenKey: String, data: Any?): Fragment? {
            return null
        }

        override fun showSystemMessage(message: String) {
            Toast.makeText(this@InitActivity, message, Toast.LENGTH_SHORT).show()
        }

        override fun exit() {
            finish()
        }
    }

}