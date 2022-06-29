package com.android.framework.mvvm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @author 陈自强
 * @version 1.0
 * @date 2020/10/19
 */
public class DataBindingHelper {

    public static <T extends ViewDataBinding> T inflate(ViewGroup parent, int layoutId) {
        return DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutId, parent, false);
    }

    public static <T extends ViewDataBinding> T inflate(Context mContext, int layoutId) {
        return DataBindingUtil.inflate(LayoutInflater.from(mContext), layoutId, null, false);
    }
}
