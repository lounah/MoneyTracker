package ru.popov.bodya.core.dagger

import javax.inject.Qualifier

/**
 * @author popovbodya
 */
@Target(AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER)
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationContext
