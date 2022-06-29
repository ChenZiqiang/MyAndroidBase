package com.android.base;

import com.android.framework.base.BaseApplication;
import com.android.framework.uitls.FrameUtil;
import com.android.framework.uitls.MyFrameWork;

/**
 * @author 陈自强
 * @version 1.0
 * @date 2020/11/30
 */
public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        MyFrameWork.init(this,BuildConfig.DEBUG);
    }
}
