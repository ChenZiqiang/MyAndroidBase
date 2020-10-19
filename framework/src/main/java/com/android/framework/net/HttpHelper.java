package com.android.framework.net;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;

/**
 * @author 陈自强
 * @version 1.0
 * @date 2020/10/19
 */
public class HttpHelper {

    public static void onPost(String url, HttpParams params, StringCallback callback) {
        OkGo.<String>post(url).params(params).execute(callback);
    }

    public static void onPost(String url, StringCallback callback) {
        OkGo.<String>post(url).execute(callback);
    }


}
