package ru.popov.bodya.presentation.init

import android.arch.lifecycle.MutableLiveData
import io.reactivex.functions.Consumer
import ru.popov.bodya.core.extensions.connect
import ru.popov.bodya.core.mvwhatever.AppViewModel
import ru.popov.bodya.core.rx.RxSchedulersTransformer
import ru.popov.bodya.domain.currency.CurrencyInteractor
import ru.popov.bodya.presentation.common.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 *  @author popovbodya
 */
class InitViewModel @Inject constructor(private val currencyInteractor: CurrencyInteractor,
                                        private val router: Router,
                                        private val rxSchedulersTransformer: RxSchedulersTransformer) : AppViewModel() {

    val isAllReadyLiveData = MutableLiveData<Boolean>()

    fun fetchInitialData() {
        currencyInteractor.getExchangeRate()
                .compose(rxSchedulersTransformer.ioToMainTransformerSingle())
                .subscribe(Consumer { isAllReadyLiveData.postValue(true) })
                .connect(compositeDisposable)
    }

    fun onEverythingIsReady() {
        router.replaceScreen(Screens.ACCOUNT_ACTIVITY)
    }

}