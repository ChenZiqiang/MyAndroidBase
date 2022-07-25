package com.android.framework.uitls;

import android.content.Context;
import android.os.Parcelable;

import com.tencent.mmkv.MMKV;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MMKV小工具，对方法重命名，更加符合开发习惯
 *
 * @author admin
 * @version 1.0
 * @date 2022/7/25
 */
public class MMKVTools {

    public static void init(Context mContext) {
        MMKV.initialize(mContext);
    }

    public static void put(String key, String value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    public static void put(String key, boolean value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    public static void put(String key, byte[] value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    public static void put(String key, int value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    public static void put(String key, long value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    public static void put(String key, double value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    public static void put(String key, float value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    public static void put(String key, Parcelable value) {
        MMKV.defaultMMKV().encode(key, value);
    }

    public static String getString(String key) {
        return MMKV.defaultMMKV().decodeString(key);
    }

    public static String getString(String key, String defaultString) {
        return MMKV.defaultMMKV().decodeString(key, defaultString);
    }

    public static boolean getBoolean(String key) {
        return MMKV.defaultMMKV().decodeBool(key);
    }

    public static boolean getBoolean(String key, boolean defaultBoolean) {
        return MMKV.defaultMMKV().decodeBool(key, defaultBoolean);
    }

    public static byte[] getBytes(String key) {
        return MMKV.defaultMMKV().decodeBytes(key);
    }

    public static byte[] getBytes(String key, byte[] defaultBytes) {
        return MMKV.defaultMMKV().decodeBytes(key, defaultBytes);
    }

    public static int getInt(String key) {
        return MMKV.defaultMMKV().decodeInt(key);
    }

    public static int getInt(String key, int defaultInt) {
        return MMKV.defaultMMKV().decodeInt(key, defaultInt);
    }

    public static double getDouble(String key) {
        return MMKV.defaultMMKV().decodeDouble(key);
    }

    public static double getDouble(String key, int defaultDouble) {
        return MMKV.defaultMMKV().decodeDouble(key, defaultDouble);
    }

    public static float getFloat(String key) {
        return MMKV.defaultMMKV().decodeFloat(key);
    }

    public static long getLong(String key) {
        return MMKV.defaultMMKV().decodeLong(key);
    }

    public static long getLong(String key, long defaultLogin) {
        return MMKV.defaultMMKV().decodeLong(key, defaultLogin);
    }

    public static <T extends Parcelable> T getParcelable(String key, Class<T> tClass) {
        return MMKV.defaultMMKV().decodeParcelable(key, tClass);
    }

    public static <T extends Parcelable> T getParcelable(String key, Class<T> tClass, T defaultValue) {
        return MMKV.defaultMMKV().decodeParcelable(key, tClass, defaultValue);
    }


    public static void clear() {
        MMKV.defaultMMKV().clearAll();
    }

    public static void remove(String key) {
        MMKV.defaultMMKV().remove(key);
    }

    public static String[] allKeys() {
        return MMKV.defaultMMKV().allKeys();
    }

    /**
     * 清除指定KEY以外的数据
     *
     * @param keyList 要保留的KEY,仅限string 类型
     */
    public static void removeOtherKeys(List<String> keyList) {
        Map<String, String> value = new HashMap<>();
        for (String key : keyList) {
            value.put(key, MMKV.defaultMMKV().decodeString(key));
        }
        MMKV.defaultMMKV().clearAll();
        for (String key : keyList) {
            MMKV.defaultMMKV().encode(key, value.get(key));
        }
    }

    /**
     * 清除指定KEY的数据
     *
     * @param keyList 要清除的KEY
     */
    public static void removeKeys(List<String> keyList) {
        for (String key : keyList) {
            MMKV.defaultMMKV().remove(key);
        }
    }
}
