package com.android.framework.base;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.kongzue.dialog.util.BaseDialog;

import org.litepal.LitePal;
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
    public static Context sContext;

    public BaseApplication() {
        sContext = this;
        instance=this;
    }

    public static Context getContext() {
        if (sContext == null) {
            throw new GlobalException(GlobalException.APPLICATION_CONTEXT_IS_NULL);
        }
        return sContext;
    }

    public static BaseApplication getInstance() {
        if (instance == null) {
            throw new GlobalException(GlobalException.APPLICATION_CONTEXT_IS_NULL);
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
