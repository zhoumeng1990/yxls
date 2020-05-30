package com.coder.yxls.di

import com.coder.hydf.di.storageModule

val appComponent = listOf(apiComponent, storageModule, repositoryModule, viewModelModule)