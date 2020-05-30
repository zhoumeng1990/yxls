package com.coder.framework.net

import com.coder.hydf.base.BuildConfig

/**
 * Created by Zero on 2020/05/30.
 *
 */
class HttpConfig {
    companion object {
        private const val isDebug = BuildConfig.IS_DEBUG
        private const val devlopeUrl = "http://gps.mapleleaf.ren:8000/"
        private const val releaseUrl = "http://gps.mapleleaf.ren:8000/"

        fun getBaseUrl(): String = if (isDebug) {
            devlopeUrl
        } else {
            releaseUrl
        }
    }
}