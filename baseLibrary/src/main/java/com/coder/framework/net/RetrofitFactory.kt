package com.coder.framework.net

import com.coder.framework.api.ApiService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Zero on 2020/05/30.
 * Retrofit 单例与工厂
 */
object RetrofitFactory {

    val instance: ApiService by lazy { create(ApiService::class.java) }

    private val moshi = Moshi.Builder()
        .add(NullToEmptyStringAdapter)
//        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(HttpConfig.getBaseUrl())
//            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
//          .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(MyOkHttpClient.instance)
            .build()
    }

    fun <T> create(apiService: Class<T>): T {
        return retrofit.create(apiService)
    }
}

object NullToEmptyStringAdapter {
    @FromJson
    fun fromJson(reader: JsonReader): String {
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextString()
        }
        reader.nextNull<Unit>()
        return ""
    }
}