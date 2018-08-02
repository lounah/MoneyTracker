package ru.popov.bodya.presentation.account

import android.os.Bundle
import android.support.v4.app.Fragment
import com.lounah.wallettracker.R
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import ru.popov.bodya.core.mvp.AppActivity
import javax.inject.Inject

/**
 * @author popovbodya
 */
class AccountActivity: AppActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Fragment>


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }



    override fun supportFragmentInjector(): AndroidInjector<Fragment> = injector




}