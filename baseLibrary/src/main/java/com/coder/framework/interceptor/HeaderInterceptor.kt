package com.coder.framework.interceptor

import com.coder.framework.util.Fields
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Zero on 2020/05/30.
 * 头文件拦截器
 */
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("User-Agent", "Zero")
            .addHeader("Accept","application/json")
            .addHeader("charset", "utf-8")
            .addHeader("token", Fields.token)
            .addHeader("terminal","Android")
            .build()
        return chain.proceed(request)
    }
}