package com.coder.little.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import com.coder.little.R

/**
 * Created by Zero on 2021/11/18.
 *
 */
object ExecuteUtils {
    /**
     * 应用界面内显示悬浮球
     */
    private fun showCurrentWindow(
        context: FragmentActivity,
        @LayoutRes layoutId: Int? = null,
        block: () -> Unit
    ) {
        val layoutParam = WindowManager.LayoutParams().apply {
            //设置大小 自适应
            width = WRAP_CONTENT
            height = WRAP_CONTENT
            flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        }
        // 新建悬浮窗控件
        val floatRootView: View?
        if (layoutId == null) {
            floatRootView = LayoutInflater.from(context).inflate(R.layout.activity_float_item, null)
            floatRootView.findViewById<ImageView>(R.id.img_chain).setOnClickListener{

            }
        } else {
            floatRootView =LayoutInflater.from(context).inflate(layoutId, null)
            block.invoke()
        }
        //设置拖动事件
        floatRootView?.apply {
            setOnTouchListener(ItemViewTouchListener(layoutParam, context.windowManager))
            // 将悬浮窗控件添加到WindowManager
            context.windowManager.addView(floatRootView, layoutParam)
        }
    }
}