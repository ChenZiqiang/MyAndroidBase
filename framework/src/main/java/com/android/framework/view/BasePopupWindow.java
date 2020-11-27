package com.android.framework.view;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * PopupWindow 基类
 * @author 陈自强
 * @version 1.0
 * @date 2020/9/30
 */
public abstract class BasePopupWindow extends PopupWindow {

    protected Activity activity;

    public BasePopupWindow(Activity activity) {
        super(activity);
        this.activity = activity;
        setHeight(-2);
        setWidth(-1);
        setOutsideTouchable(true);
        setClippingEnabled(true);
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.7f;
        activity.getWindow().setAttributes(lp);
        setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = activity.getWindow().getAttributes();
            lp1.alpha = 1f;
            activity.getWindow().setAttributes(lp1);
        });
    }

}
