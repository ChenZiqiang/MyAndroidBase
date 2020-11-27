package com.android.framework.mvvm;

import android.content.Context;
import android.graphics.Outline;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.android.framework.uitls.ViewTools;
import com.bumptech.glide.Glide;

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

    /**
     * 任意View裁剪圆角
     *
     * @param view 要裁剪的View
     * @param roundDP 圆角度数，单位为DP，
     */
    @BindingAdapter({"cutViewRound"})
    public static void cutViewRound(View view, int roundDP) {
        view.setClipToOutline(true);
        view.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), ViewTools.dp2px(roundDP));
            }
        });
    }
}
