package com.android.framework.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.android.framework.R;


/**
 * 简介：全屏Dialog基类
 *
 * @author 陈自强
 * @version 1.0
 * @date 2020/5/8
 */
public abstract class BaseFullScreenDialog extends Dialog {

    protected Context context;
    public static float DIALOG_BRIGHTNESS = 0.3f;

    public BaseFullScreenDialog(@NonNull Context context) {
        super(context, R.style.custom_dialog_bottom_anim);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindows();
    }

    private void initWindows() {
        Window window = getWindow();
        View decorView = window.getDecorView();
        // 把 DecorView 的默认 padding 取消，同时 DecorView 的默认大小也会取消
        decorView.setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        // 设置宽度
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        // 设置背景颜色变暗
        window.setDimAmount(DIALOG_BRIGHTNESS);
        window.setAttributes(layoutParams);

        int systemUiVisibility = decorView.getSystemUiVisibility()
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        decorView.setSystemUiVisibility(systemUiVisibility);
    }



}
