package com.android.framework.uitls;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.litepal.LitePal;

/**
 * 简单的初始化
 *
 * @author 陈自强
 */
public class FrameUtil {
    public static void init(Application context, final boolean debug) {
        ViewTools.init(context);
        SPUtils.getInstance(context);
        LitePal.initialize(context);
        OkGo.getInstance().init(context);
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return debug;
            }
        });
    }
}
