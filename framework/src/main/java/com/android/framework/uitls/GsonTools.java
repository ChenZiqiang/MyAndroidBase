package com.android.framework.uitls;

import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * 解析工具类
 * @create 陈自强
 * @date 2020/7/6
 */
public class GsonTools {

    // json字符串转换成对象
    public static <T> T json2Object(String json, Class<T> classOfT) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(json, classOfT);
    }

    // list转换成json字符串
    public static String object2json(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);

    }

}
