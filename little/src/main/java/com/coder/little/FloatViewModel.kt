package com.coder.little

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.Interceptor

/**
 * Created by Zero on 2021/11/18.
 *
 */
object FloatViewModel:ViewModel() {
    var chainData = MutableLiveData<ArrayList<Interceptor.Chain>>()
}