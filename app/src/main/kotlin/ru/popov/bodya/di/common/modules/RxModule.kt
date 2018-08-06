package ru.popov.bodya.di.common.modules

import dagger.Module
import dagger.Provides
import ru.popov.bodya.core.rx.RxSchedulers
import ru.popov.bodya.core.rx.RxSchedulersImpl
import ru.popov.bodya.core.rx.RxSchedulersTransformer
import ru.popov.bodya.core.rx.RxSchedulersTransformerImpl
import javax.inject.Singleton

/**
 *  @author popovbodya
 */
@Module
class RxModule {

    @Singleton
    @Provides
    fun provideRxSchedulersTransformer(rxSchedulers: RxSchedulers): RxSchedulersTransformer =
            RxSchedulersTransformerImpl(rxSchedulers)


    @Singleton
    @Provides
    fun provideRxSchedulers(): RxSchedulers = RxSchedulersImpl()
}