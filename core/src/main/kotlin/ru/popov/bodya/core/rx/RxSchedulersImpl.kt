package ru.popov.bodya.core.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.schedulers.Schedulers

/**
 * @author popovbodya
 */

class RxSchedulersImpl : RxSchedulers {

    override fun mainThreadScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun ioScheduler(): Scheduler {
        return Schedulers.io()
    }

    override fun computationScheduler(): Scheduler {
        return Schedulers.computation()
    }
}
