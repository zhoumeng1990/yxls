package com.coder.framework.net

import com.coder.hydf.base.BuildConfig
import com.coder.framework.interceptor.HeaderInterceptor
import com.coder.framework.interceptor.LogInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * Created by Zero on 2020/05/30.
 * OkHttpClient 单例
 */
class MyOkHttpClient private constructor() {

    companion object {

        private var singleton: OkHttpClient? = null
        private const val timeOut = 10L
        /**
         * 设置cookie持久化
         */
        val instance: OkHttpClient
            get() {
                if (singleton == null) {
                    synchronized(MyOkHttpClient::class.java) {
                        if (singleton == null) {

                            val okHttpClientBuilder = OkHttpClient.Builder()

                            okHttpClientBuilder.connectTimeout(timeOut, TimeUnit.SECONDS)
                            okHttpClientBuilder.readTimeout(timeOut, TimeUnit.SECONDS)

                            /**
                             * debug模式下打印json
                             */
                            if (BuildConfig.DEBUG) {
                                val logging = LogInterceptor()
                                okHttpClientBuilder.addInterceptor(logging)
                            }

                            okHttpClientBuilder.addInterceptor(HeaderInterceptor())
                            singleton = okHttpClientBuilder.build()
                        }
                    }
                }
                return singleton!!
            }
    }
}