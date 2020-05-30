package com.coder.framework.base

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Created by Zero on 2020/05/30.
 *
 */
abstract class BaseViewModelActivity<VDB: ViewDataBinding> : BaseActivity() {
//    abstract class BaseViewModelActivity<VM : BaseViewModel,VDB:ViewDataBinding> : BaseActivity() {


//    protected lateinit var viewModel: VM
//
//    private var providerVMClass: Class<VM>  = this.providerVMClass()
//
    protected val binding : VDB by lazy { DataBindingUtil.setContentView<VDB>(this,layoutId()) }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        initVM()
//        super.onCreate(savedInstanceState)
//
////        startObserve()
//    }
//
//    private fun initVM() {
//        providerVMClass.let {
//            viewModel = ViewModelProviders.of(this).get(it)
//            lifecycle.addObserver(viewModel)
//        }
//    }
//
//    abstract fun providerVMClass():Class<VM>
//
//    override fun onDestroy() {
//        super.onDestroy()
//        lifecycle.removeObserver(viewModel)
//    }
}