package com.coder.framework.base

import android.content.Context
import android.widget.Toast
import com.coder.framework.ICommonPop
import com.coder.framework.util.SharedPrefsManager
import com.coder.framework.view.CommonLoginPop
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException

/**
 * Created by Zero on 2020/05/30.
 *
 */
open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): T? {
        val result: BaseResult<T> = safeApiResult(call)
        var data: T? = null

        when (result) {
            is BaseResult.Success -> data = result.data

            is BaseResult.Error -> {
                println(" exception - ${result.exception}")
            }
        }
        return data
    }

    suspend fun <T : BaseData<*>> safeApiCall(call: suspend () -> Response<T>, context: Context? = null): T? {
        val result: BaseResult<T> = safeApiResult(call)
        var data: T? = null

        when (result) {
            is BaseResult.Success -> {
                data = result.data
                if (data.code == 2004 || data.code == 2005 || data.code == 2008) {
                    if (context != null) {
                        SharedPrefsManager(context).saveToken("")
                        var leftText = ""
                        if (data.code == 2008) {
                            leftText = "退出"
                        }

                        val popLogin = CommonLoginPop(
                            context,
                            title = "您的账号${SharedPrefsManager(context).getPhoneNumber().showPhoneNumber()}已在另一台设备登录，请确认账号安全！",
                            callback = object : ICommonPop {
                                override fun leftClicked() {
                                }

                                override fun rightClicked() {

                                }
                            }, leftText = leftText
                        )
                        popLogin.show()
                    }
                }
            }

            is BaseResult.Error -> {
                println(" exception - ${result.exception}")
//                Toast.makeText(context, "${result.exception}", Toast.LENGTH_SHORT).show()
                when (result.exception) {
                    is ConnectException -> {
                        when (context) {
                            is BaseActivity -> {
                                context.notHaveNetWork()
                            }
                            else -> {
                                if (data is BaseData<*>) {
                                    println("...")
                                }
                                return data
                            }
                        }
                    }
                    is UnknownHostException -> {
                        when (context) {
                            is BaseActivity -> context.toast("请检查您的网络")
                            else -> Toast.makeText(context, "请检查您的网络", Toast.LENGTH_SHORT).show()
                        }
                    }
                    is IOException -> {
                    }
                }
            }
        }
        return data
    }

//    suspend fun <T : BaseData<T>> Call<T>.request(): T {
//        return suspendCoroutine { continuation ->
//            enqueue(object : Callback<T> {
//                override fun onFailure(call: Call<T>, t: Throwable) {
//                    continuation.resumeWithException(t)
//                }
//
//                override fun onResponse(call: Call<T>, response: Response<T>) {
//                    val body = response.body()
//                    if (body != null) continuation.resume(body)
//                    else continuation.resumeWithException(RuntimeException("response body is null"))
//                }
//            })
//        }
//    }

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? {
        val result: BaseResult<T> = safeApiResult(call, errorMessage)
        var data: T? = null

        when (result) {
            is BaseResult.Success -> data = result.data

            is BaseResult.Error -> {
                println("$errorMessage & exception - ${result.exception}")
            }
        }

        return data
    }

    private suspend fun <T : Any> safeApiResult(
        call: suspend () -> Response<T>
    ): BaseResult<T> {
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                return BaseResult.Success(response.body()!!)
            } else {
                println("isFailure: " + response.message())
            }
        } catch (ce: ConnectException) {
            return BaseResult.Error(ConnectException("ConnectException"))
        } catch (ce: UnknownHostException) {
            return BaseResult.Error(UnknownHostException("UnknownHostException"))
        } catch (e: IOException) {
            println(e.message)
        }

        return BaseResult.Error(IOException("IOException"))
    }

    private suspend fun <T : Any> safeApiResult(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): BaseResult<T> {
        try {
            val response = call.invoke()
            if (response.isSuccessful) {
                return BaseResult.Success(response.body()!!)
            } else {
                println("isFailure: " + response.message())
            }
        } catch (e: IOException) {
            println(e.message)
        }

        return BaseResult.Error(IOException("ERROR* - $errorMessage"))
    }
}

fun String.showPhoneNumber(): String {
    if (this.length < 10)
        return ""
    return this.substring(0, 3) + "****" + this.substring(this.length - 4, this.length)
}