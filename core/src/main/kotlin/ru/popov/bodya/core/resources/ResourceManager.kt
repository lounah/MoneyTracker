package ru.popov.bodya.core.resources

import android.content.Context
import android.content.pm.PackageManager
import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat

/**
 * @author popovbodya
 */

class ResourceManager(private val context: Context) {

    val packageManager: PackageManager
        get() = context.packageManager

    val packageName: String
        get() = context.packageName

    fun getString(@StringRes resId: Int): String {
        return context.getString(resId)
    }

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return context.resources.getString(resId, *formatArgs)
    }

    fun getColorResource(@ColorRes resId: Int): Int {
        return ContextCompat.getColor(context, resId)
    }
}
