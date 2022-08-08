package com.android.framework.uitls;

import android.app.Application;
import android.text.TextUtils;

import com.android.framework.net.BaseHttpHelper;
import com.kongzue.dialog.util.DialogSettings;
import com.lzy.okgo.OkGo;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import org.litepal.LitePal;

/**
 * 简单初始化
 *
 * @author 陈自强
 * @version 1.0
 * @date 2022/6/28
 */
public class MyFrameWork {

    /**
     * 初始化
     *
     * @param app
     * @param debug 是否打印debug日志
     * @param logcatTag 日志输出TAG
     */
    public static void init(Application app, final boolean debug, String logcatTag,String baseUrl) {
        ViewTools.init(app);
        MMKVTools.init(app);
        LitePal.initialize(app);
        OkGo.getInstance().init(app);
        if (TextUtils.isEmpty(logcatTag)) {
            logcatTag = "LOGGER";
        }
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag(logcatTag)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return debug;
            }
        });
        initDialog(debug);
        BaseHttpHelper.setBaseUrl(baseUrl);
    }

    public static void init(Application app, final boolean debug) {
        init(app, debug, null,"");
    }

    public static void init(Application app) {
        init(app, false, null,"");
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
