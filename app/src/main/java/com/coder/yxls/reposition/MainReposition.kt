package com.coder.yxls.reposition

import android.content.Context
import com.coder.framework.base.BaseRepository
import com.coder.yxls.api.MainApi

/**
 * Created by Zero on 2020/05/30.
 *
 */
class MainReposition(private val api :MainApi) :BaseRepository(){
    suspend fun login(map: HashMap<String, String>, context: Context?) =
        safeApiCall(call = { api.login(map) }, context = context)
}