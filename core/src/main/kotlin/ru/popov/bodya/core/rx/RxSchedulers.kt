package ru.popov.bodya.core.rx

import io.reactivex.Scheduler
import io.reactivex.annotations.NonNull

/**
 * @author popovbodya
 */

interface RxSchedulers {

    fun mainThreadScheduler(): Scheduler

    fun ioScheduler(): Scheduler

    fun computationScheduler(): Scheduler

}
