package ru.popov.bodya.core.rx

import io.reactivex.*

/**
 * @author popovbodya
 */

class RxSchedulersTransformerImpl(private val rxSchedulers: RxSchedulers) : RxSchedulersTransformer {

    override fun <T> ioToMainTransformer(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(rxSchedulers.ioScheduler())
                    .observeOn(rxSchedulers.mainThreadScheduler())
        }
    }

    override fun <T> ioToMainTransformerSingle(): SingleTransformer<T, T> {
        return SingleTransformer {
            it.subscribeOn(rxSchedulers.ioScheduler())
                    .observeOn(rxSchedulers.mainThreadScheduler())
        }
    }

    override fun <T> ioToMainTransformerMaybe(): MaybeTransformer<T, T> {
        return MaybeTransformer {
            it.subscribeOn(rxSchedulers.ioScheduler())
                    .observeOn(rxSchedulers.mainThreadScheduler())
        }
    }

    override fun ioToMainTransformerCompletable(): CompletableTransformer {
        return CompletableTransformer {
            it.subscribeOn(rxSchedulers.ioScheduler())
                    .observeOn(rxSchedulers.mainThreadScheduler())
        }
    }

    override fun <T> ioToMainTransformerFlowable(): FlowableTransformer<T, T> {
        return FlowableTransformer {
            it.subscribeOn(rxSchedulers.ioScheduler())
                    .observeOn(rxSchedulers.mainThreadScheduler())
        }
    }

    override fun <T> computationToMainTransformer(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(rxSchedulers.computationScheduler())
                    .observeOn(rxSchedulers.mainThreadScheduler())
        }
    }

    override fun <T> computationToMainTransformerSingle(): SingleTransformer<T, T> {
        return SingleTransformer {
            it.subscribeOn(rxSchedulers.computationScheduler())
                    .observeOn(rxSchedulers.mainThreadScheduler())
        }
    }
}
