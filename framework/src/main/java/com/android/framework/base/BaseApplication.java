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
    private static BaseApplication instance;

    public static BaseApplication getInstance() {
        if (instance == null) {
            synchronized (BaseApplication.class) {
                if (instance == null) {
                    instance = new BaseApplication();
                }
            }
        }
        return instance;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        LitePal.initialize(this);
    }

    @Override
    public void onTerminate() {
        BaseDialog.unload();
        super.onTerminate();
    }
}
