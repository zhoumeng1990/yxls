package com.coder.framework.base

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

/**
 * Created by Zero on 2020/05/30.
 *
 */
open class BaseApplication :Application() {
    protected var isDebugARouter = true
    override fun onCreate() {
        super.onCreate()
        if (isDebugARouter){
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
    }
}