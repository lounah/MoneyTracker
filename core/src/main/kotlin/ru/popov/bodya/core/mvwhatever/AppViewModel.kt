package ru.popov.bodya.core.mvwhatever

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * @author popovbodya
 */

open class AppViewModel: ViewModel() {

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}
