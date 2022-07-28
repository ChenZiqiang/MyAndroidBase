package com.android.base;

import android.app.Application;

import com.android.framework.base.FrameBaseApplication;
import com.android.framework.uitls.MyFrameWork;
import com.android.framework.uitls.ViewTools;
import com.kongzue.dialog.util.DialogSettings;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.mmkv.MMKV;

import org.litepal.LitePal;

import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * @author 陈自强
 * @version 1.0
 * @date 2020/11/30
 */
public class MyApplicationFrame extends FrameBaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
//        init(this, BuildConfig.DEBUG);
        MyFrameWork.init(this,BuildConfig.DEBUG);
    }

    private void init(Application context, final boolean debug) {
        ViewTools.init(context);
        MMKV.initialize(context);
        LitePal.initialize(context);

        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return debug;
            }
        });
        initDialog(debug);
        initOkGO();

    }

    private void initOkGO() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        HttpHeaders headers = new HttpHeaders();

        OkGo.getInstance()
                .init(this)
                .setOkHttpClient(builder.build())
                .addCommonHeaders(headers);
    }

    private void initDialog(final boolean debug) {
        DialogSettings.init();
        DialogSettings.DEBUGMODE = debug;
//        DialogSettings.isUseBlur = true;
        DialogSettings.style = DialogSettings.STYLE.STYLE_IOS;
        DialogSettings.theme = DialogSettings.THEME.LIGHT;
        DialogSettings.autoShowInputKeyboard = true;
    }
}
