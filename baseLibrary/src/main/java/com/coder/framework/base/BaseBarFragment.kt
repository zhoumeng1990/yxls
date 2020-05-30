//package com.coder.framework.base
//
//import android.app.Activity
//import android.content.Context
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import me.yokeyword.fragmentation.SupportFragment
//
///**
// * Created by Zero on 2020/05/30.
// *
// */
//abstract class BaseBarFragment : SupportFragment(){
//    protected lateinit var rootView: View
//    protected lateinit var mContext: Context
//    protected lateinit var mActivity: Activity
//    private var isLazyLoad = false
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        rootView = inflater.inflate(layoutId(), container, false)
//        initData(rootView)
//        return rootView
//    }
//
//    abstract fun layoutId(): Int
//
//    abstract fun initData(rootView: View)
//
//    open fun lazyLoad(){}
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        this.mContext = context
//        this.mActivity = activity ?: throw NullPointerException("Activity丢失")
//    }
//
//    override fun onResume() {
//        super.onResume()
//        if(!isLazyLoad){
//            isLazyLoad = !isLazyLoad
//            lazyLoad()
//        }
//    }
//}