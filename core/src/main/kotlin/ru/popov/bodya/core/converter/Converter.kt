package ru.popov.bodya.core.converter

/**
 * @author popovbodya
 */

interface Converter<F, T> : OneWayConverter<F, T> {

    fun reverse(to: T): F

}
