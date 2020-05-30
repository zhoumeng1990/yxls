package com.coder.framework.base

/**
 * Created by Zero on 2020/05/30.
 *
 */
data class BaseData<T>(
    var code: Int,
    var `data`: T?,
    var msg: String
)