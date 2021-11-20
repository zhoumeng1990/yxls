package com.coder.little.interceptors

import com.coder.little.FloatViewModel
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Zero on 2021/11/18.
 *
 */
class HttpInterceptor :Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        FloatViewModel.chainDataList.add(chain)
        FloatViewModel.chainData.postValue(FloatViewModel.chainDataList)
        val request = chain.request()
        return chain.proceed(request)
    }
}