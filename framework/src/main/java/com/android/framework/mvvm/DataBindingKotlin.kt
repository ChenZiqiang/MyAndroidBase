package com.android.framework.mvvm

import android.graphics.Outline
import android.text.TextUtils
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.android.framework.R
import com.android.framework.uitls.ViewTools
import com.bumptech.glide.Glide

/**
 * @author 陈自强
 * @date 2021/4/7/0007
 * Description:
 */
object DataBindingKotlin {


    /**
     * 1.加载图片,无需手动调用此方法
     * 2.使用@BindingAdapter注解设置自定义属性的名称，imageUrl就是属性的名称，
     * 当ImageView中使用imageUrl属性时，会自动调用loadImage方法，
     *
     * @param imageView ImageView
     * @param url       图片地址
     */
    @JvmStatic
    @BindingAdapter("loadImageUrl")
    fun loadImage(imageView: ImageView, url: String?) {
        Glide.get(imageView.context).clearMemory()
        if (!TextUtils.isEmpty(url)) {
            Glide.with(imageView.context).load(url)
                    .load(R.drawable.icon_loading)
                    .error(R.mipmap.icon_default_error)
                    .into(imageView)
        }
    }

    @JvmStatic
    @BindingAdapter("loadImageUrl")
    fun loadImage(imageView: ImageView, resId: Int?) {
        Glide.get(imageView.context).clearMemory()
        Glide.with(imageView.context).load(resId)
                .load(R.drawable.icon_loading)
                .error(R.mipmap.icon_default_error)
                .into(imageView)
    }

    /**
     * 任意View裁剪圆角
     *
     * @param view 要裁剪的View
     * @param roundDP 圆角度数，单位为DP，
     */
    @JvmStatic
    @BindingAdapter("cutViewRound")
    fun cutViewRound(view: View, roundDP: Int) {
        view.clipToOutline = true
        view.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(0, 0, view.width, view.height, ViewTools.dp2px(roundDP.toFloat()).toFloat())
            }
        }
    }

}