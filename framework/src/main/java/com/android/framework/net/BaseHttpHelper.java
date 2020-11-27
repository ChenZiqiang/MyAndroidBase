package com.android.framework.net;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;

import java.io.File;
import java.util.List;

/**
 * @author 陈自强
 * @version 1.0
 * @date 2020/10/19
 */
public class BaseHttpHelper {
    /**
     * 无参 post 请求
     * @param url
     * @param callback
     */
    public static void onPost(String url, StringCallback callback) {
        OkGo.<String>post(url).execute(callback);
    }

    /**
     * 带参 post 请求
     * @param url
     * @param params
     * @param callback
     */
    public static void onPost(String url, HttpParams params, StringCallback callback) {
        OkGo.<String>post(url).params(params).execute(callback);
    }


    /**
     * 无参 get 请求
     * @param url
     * @param callback
     */
    public static void onGet(String url, StringCallback callback) {
        OkGo.<String>get(url).execute(callback);
    }

    /**
     * 带参 get 请求
     * @param url
     * @param params
     * @param callback
     */
    public static void onGet(String url, HttpParams params, StringCallback callback) {
        OkGo.<String>get(url).params(params).execute(callback);
    }

    /**
     * 无参上传单个文件
     * @param url
     * @param file
     * @param callback
     */
    public static void onUpFile(String url, File file, StringCallback callback) {
        OkGo.<String>post(url).upFile(file).execute(callback);
    }


}
