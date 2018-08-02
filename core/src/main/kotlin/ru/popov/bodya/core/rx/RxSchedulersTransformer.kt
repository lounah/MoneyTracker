package ru.popov.bodya.core.rx

import io.reactivex.CompletableTransformer
import io.reactivex.FlowableTransformer
import io.reactivex.MaybeTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer

/**
 * @author popovbodya
 */

interface RxSchedulersTransformer {

    fun <T> ioToMainTransformer(): ObservableTransformer<T, T>

    fun <T> ioToMainTransformerSingle(): SingleTransformer<T, T>

    fun <T> ioToMainTransformerMaybe(): MaybeTransformer<T, T>

    fun ioToMainTransformerCompletable(): CompletableTransformer

    fun <T> ioToMainTransformerFlowable(): FlowableTransformer<T, T>

    fun <T> computationToMainTransformer(): ObservableTransformer<T, T>

    fun <T> computationToMainTransformerSingle(): SingleTransformer<T, T>
}
