package com.android.framework.base;

import android.content.Context;

import androidx.multidex.MultiDex;

import org.litepal.LitePalApplication;

/**
 * Application基类
 * @author 陈自强
 * @version 1.0
 * @date 2020/10/19
 */
public class BaseApplication extends LitePalApplication {

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
