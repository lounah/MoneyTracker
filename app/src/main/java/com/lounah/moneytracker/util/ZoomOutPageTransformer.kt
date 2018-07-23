package com.lounah.moneytracker.util

import android.support.v4.view.ViewPager
import android.view.View

class ZoomOutPageTransformer : ViewPager.PageTransformer {

    override fun transformPage(view: View, position: Float) {
        val pageWidth = view.width
        val pageHeight = view.height

        when {
            position < -1 -> view.alpha = 0f
            position <= 1 -> {

                val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                val verticalMargin = pageHeight * (1 - scaleFactor) / 3
                val horizontalMargin = pageWidth * (1 - scaleFactor) / 3

                if (position < 0)
                    view.translationX = horizontalMargin - verticalMargin / 3
                else
                    view.translationX = -horizontalMargin + verticalMargin / 3

                view.scaleX = scaleFactor
                view.scaleY = scaleFactor

                view.alpha = MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA)

            }
            else -> view.alpha = 0f
        }
    }

    companion object {
        private const val MIN_SCALE = 0.85f
        private const val MIN_ALPHA = 0.5f
    }
}