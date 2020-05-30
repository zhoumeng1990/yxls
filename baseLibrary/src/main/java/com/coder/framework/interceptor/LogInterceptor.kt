package com.coder.framework.interceptor

import android.net.Uri
import android.util.Log
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import java.util.*

/**
 * Created by Zero on 2020/05/30.
 * 打印网络请求时传输的字段还有返回的json数据
 */
class LogInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        val t1 = System.nanoTime()
        val response = chain.proceed(chain.request())
        val t2 = System.nanoTime()

        val sb = StringBuffer()
        sb.append(request.method).append("\n")
        val url = request.url.toString().split("\\?".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        sb.append(url[0]).append("\n")
        if (url.size == 2) {
            val params = url[1].split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (param in params) {
                sb.append(Uri.decode(param)).append("\n")
            }
        }

        if (request.body is FormBody) {
            val postParams = request.body as FormBody?
            if (postParams != null) {
                sb.append("post:").append("\n")
                val size = postParams.size
                for (i in 0 until size) {
                    sb.append(
                        postParams.encodedName(i) + "=" + java.net.URLDecoder.decode(
                            postParams.encodedValue(i),
                            "utf-8"
                        )
                    ).append("\n")
                }
            }
        }

        val mediaType = response.body!!.contentType()
        val content = response.body!!.string()
        Log.v(
            TAG,
            String.format(Locale.getDefault(), "%s cost %.1fms%n%s", sb.toString(), (t2 - t1) / 1e6,
                format(content)
            )
        )
        //格式化打印json
        //       Log.v(TAG, String.format(Locale.getDefault(), "%s cost %.1fms%n%s", sb.toString(), (t2 - t1) / 1e6d, format(content)));
        //        Log.v(TAG, String.format(Locale.getDefault(), "%s cost %.1fms%n%s", sb.toString(), (t2 - t1) / 1e6d, content));
        return response.newBuilder()
//            .body(okhttp3.ResponseBody.create(mediaType, content))
            .body(content.toResponseBody(mediaType))
            .build()
    }

    companion object {
        private val TAG = LogInterceptor::class.java.simpleName

        fun format(jsonStr: String): String {

            var level = 0
            val jsonForMatStr = StringBuffer()
            for (i in 0 until jsonStr.length) {
                val c = jsonStr[i]
                if (level > 0 && '\n' == jsonForMatStr[jsonForMatStr.length - 1]) {
                    jsonForMatStr.append(getLevelStr(level))
                }
                when (c) {
                    '{', '[' -> {
                        jsonForMatStr.append(c + "\n")
                        level++
                    }
                    ',' -> jsonForMatStr.append(c + "\n")
                    '}', ']' -> {
                        jsonForMatStr.append("\n")
                        level--
                        jsonForMatStr.append(getLevelStr(level))
                        jsonForMatStr.append(c)
                    }
                    else -> jsonForMatStr.append(c)
                }
            }
            return jsonForMatStr.toString()
        }

        private fun getLevelStr(level: Int): String {
            val levelStr = StringBuffer()
            for (levelI in 0 until level) {
                levelStr.append("\t")
            }
            return levelStr.toString()
        }
    }
}