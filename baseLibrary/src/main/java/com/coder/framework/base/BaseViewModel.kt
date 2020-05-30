package com.coder.framework.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren

/**
 * Created by Zero on 2020/05/30.
 *
 */
open class BaseViewModel :ViewModel(), LifecycleObserver{

    protected val ioScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onCleared() {
        super.onCleared()

        ioScope.coroutineContext.cancelChildren(null)
    }
}