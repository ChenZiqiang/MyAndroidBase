package com.android.framework.base;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.kongzue.dialog.util.BaseDialog;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;
import org.litepal.exceptions.GlobalException;

/**
 * Application基类
 *
 * @author 陈自强
 * @version 1.0
 * @date 2020/10/19
 */
public class BaseApplication extends Application {
    protected static BaseApplication instance;

    public BaseApplication() {
        super();
        instance = this;
        LitePalApplication.sContext = this;
    }


    public static BaseApplication getInstance() {
        if (instance == null) {
            throw new GlobalException(GlobalException.APPLICATION_CONTEXT_IS_NULL);
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        LitePalApplication.sContext = base;
        MultiDex.install(base);
        LitePal.initialize(base);
    }

    @Override
    public void onTerminate() {
        BaseDialog.unload();
        super.onTerminate();
    }
}
