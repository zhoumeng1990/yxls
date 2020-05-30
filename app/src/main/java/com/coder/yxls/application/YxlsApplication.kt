package com.coder.yxls.application

import com.coder.framework.base.BaseApplication
import com.coder.yxls.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Created by Zero on 2020/05/30.
 *
 */
class YxlsApplication: BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@YxlsApplication)
            androidFileProperties()
            modules(appComponent)
        }
    }
}