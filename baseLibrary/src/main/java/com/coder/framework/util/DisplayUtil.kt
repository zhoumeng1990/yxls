package com.coder.framework.util

import android.content.Context

/**
 * Created by Zero on 2020/05/30.
 * dp,sp 和 px 转换的辅助类
 */
class DisplayUtil private constructor() {
    init {
        /* cannot be instantiated */
        throw UnsupportedOperationException("cannot be instantiated")
    }

    companion object {

        /**
         * 将px值转换为dip或dp值，保证尺寸大小不变
         * DisplayMetrics类中属性density
         */
        fun px2dip(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        /**
         * 将dip或dp值转换为px值，保证尺寸大小不变
         * DisplayMetrics类中属性density
         */
        fun dip2px(context: Context, dipValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dipValue * scale + 0.5f).toInt()
        }

        /**
         * 将px值转换为sp值，保证文字大小不变
         * DisplayMetrics类中属性scaledDensity
         */
        fun px2sp(context: Context, pxValue: Float): Int {
            val fontScale = context.resources.displayMetrics.scaledDensity
            return (pxValue / fontScale + 0.5f).toInt()
        }

        /**
         * 将sp值转换为px值，保证文字大小不变
         * DisplayMetrics类中属性scaledDensity
         */
        fun sp2px(context: Context, spValue: Float): Int {
            val fontScale = context.resources.displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }
    }
}
