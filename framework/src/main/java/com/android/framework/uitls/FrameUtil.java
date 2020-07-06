package com.android.framework.uitls;

import android.content.Context;

public class FrameUtil {
    public static void init(Context context) {
        ViewTools.init(context);
        SPUtils.getInstance(context);
    }
}
