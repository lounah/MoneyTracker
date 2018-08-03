package ru.popov.bodya.di.common.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.popov.bodya.di.account.AccountModule
import ru.popov.bodya.di.account.FragmentBuildersModule
import ru.popov.bodya.presentation.account.AccountActivity

/**
 * @author popovbodya
 */
@Module
interface ActivitiesBuildersModule {
    @ContributesAndroidInjector(modules = [
        FragmentBuildersModule::class,
        AccountModule::class
    ])
    fun provideAccountActivity(): AccountActivity
}