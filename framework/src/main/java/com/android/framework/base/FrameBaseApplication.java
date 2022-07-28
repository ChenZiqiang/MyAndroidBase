package com.android.framework.base;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.kongzue.dialog.util.BaseDialog;

import org.litepal.LitePalApplication;
import org.litepal.exceptions.GlobalException;

/**
 * Application基类
 *
 * @author 陈自强
 * @version 1.0
 * @date 2020/10/19
 */
public class FrameBaseApplication extends Application {
    private static FrameBaseApplication instance;
    private Context mContext;

    public FrameBaseApplication() {
        super();
        mContext = instance = this;
        LitePalApplication.sContext = this;
    }

    public static FrameBaseApplication getInstance() {
        if (instance == null) {
            throw new GlobalException(GlobalException.APPLICATION_CONTEXT_IS_NULL);
        }
        return instance;
    }

    public Context getContext() {
        if (mContext == null) {
            throw new GlobalException(GlobalException.APPLICATION_CONTEXT_IS_NULL);
        }
        return mContext;
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
    }

    @Override
    public void onTerminate() {
        BaseDialog.unload();
        super.onTerminate();
    }
}
