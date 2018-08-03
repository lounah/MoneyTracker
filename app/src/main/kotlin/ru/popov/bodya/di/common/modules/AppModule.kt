package ru.popov.bodya.di.common.modules

import dagger.Module

@Module(includes = [
    ViewModelModule::class,
    NetworkModule::class,
    PersistenceModule::class])
class AppModule {

}
