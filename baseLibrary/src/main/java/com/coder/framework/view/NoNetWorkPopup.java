package com.coder.framework.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import com.coder.framework.base.BaseInterface;
import com.coder.hydf.base.R;

/**
 * Created by Zero on 2020/05/30.
 */
public class NoNetWorkPopup extends PopupWindow {
    private View view; // PopupWindow 菜单布局
    private Context context; // 上下文参数
    private BaseInterface callback;

    public NoNetWorkPopup(Context context, BaseInterface callback) {
        super(context);
        this.context = context;
        this.callback = callback;
        init();
    }

    /**l
     * 设置布局以及点击事件
     */
    private void init() {
        view = LayoutInflater.from(context).inflate(R.layout.pop_no_network_view, null);
        view.findViewById(R.id.ll_no_network).setOnClickListener(v -> callback.callback());

        this.setOnDismissListener(() -> callback.callback());
        // 导入布局
        setContentView(view);
        // 设置动画效果
        setAnimationStyle(R.style.popwindow_anim_style);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        // 设置可触
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xFFFFFF);
        setBackgroundDrawable(dw);
    }

    public void show() {
        if (!((Activity)context).isFinishing()) {
            try {
                showAtLocation(view, Gravity.CENTER, 0, 0);
            }catch (Exception e){
                e.printStackTrace();
                view.postDelayed(this::show, 1000);
            }
        }
    }

    public void showAsBottom(View parent) {
        if (!((Activity)context).isFinishing()) {
            try {
                showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            }catch (Exception e){
                e.printStackTrace();
                parent.postDelayed(() -> showAsBottom(parent), 1000);
            }
        }
    }
}