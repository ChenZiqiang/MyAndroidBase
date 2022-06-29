package com.android.framework.uitls;

import android.app.Application;

import com.kongzue.dialog.util.DialogSettings;
import com.lzy.okgo.OkGo;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.mmkv.MMKV;

import org.litepal.LitePal;

/**
 * 简单初始化
 * @author 陈自强
 * @version 1.0
 * @date 2022/6/28
 */
public class MyFrameWork {

    public static void init(Application context, final boolean debug) {
        ViewTools.init(context);
        MMKV.initialize(context);
        LitePal.initialize(context);
        OkGo.getInstance().init(context);
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return debug;
            }
        });
        initDialog(debug);
    }

    private static void initDialog(final boolean debug) {
        DialogSettings.init();
        DialogSettings.DEBUGMODE = debug;
//        DialogSettings.isUseBlur = true;
        DialogSettings.style = DialogSettings.STYLE.STYLE_IOS;
        DialogSettings.theme = DialogSettings.THEME.LIGHT;
        DialogSettings.autoShowInputKeyboard = true;
    }
}
