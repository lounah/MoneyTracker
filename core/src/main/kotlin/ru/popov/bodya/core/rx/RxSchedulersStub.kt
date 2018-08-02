package ru.popov.bodya.core.rx


import io.reactivex.Scheduler
import io.reactivex.annotations.NonNull
import io.reactivex.schedulers.Schedulers

/**
 * @author popovbodya
 */

class RxSchedulersStub : RxSchedulers {

    override fun mainThreadScheduler(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun ioScheduler(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun computationScheduler(): Scheduler {
        return Schedulers.trampoline()
    }
}
