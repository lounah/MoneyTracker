package ru.popov.bodya.core.rx

import io.reactivex.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

/**
 * @author popovbodya
 */
class RxSchedulersTransformerImplTest {

    private lateinit var rxSchedulersTransformer: RxSchedulersTransformer
    private lateinit var rxSchedulers: RxSchedulers

    @Before
    fun setUp() {
        rxSchedulers = spy(RxSchedulersStub())
        rxSchedulersTransformer = RxSchedulersTransformerImpl(rxSchedulers)
    }

    @Test
    fun testGetIOToMainTransformer() {
        Observable.just(1)
                .compose(rxSchedulersTransformer.ioToMainTransformer())
        verifyRxSchedulersIOMain()
    }

    @Test
    fun testGetIOToMainTransformerSingle() {
        Single.just(1)
                .compose(rxSchedulersTransformer.ioToMainTransformerSingle())
        verifyRxSchedulersIOMain()
    }

    @Test
    fun testGetIOToMainTransformerMaybe() {
        Maybe.just(1)
                .compose(rxSchedulersTransformer.ioToMainTransformerMaybe())
        verifyRxSchedulersIOMain()
    }

    @Test
    fun testGetIOToMainTransformerCompletable() {
        Completable.complete()
                .compose(rxSchedulersTransformer.ioToMainTransformerCompletable())
        verifyRxSchedulersIOMain()
    }

    @Test
    fun testGetIOToMainTransformerFlowable() {
        Flowable.just(1)
                .compose(rxSchedulersTransformer.ioToMainTransformerFlowable())
        verifyRxSchedulersIOMain()
    }

    @Test
    fun testGetComputationToMainTransformer() {
        Observable.just(1)
                .compose(rxSchedulersTransformer.computationToMainTransformer())
        verifyRxSchedulersComputationMain()
    }


    @Test
    fun testGetComputationToMainTransformerSingle() {
        Single.just(1)
                .compose(rxSchedulersTransformer.computationToMainTransformerSingle())
        verifyRxSchedulersComputationMain()
    }

    private fun verifyRxSchedulersIOMain() {
        verify<RxSchedulers>(rxSchedulers).ioScheduler()
        verify<RxSchedulers>(rxSchedulers).mainThreadScheduler()
        verifyNoMoreInteractions(rxSchedulers)
    }

    private fun verifyRxSchedulersComputationMain() {
        verify<RxSchedulers>(rxSchedulers).computationScheduler()
        verify<RxSchedulers>(rxSchedulers).mainThreadScheduler()
        verifyNoMoreInteractions(rxSchedulers)
    }
}