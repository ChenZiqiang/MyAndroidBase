package com.android.framework.net;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 77
 * @version 1.0
 * @date 2020/11/7
 */
public abstract class BaseCallBack<T> extends StringCallback {
    protected String TAG = "HttpResult";
    protected Gson gson;


    protected BaseHttpBean<T> baseBean;
    protected boolean isList;
    public Class<T> tClass;

    public BaseCallBack() {
        this(null);
    }

    public BaseCallBack(Class<T> tClass) {
        this(tClass, false);
    }

    public BaseCallBack(Class<T> tClass, boolean isList) {
        this.isList = isList;
        this.tClass = tClass;
        gson = new Gson();
    }

    @Override
    public void onSuccess(Response<String> response) {
        String body = response.body();
        Logger.t(TAG).json(body);

        try {
            Type type = getType();
            if (type != null) {
                baseBean = gson.fromJson(body, type);
            } else {
                baseBean = gson.fromJson(body, BaseHttpBean.class);
            }
            if (baseBean != null) {
                httpSuccess(baseBean.data);
            } else {
                onResultError(BaseHttpCode.ERROR_JSON,"解析失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (tClass != null) {
                try {
                    if (isList) {
                        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
                        JsonArray jsonArray = jsonObject.getAsJsonArray("data");
                        List<T> list = getObjectList(jsonArray, tClass);
                        onListSuccess(list);
                    } else {
                        JSONObject object = new JSONObject(body);
                        String data = object.getString("data");
                        T t = gson.fromJson(data, tClass);
                        httpSuccess(t);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    onResultError(BaseHttpCode.ERROR_JSON,"解析失败");
                }
            } else {
                onResultError(BaseHttpCode.ERROR_JSON,"解析失败");
            }
        }
    }

    @Override
    public void onError(Response<String> response) {
        super.onError(response);
        onResultError(BaseHttpCode.ERROR_NET,response.body());
    }

    public abstract void onResultError(int code, String msg);

    public abstract void httpSuccess(T t);

    public Type getType() {
        try {
            Type type = getClass().getGenericSuperclass();
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            Type ty = new ParameterizedTypeImpl(BaseHttpBean.class, new Type[]{types[0]});
            return ty;
        } catch (Exception e) {
            return null;
        }
    }

    public static class ParameterizedTypeImpl implements ParameterizedType {
        private final Class raw;
        private final Type[] args;

        public ParameterizedTypeImpl(Class raw, Type[] args) {
            this.raw = raw;
            this.args = args != null ? args : new Type[0];
        }

        @Override
        public Type[] getActualTypeArguments() {
            return args;
        }

        @Override
        public Type getRawType() {
            return raw;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }


    protected void onListSuccess(List<T> list) {

    }

    public <T> List<T> getObjectList(JsonArray array, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            for (JsonElement jsonElement : array) {
                list.add(gson.fromJson(jsonElement, cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
