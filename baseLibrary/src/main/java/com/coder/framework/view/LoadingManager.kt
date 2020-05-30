package com.coder.framework.view

import android.content.Context

/**
 * Created by Zero on 2020/05/30.
 *
 */
class LoadingManager private constructor(){

    private var mContext: Context? = null
    private var loadingView: LoadingView? = null

    companion object {
        val instance: LoadingManager by lazy {
            LoadingManager()
        }
    }

    fun createDialog(context: Context, title: String): LoadingView {
        if(mContext == context){
            if(loadingView != null){
                loadingView?.setTitle(title)
                return loadingView!!
            }
        }
        loadingView = null
        mContext = context
        loadingView = LoadingView(mContext!!, title)
        return loadingView!!
    }

}