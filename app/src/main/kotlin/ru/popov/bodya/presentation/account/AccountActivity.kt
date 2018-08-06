package ru.popov.bodya.presentation.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import com.lounah.moneytracker.ui.settings.AboutFragment
import com.lounah.moneytracker.ui.settings.SettingsFragment
import com.lounah.wallettracker.R
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import ru.popov.bodya.core.mvwhatever.AppActivity
import ru.popov.bodya.presentation.addtransaction.AddTransactionFragment
import ru.popov.bodya.presentation.common.Screens.ABOUT_SCREEN
import ru.popov.bodya.presentation.common.Screens.ADD_NEW_TRANSACTION_SCREEN
import ru.popov.bodya.presentation.common.Screens.SETTINGS_SCREEN
import ru.popov.bodya.presentation.common.Screens.WALLET_SCREEN
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.SupportAppNavigator
import javax.inject.Inject

/**
 * @author popovbodya
 */
class AccountActivity : AppActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var router: Router
    @Inject
    lateinit var navigationHolder: NavigatorHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            router.replaceScreen(WALLET_SCREEN)
        }
    }

    override fun onResume() {
        super.onResume()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        router.exit()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = injector


    private val navigator = object : SupportAppNavigator(this, supportFragmentManager, R.id.main_container) {
        override fun createActivityIntent(context: Context, screenKey: String, data: Any?): Intent? {
            return null
        }

        override fun createFragment(screenKey: String, data: Any?): Fragment? {
            return when (screenKey) {
                WALLET_SCREEN -> AccountFragment()
                ADD_NEW_TRANSACTION_SCREEN -> AddTransactionFragment.newInstance(data as Boolean)
                SETTINGS_SCREEN -> SettingsFragment()
                ABOUT_SCREEN -> AboutFragment()
                else -> null
            }
        }

        override fun showSystemMessage(message: String) {
            Toast.makeText(this@AccountActivity, message, Toast.LENGTH_SHORT).show()
        }

        override fun exit() {
            finish()
        }
    }
}