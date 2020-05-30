package com.coder.yxls.di

import com.coder.yxls.reposition.MainReposition
import org.koin.dsl.module

/**
 * Created by Zero on 2019/12/2.
 *
 */
val repositoryModule = module {
    factory { MainReposition(get()) }

}