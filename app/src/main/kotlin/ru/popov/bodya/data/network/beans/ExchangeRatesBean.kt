package ru.popov.bodya.data.network.beans

import com.google.gson.annotations.SerializedName

/**
 * @author popovbodya
 */
data class ExchangeRatesBean(@SerializedName("success") val success: Boolean,
                             @SerializedName("timestamp") val timestamp: Long,
                             @SerializedName("base") val base: String,
                             @SerializedName("date") val date: String,
                             @SerializedName("rates") val rates: RatesBean)