package com.android.framework.net;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;

import java.io.File;

/**
 * @author 陈自强
 * @version 1.0
 * @date 2020/10/19
 */
public class BaseHttpHelper {

    private static String BASE_URL = "";

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

    private static String getBaseUrl(String endUrl) {
        return BASE_URL + endUrl;
    }

    /**
     * 无参 post 请求
     *
     * @param url
     * @param callback
     */
    public static void onPost(String url, StringCallback callback) {
        OkGo.<String>post(getBaseUrl(url)).execute(callback);
    }

    /**
     * 带参 post 请求
     *
     * @param url
     * @param params
     * @param callback
     */
    public static void onPost(String url, HttpParams params, StringCallback callback) {
        OkGo.<String>post(getBaseUrl(url)).params(params).execute(callback);
    }


    /**
     * 无参 get 请求
     *
     * @param url
     * @param callback
     */
    public static void onGet(String url, StringCallback callback) {
        OkGo.<String>get(getBaseUrl(url)).execute(callback);
    }

    /**
     * 带参 get 请求
     *
     * @param url
     * @param params
     * @param callback
     */
    public static void onGet(String url, HttpParams params, StringCallback callback) {
        OkGo.<String>get(getBaseUrl(url)).params(params).execute(callback);
    }

    /**
     * 无参上传单个文件
     *
     * @param url
     * @param file
     * @param callback
     */
    public static void onUpFile(String url, File file, StringCallback callback) {
        OkGo.<String>post(getBaseUrl(url)).upFile(file).execute(callback);
    }


}
