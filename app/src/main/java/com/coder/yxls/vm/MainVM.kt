package com.coder.yxls.vm

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.coder.framework.base.BaseData
import com.coder.framework.base.BaseViewModel
import com.coder.yxls.reposition.MainReposition
import kotlinx.coroutines.launch

/**
 * Created by Zero on 2020/5/30.
 *
 */
class MainVM(private val reposition: MainReposition) : BaseViewModel() {

    val loginData = MutableLiveData<BaseData<Any>>()

    fun login(map: HashMap<String, String>, context: Context?) {
        ioScope.launch {
            loginData.postValue(reposition.login(map, context))
        }
    }
}