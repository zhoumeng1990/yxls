package com.coder.framework.view

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.alibaba.android.arouter.launcher.ARouter
import com.coder.framework.ICommonPop
import com.coder.framework.util.ARouterConstance
import com.coder.framework.util.SharedPrefsManager
import com.coder.hydf.base.R
import kotlinx.android.synthetic.main.pop_common_login.view.*

/**
 * Created by Zero on 2020/05/30.
 *
 */
class CommonLoginPop(private val context: Context, callback: ICommonPop,
                     leftText: String = "退出", rightText: String = "重新登录",
                     title:String="") :
    PopupWindow(context) {
    private var view: View = View.inflate(context, R.layout.pop_common_login, null)

    init {
        contentView = view
        contentView.tvTitle.text = title
        if (TextUtils.isEmpty(leftText)) {
            contentView.tvLeft.visibility = View.GONE
            contentView.viewLine.visibility = View.GONE
        }
        contentView.tvLeft.text = leftText
        contentView.tvLeft.setOnClickListener {
//            System.exit(1)
            callback.leftClicked()
            dismiss()
//            Process.killProcess(Process.myPid())
            val activityManager =
                context.applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

            // 2\. 通过ActivityManager获取任务栈
            val appTaskList = activityManager.appTasks

            // 3\. 逐个关闭Activity
            for (appTask in appTaskList) {
                appTask.finishAndRemoveTask()
            }
        }

        contentView.tvRight.text = rightText
        contentView.tvRight.setOnClickListener {
            SharedPrefsManager(context).saveToken("")
            ARouter.getInstance()
                .build(ARouterConstance.ACTIVITY_LOGIN)
                .navigation(context)
            callback.rightClicked()
            dismiss()
        }

        // 设置动画效果
        animationStyle = R.style.popwindow_anim_style
        width = WindowManager.LayoutParams.MATCH_PARENT
        height = WindowManager.LayoutParams.MATCH_PARENT
        // 设置可触
        isFocusable = true
        val dw = ColorDrawable(0xFFFFFF)
        setBackgroundDrawable(dw)
    }

    fun show() {
        if (context is Activity && !context.isFinishing) {
            showAtLocation(view, Gravity.CENTER, 0, 0)
        }
    }
}