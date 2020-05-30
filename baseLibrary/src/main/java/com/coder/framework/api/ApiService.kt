package com.coder.framework.api

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * Created by Zero on 2020/05/30.
 *
 */
interface ApiService {

    @GET
    fun executeGetAsync(@Url url:String, @QueryMap map:Map<String,@JvmSuppressWildcards Any>):@JvmSuppressWildcards Deferred<ResponseBody>

    @GET
    suspend fun executeGet(@Url url:String, @QueryMap map:Map<String, @JvmSuppressWildcards Any>):@JvmSuppressWildcards ResponseBody


    @FormUrlEncoded
    @POST
    fun executePostAsync(@Url url:String, @FieldMap map:Map<String, Any>): Deferred<ResponseBody>

    @FormUrlEncoded
    @POST
    suspend fun executePost(@Url url:String, @FieldMap map:Map<String, Any>): ResponseBody
}