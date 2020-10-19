package com.android.framework.uitls;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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


    /**
     * 1.加载图片,无需手动调用此方法
     * 2.使用@BindingAdapter注解设置自定义属性的名称，imageUrl就是属性的名称，
     * 当ImageView中使用imageUrl属性时，会自动调用loadImage方法，
     *
     * @param imageView ImageView
     * @param url       图片地址
     */
    @BindingAdapter({"loadImageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        Glide.get(imageView.getContext()).clearMemory();
        if (!TextUtils.isEmpty(url)) {
            Glide.with(imageView.getContext()).load(url)
                    .into(imageView);
        }
    }

}
