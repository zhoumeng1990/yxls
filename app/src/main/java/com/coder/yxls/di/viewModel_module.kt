package com.coder.yxls.di

import com.coder.yxls.vm.MainVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Zero on 2019/12/2.
 *
 */
val viewModelModule = module {

    viewModel { MainVM(get()) }

}