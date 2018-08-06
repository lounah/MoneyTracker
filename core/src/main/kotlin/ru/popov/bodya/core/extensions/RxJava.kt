package ru.popov.bodya.core.extensions

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author popovbodya
 */
fun Disposable.connect(composite: CompositeDisposable) {
    composite.add(this)
}
