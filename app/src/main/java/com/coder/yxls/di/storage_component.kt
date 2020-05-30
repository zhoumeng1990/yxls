package com.coder.hydf.di

import com.coder.framework.util.SharedPrefsManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Created by Zero on 2020/2/11.
 *
 */
val storageModule = module {
    single { SharedPrefsManager(androidContext()) }
}