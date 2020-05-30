package com.coder.framework.view

import android.app.Dialog
import android.content.Context
import android.view.KeyEvent
import com.coder.hydf.base.R
import kotlinx.android.synthetic.main.loading_dialog.*

/**
 * Created by Zero on 2020/05/30.
 *
 */
class LoadingView(context: Context, content: String) :
    Dialog(context, R.style.CustomLoadingDialog) {

    init {
        setContentView(R.layout.loading_dialog)
        content_view.text = content
        setCanceledOnTouchOutside(true)
//        backgroundAlpha(1f)
        val dm = context.resources.displayMetrics
        window?.attributes?.width = (dm.widthPixels * 0.6).toInt()
        window?.attributes?.height = (dm.widthPixels * 0.6 * 0.6).toInt()
        setCancelable(false)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                if (isShowing) {
                    dismiss()
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun dismiss() {
        if(isShowing){
            super.dismiss()
        }
    }

    override fun setTitle(title: CharSequence?) {
        content_view.text = title
    }

    private fun backgroundAlpha(alpha: Float) {
        window?.attributes?.alpha = alpha
    }

}