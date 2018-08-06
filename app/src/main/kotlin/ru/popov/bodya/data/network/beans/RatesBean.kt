package ru.popov.bodya.data.network.beans

import com.google.gson.annotations.SerializedName

/**
 * @author popovbodya
 */
data class RatesBean(
        @SerializedName("USD") val usd: Double,
        @SerializedName("EUR") val eur: Double) {


}