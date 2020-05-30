package com.coder.framework.base

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.coder.framework.view.NoNetWorkPopup
import com.coder.framework.view.ZMDialogLoading
import com.coder.hydf.base.R
import java.util.*

/**
 * Created by Zero on 2020/05/30.
 *
 */
abstract class BaseFragment : Fragment(),BaseInterface {
    protected lateinit var rootView: View
    protected lateinit var mContext: Context
    protected lateinit var mActivity: Activity

    private val mapReceiver = HashMap<String, BroadcastReceiver>()
    private var receiver: BroadcastReceiver? = null
    private var filter: IntentFilter? = null

    private var mVisible: Boolean = false
    private val dialogLoading by lazy { ZMDialogLoading(mContext) }

    protected var showNotNetWorkPop = true
    protected val noNetWorkPopup by lazy { NoNetWorkPopup(mContext,this) }

    protected var isLazyLoad = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(layoutId(), container, false)
        initData(rootView)
        return rootView
    }

    abstract fun layoutId(): Int

    abstract fun initData(rootView: View)

    open fun lazyLoad() {}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
        this.mActivity = activity ?: throw NullPointerException("Activity丢失")
    }

    override fun onResume() {
        super.onResume()
        mVisible = true
        if (!isLazyLoad) {
            isLazyLoad = !isLazyLoad
            lazyLoad()
        }
    }

    override fun onPause() {
        super.onPause()
        mVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mapReceiver.size > 0 && mapReceiver[javaClass.simpleName] != null) {
            LocalBroadcastManager.getInstance(mContext).unregisterReceiver(mapReceiver[javaClass.simpleName]!!)
        }
    }

    fun showProcessDialog(resId: Int = 0, visible: Int = View.VISIBLE) {
        when (resId) {
            0 -> showProcessDialog(R.string.loading, visible)
            else -> showProcessDialog(getString(resId), visible)
        }
    }

    private fun showProcessDialog(msg: String, visible: Int = View.VISIBLE) {
        if (mVisible) {
            dialogLoading.setText(msg).setImgVisibility(visible)
            dialogLoading.show()
        }
    }

    fun hideProcessDialog() {
        if (mVisible) {
            try {
                dialogLoading.dismiss()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }


    /**
     * 传入需要过滤的action不定参数
     *
     * @param filterActions
     */
    protected fun registerReceiver(@NonNull vararg filterActions: String) {
        filter = if (filter == null) IntentFilter() else filter
        for (action in filterActions) {
            filter!!.addAction(action)
        }
        registerReceiver(filter!!)
    }

    /**
     * 传入filter，注册广播
     *
     * @param filter
     */
    protected fun registerReceiver(@NonNull filter: IntentFilter) {
        receiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                executeReceiver(context, intent)
                executeReceiver(if (intent.action == null) "" else intent.action)
            }
        }
        LocalBroadcastManager.getInstance(mContext).registerReceiver(
            receiver!!, filter
        )
        mapReceiver[javaClass.simpleName] = receiver!!
    }

    /**
     * 接收到广播
     *
     * @param context
     * @param intent
     */
    open protected fun executeReceiver(context: Context, intent: Intent) {

    }

    /**
     * 接收到广播
     *
     * @param action
     */
    open protected fun executeReceiver(@NonNull action: String?) {

    }

    /**
     * 发送本地广播
     * @param actions 广播集合
     */
    protected fun sendLocalBroadCast(vararg actions: String) {
        for (action in actions) {
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(Intent(action))
        }
    }

    open fun notHaveNetWork() {
        if (showNotNetWorkPop) {
            if (activity != null && !activity?.isFinishing!!) {
                noNetWorkPopup.show()
            }
        }
    }

    override fun callback() {
        lazyLoad()
    }
}