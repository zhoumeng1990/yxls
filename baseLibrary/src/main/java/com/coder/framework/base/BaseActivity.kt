package com.coder.framework.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.IBinder
import android.text.TextUtils
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.alibaba.android.arouter.launcher.ARouter
import com.coder.framework.util.DisplayUtil
import com.coder.framework.view.NoNetWorkPopup
import com.coder.framework.view.ZMDialogLoading
import com.coder.hydf.base.R
import kotlinx.android.synthetic.main.include_toolbar.*
import org.jetbrains.anko.find
import org.jetbrains.anko.textColorResource
import pub.devrel.easypermissions.EasyPermissions
import java.util.*

/**
 * Created by Zero on 2020/05/30.
 *
 */
abstract class BaseActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks, BaseInterface {
    companion object {
        const val PERMISSION_CODE = 0X01
    }

    protected var mToolbar: Toolbar? = null
    protected var tvToolbarTitle: TextView? = null
    protected var tvToolbarRightTitle: TextView? = null

    private val mapReceiver = HashMap<String, BroadcastReceiver>()
    private var receiver: BroadcastReceiver? = null
    private var filter: IntentFilter? = null

    private var mVisible: Boolean = false
    private val dialogLoading by lazy { ZMDialogLoading(this) }
    protected var showNotNetWorkPop = true
    protected val noNetWorkPopup by lazy { NoNetWorkPopup(this, this) }
    private var savedInstanceState: Bundle? = null
    private var uMengStatistics = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.savedInstanceState = savedInstanceState
        ARouter.getInstance().inject(this)
        setContentView(layoutId())
        if (toolbar != null) {
            this.mToolbar = toolbar
            initToolBar(toolbar)
        }
        initView()
        initData(savedInstanceState)
        initData()
        update()
    }

    abstract fun layoutId(): Int
    abstract fun initView()
    abstract fun initData()
    open fun initData(savedInstanceState: Bundle? = null) {

    }

    open fun update(){

    }

    private fun initToolBar(toolbar: Toolbar?) {
        if (toolbar == null) {
            return
        }
        toolbar.title = ""
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.mipmap.icon_back)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        toolbar.setPadding(0, 0, 0, 0)
    }

    protected fun setToolsBarTitle(title: String) {
        tvToolbarTitle = findViewById(R.id.tv_toolbar_title)
        tvToolbarTitle?.text = title
    }

    protected fun setToolsBarRightTitle(title: String) {
        tvToolbarRightTitle = findViewById(R.id.tv_right_title)
        tvToolbarRightTitle?.text = title
        tvToolbarRightTitle?.visibility = View.VISIBLE
    }

    protected fun setToolsBarRightTitleColor(colorId: Int, isClick: Boolean = false) {
        tvToolbarRightTitle?.textColorResource = colorId
        tvToolbarRightTitle?.isClickable = isClick
    }

    protected fun setViewLineVisible(visible: Int = View.GONE) {
        if (viewLine != null) {
            viewLine.visibility = visible
        }
    }

    protected fun uMengStatistics(uMengStatistics: String) {
        this.uMengStatistics = uMengStatistics
    }

    //请求一些必须要的权限
    protected fun requestPermission(permission: Array<String>) {
        if (EasyPermissions.hasPermissions(this, *permission)) {
            //具备权限 直接进行操作
            onPermissionSuccess()
        } else {
            //权限拒绝 申请权限
            EasyPermissions.requestPermissions(
                this, "为了正常使用，需要获取以下权限",
                PERMISSION_CODE, *permission
            ); }
    }

    //权限申请相关
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 将结果转发到EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    //权限获取成功
    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        onPermissionSuccess()
    }

    //权限获取被拒绝
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        /**
         * 若是在权限弹窗中，用户勾选了'NEVER ASK AGAIN.'或者'不在提示'，且拒绝权限。
         * 这时候，需要跳转到设置界面去，让用户手动开启。
         */
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            //拒绝了权限，而且选择了不在提醒，需要去手动设置了
        }
        //拒绝了权限，重新申请
        else {
            onPermissionFail()
        }
    }

    /**
     * 权限申请成功执行方法
     */
    protected open fun onPermissionSuccess() {

    }

    /**
     * 权限申请失败
     */
    protected open fun onPermissionFail() {

    }

    val layout: View by lazy { View.inflate(this, R.layout.custom_toast, null) }

    fun toast(str: String, topMargin: Float = 0f) {
        val tvContent = layout.find<TextView>(R.id.tvContent)
        tvContent.text = str
        if (topMargin > 0) {
            val params = tvContent.layoutParams as LinearLayout.LayoutParams
            params.topMargin = DisplayUtil.dip2px(this, topMargin)
            tvContent.layoutParams = params
        }
        val toast = Toast(this)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }

    fun showProcessDialog(resId: Int = 0, visible: Int = View.VISIBLE, content: String? = null) {
        when (resId) {
            0 -> showProcessDialog(content ?: getString(R.string.loading), visible)
            else -> showProcessDialog(getString(resId), visible)
        }
    }

    fun showProcessDialog(msg: String, visible: Int = View.VISIBLE) {
        if (mVisible) {
            dialogLoading.setText(msg).setImgVisibility(visible)
            dialogLoading.show()
        }
    }

    fun hideProcessDialog() {
        if (mVisible) {
            try {
                if (dialogLoading.isShowing)
                    dialogLoading.dismiss()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mVisible = true
    }

    override fun onPause() {
        super.onPause()
        mVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mapReceiver.size > 0 && mapReceiver[javaClass.simpleName] != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mapReceiver[javaClass.simpleName]!!)
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
        LocalBroadcastManager.getInstance(this).registerReceiver(
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
     *
     * @param actions 广播集合
     */
    protected fun sendLocalBroadCast(vararg actions: String) {
        for (action in actions) {
            LocalBroadcastManager.getInstance(this).sendBroadcast(Intent(action))
        }
    }

    protected fun sendLocalBroadCast(intent: Intent) {
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    open fun notHaveNetWork() {
        if (showNotNetWorkPop) {
            if (!isFinishing) {
                if (TextUtils.isEmpty(tvToolbarTitle?.text)) {
                    noNetWorkPopup.showAsBottom(mToolbar)
                } else {
                    noNetWorkPopup.show()
                }
            }
        }
    }

    override fun callback() {
        initView()
        initData(savedInstanceState)
        initData()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
//        Bugtags.onDispatchTouchEvent(this, ev)
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v?.windowToken)
//                v?.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.height
            val right = left + v.width
            return !(event.x > left && event.x < right
                    && event.y > top && event.y < bottom)
        }
        return false
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private fun hideKeyboard(token: IBinder?) {
        if (token != null) {
            val im = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
}