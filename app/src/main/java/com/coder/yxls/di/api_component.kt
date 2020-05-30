package com.coder.yxls.di

import com.coder.framework.net.RetrofitFactory
import com.coder.yxls.api.MainApi
import org.koin.dsl.module

/**
 * Created by Zero on 2019/12/2.
 *
 */
val apiComponent = module {
    single { RetrofitFactory.create(MainApi::class.java) }

}