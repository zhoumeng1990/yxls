package com.coder.little.utils

import android.view.MotionEvent
import android.view.View
import android.view.WindowManager

/**
 * Created by Zero on 202111/14.
 * 处理悬浮窗拖动更新位置
 */
class ItemViewTouchListener(private val layoutParams: WindowManager.LayoutParams, private val windowManager: WindowManager) :
    View.OnTouchListener {
    private var x = 0
    private var y = 0
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                x = motionEvent.rawX.toInt()
                y = motionEvent.rawY.toInt()

            }
            MotionEvent.ACTION_MOVE -> {
                val nowX = motionEvent.rawX.toInt()
                val nowY = motionEvent.rawY.toInt()
                val movedX = nowX - x
                val movedY = nowY - y
                x = nowX
                y = nowY
                layoutParams.apply {
                    x += movedX
                    y += movedY
                }
                //更新悬浮球控件位置
                windowManager.updateViewLayout(view, layoutParams)
            }
            else -> {

            }
        }
        return false
    }
}