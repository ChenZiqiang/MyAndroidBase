package com.android.framework.base;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.kongzue.dialog.util.BaseDialog;

import org.litepal.LitePal;

/**
 * Application基类
 *
 * @author 陈自强
 * @version 1.0
 * @date 2020/10/19
 */
public class BaseApplication extends Application {
    protected static BaseApplication instance;
    protected static Context mContext;

    public static BaseApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return mContext;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        instance = this;
        mContext=this;
        MultiDex.install(this);
        LitePal.initialize(this);
    }

    @Override
    public void onTerminate() {
        BaseDialog.unload();
        super.onTerminate();
    }
}
