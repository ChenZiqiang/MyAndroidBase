package com.android.framework.uitls;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.util.Map;

/**
 * SharedPreferences工具类
 * Created by 陈自强 on 2017/8/14 0014.
 */

public class SPUtils {
    private static String SP_NAME = "Framework";
    private static SPUtils instance;
    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;

    public static void setSpName(String spName) {
        SP_NAME = spName;
    }

    public static SPUtils getInstance(Context mContext) {
        return getInstance(mContext, SP_NAME);
    }

    public static SPUtils getInstance(Context mContext, String spName) {
        if (instance == null) {
            synchronized (SPUtils.class) {
                if (instance == null) {
                    instance = new SPUtils();
                }
            }
        }
        initSP(mContext, spName);
        return instance;
    }

    /**
     * 初始化
     *
     * @param context
     * @param fileName
     */
    private static void initSP(Context context, String fileName) {
        if (context == null) {
            return;
        }
        String FILE_NAME;
        if (TextUtils.isEmpty(fileName)) {
            FILE_NAME = SP_NAME;
        } else {
            FILE_NAME = fileName;
        }
        sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public void put(String key, Object object) {
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public Object get(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    public String getString(String key) {
        return getString(key, "");
    }

    public String getString(String key, String s) {
        return (String) get(key, s);
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int i) {
        return (Integer) get(key, i);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean b) {
        return (Boolean) get(key, b);
    }

    public float getFloat(String key) {
        return getFloat(key, 0f);
    }

    public float getFloat(String key, float f) {
        return (Float) get(key, f);
    }

    public long getLong(String key) {
        return getLong(key, 0L);
    }

    public long getLong(String key, long l) {
        return (Long) get(key, l);
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public void removeValue(String key) {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        apply(editor);
    }

    /**
     * 清除所有数据
     */
    public void clearAll() {
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public boolean containsKey(String key) {
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public Map<String, ?> getAll() {
        return sp.getAll();
    }


    /**
     * 如果找到则使用apply执行，否则使用commit
     *
     * @param editor
     */
    private void apply(SharedPreferences.Editor editor) {
        editor.commit();
    }

    public void saveObject(String key, Object object) {
        String json = GsonTools.object2json(object);
        put(key, json);
    }

    public <T> T getObject(String key, Class<T> tClass) {
        String json = getString(key);
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        try {
            return GsonTools.json2Object(json, tClass);
        } catch (Exception e) {
            Logger.t("SPUtils").e(e.getMessage());
            return null;
        }

    }
}
