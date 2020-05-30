package com.coder.framework.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.coder.framework.util.DisplayUtil;
import com.coder.hydf.base.R;

/**
 * Created by Zero on 2020/05/30.
 * 网络加载转菊花
 */
public class ZMDialogLoading extends Dialog {
    private Context context;
    private ImageView vLoading;
    private Animation animImg;
    private String text = "";
    private TextView textView;

    public ZMDialogLoading(Context context) {
        super(context, R.style.CustomLoadingDialog);
        this.context = context;
        vLoading = new ImageView(context);
    }

    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.PROGRESS_VISIBILITY_ON);
        super.onCreate(savedInstanceState);
        vLoading.setImageResource(R.drawable.loading_icon);// 加载中的图片,建议圆形的

        LinearLayout linearLayout = new LinearLayout(context);
        // 布局
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                DisplayUtil.Companion.dip2px(context, 124f),
                DisplayUtil.Companion.dip2px(context, 114f));
        lp.gravity = Gravity.CENTER;
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(80, DisplayUtil.Companion.dip2px(context, 24f), 80, 50);
        linearLayout.setBackground(context.getDrawable(R.drawable.shape_corner_gray_bg));

        textView = new TextView(context);
        LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        textLayoutParams.gravity = Gravity.CENTER;
        textView.setLayoutParams(textLayoutParams);
        textView.setText(TextUtils.isEmpty(text) ? "加载中..." : text);
        textView.setTextSize(14);
        textView.setTextColor(context.getResources().getColor(android.R.color.white));
        textView.setGravity(Gravity.CENTER);

        animImg = AnimationUtils.loadAnimation(this.getContext(),
                R.anim.rotate_repeat);
        animImg.setInterpolator(new LinearInterpolator());

        LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(
                DisplayUtil.Companion.dip2px(context, 36f),
                DisplayUtil.Companion.dip2px(context, 36f));
        imageLayoutParams.gravity = Gravity.CENTER;
        imageLayoutParams.bottomMargin = 12;
        vLoading.setLayoutParams(imageLayoutParams);

        linearLayout.addView(vLoading);
        linearLayout.addView(textView);

        addContentView(linearLayout, lp);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 按下了键盘上返回按钮
            this.hide();
            return true;
        }
        return false;
    }

    @Override
    public void show() {
        super.show();
        textView.setText(TextUtils.isEmpty(text) ? "加载中..." : text);
        vLoading.startAnimation(animImg);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        vLoading.clearAnimation();
    }

    public ZMDialogLoading setText(String text) {
        this.text = text;
        return this;
    }

    public ZMDialogLoading setImgVisibility(int visible) {
        vLoading.setVisibility(visible);
        return this;
    }
}