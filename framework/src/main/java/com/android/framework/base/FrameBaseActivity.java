package com.android.framework.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.framework.data.Tips;
import com.android.framework.mvvm.BaseViewModel;
import com.android.framework.uitls.CommonTool;
import com.kongzue.dialog.v3.TipDialog;
import com.kongzue.dialog.v3.WaitDialog;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

/**
 * The type Frame work base activity.
 *
 * @author 陈自强
 */
public abstract class FrameBaseActivity extends AppCompatActivity {
    protected Activity mActivity;
    protected Context mContext;
    protected String TAG;
    private boolean firstComplete = true;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mContext = this;
        TAG = CommonTool.getSimpleActivityName(mContext);
        Logger.t("ActivityName").i(TAG);
    }

    /**
     * 注册event bus
     */
    protected void registerEvent() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && firstComplete) {
            firstComplete = false;
            new Handler(Looper.myLooper()).post(this::onCreateComplete);
        }
    }

    /**
     * 新的生命周期：activity创建完成
     * 将绘制时间比较耗时的View放入此方法绘制时能有效减少Activity跳转时的卡顿
     */
    protected void onCreateComplete() {

    }

    /**
     * 长时间显示Toast提示
     *
     * @param message (来自String)
     */
    protected void showLongToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast提示
     *
     * @param resId (来自res)
     */
    protected void showLongToast(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
    }

    /**
     * 短暂显示Toast提示
     *
     * @param resId (来自res)
     */
    protected void showShortToast(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 短暂显示Toast提示
     *
     * @param text (来自String)
     */
    protected void showShortToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 隐藏软键盘
     */
    public void closeKeyboard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    /**
     * 获取焦点并弹出小键盘
     */
    protected void openKeyboard(EditText editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 简易跳转Activity
     *
     * @param activity 目标Activity
     */
    protected void jumpActivity(Class<?> activity) {
        Intent starter = new Intent(this, activity);
        startActivity(starter);
    }

    /**
     * 简易跳转Activity有回调
     *
     * @param activity 目标Activity
     */
    @Deprecated
    protected void jumpActivity(Class<?> activity, int requestCode) {
        Intent starter = new Intent(this, activity);
        startActivityForResult(starter, requestCode);
    }

    /**
     * 带bundle跳转Activity
     *
     * @param activity 目标Activity
     * @param bundle   数据
     */
    protected void jumpActivity(Class<?> activity, Bundle bundle) {
        Intent starter = new Intent(this, activity);
        starter.putExtras(bundle);
        startActivity(starter);
    }

    /**
     * 带bundle 跳转Activity有回调
     *
     * @param activity 目标Activity
     * @param bundle   数据
     *
     */
    @Deprecated
    protected void jumpActivity(Class<?> activity, Bundle bundle, int requestCode) {
        Intent starter = new Intent(this, activity);
        starter.putExtras(bundle);
        startActivityForResult(starter, requestCode);

        // 新跳转方法
        /*ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                });
        Intent intent = new Intent(this,activity);
        someActivityResultLauncher.launch(intent);*/
    }


    /**
     * 获取TextView文字
     *
     * @param view
     * @return
     */
    protected String getTextString(TextView view) {
        if (view != null) {
            return view.getText().toString().trim();
        }
        return null;
    }

    /**
     * 设置TextView文字
     *
     * @param textView
     * @param text
     */
    protected void setText(TextView textView, String text) {
        if (textView != null) {
            textView.setText(text);
        }
    }

    /**
     * 设置是否可见
     *
     * @param view
     * @param isVisible
     */
    protected void setVisible(View view, boolean isVisible) {
        if (view != null) {
            view.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
        }
    }

    /**
     * 设置是否隐藏
     *
     * @param view
     * @param isGone
     */
    protected void setGone(View view, boolean isGone) {
        if (view != null) {
            view.setVisibility(isGone ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * 显示加载进度条
     */
    protected void showLoading() {
        showLoading("");
    }

    protected void showLoading(String msg) {
        WaitDialog.reset();
        WaitDialog.show(this, msg).setOnBackClickListener(() -> false);
    }

    protected void dismissLoading() {
        WaitDialog.dismiss();
    }

    protected void showTips(Tips tips) {
        TipDialog.show(this, tips.getMsg(), tips.getType());
    }

    public void initDialogVM(BaseViewModel vm) {
        vm.getShowTip().observe(this, msg -> {
            if (TextUtils.isEmpty(msg)) {
                showLoading();
            } else {
                showLoading(msg);
            }
        });

        vm.getDismissTip().observe(this, msg -> {
            dismissLoading();
        });

        vm.getTips().observe(this, this::showTips);
    }
}
