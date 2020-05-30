package com.coder.yxls.activitys

import androidx.lifecycle.Observer
import com.coder.framework.base.BaseActivity
import com.coder.yxls.R
import com.coder.yxls.vm.MainVM
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    override fun layoutId() = R.layout.activity_main

    private val mainVM : MainVM by viewModel()

    override fun initView() {
        val mapData = HashMap<String,String>()
        mapData["username"] = "admin"
        mapData["password"] = "123456"
        tvTitle.setOnClickListener { mainVM.login(mapData,this) }
    }

    override fun initData() {
    }

    override fun update() {
        mainVM.loginData.observe(this, Observer {

        })
    }
}
