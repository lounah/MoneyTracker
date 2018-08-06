package ru.popov.bodya.core.converter

/**
 * @author popovbodya
 */

interface OneWayConverter<F, T> {

    fun convert(from: F): T

}
