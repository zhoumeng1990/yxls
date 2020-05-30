package com.coder.yxls.api

import com.coder.framework.base.BaseData
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.QueryMap

/**
 * Created by Zero on 2020/5/30.
 *
 */
interface MainApi {
    @POST("login")
    suspend fun login(@QueryMap map: Map<String, String>): Response<BaseData<Any>>
}