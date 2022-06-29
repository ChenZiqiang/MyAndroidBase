package com.android.framework.uitls;

import android.content.Context;
import android.view.View;

import androidx.databinding.BindingAdapter;

import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Created by Administrator on 2017/6/16.
 */

public class PtrFrameUtils {
    public static final int MODEL_LOAD_REFRESH = 0;
    public static final int MODEL_REFRESH = 1;
    public static final int MODEL_LOAD = 2;


    /**
     * 设置PtrFrameLayout默认下拉样式
     *
     * @param pullLayout 上下拉控件
     * @param mContext   上下文
     */
    public static void setDefaultHeader(PtrFrameLayout pullLayout, Context mContext) {
        pullLayout.setDurationToCloseHeader(1000);
        MaterialHeader loadMoreView = defaultMateriaHeader(mContext);
        pullLayout.setHeaderView(loadMoreView);
        pullLayout.addPtrUIHandler(loadMoreView);
    }

    /**
     * 设置PtrFrameLayout默认样式
     *
     * @param mContext
     * @return
     */
    public static MaterialHeader defaultMateriaHeader(Context mContext) {
        MaterialHeader materialHeader = new MaterialHeader(mContext);
        materialHeader.setPadding(0, 20, 0, 20);
        return materialHeader;
    }

    /**
     * 设置PtrFrameLayout默认上拉样式
     *
     * @param pullLayout 上下拉控件
     * @param mContext   上下文
     */
    public static void setDefaultFooter(PtrFrameLayout pullLayout, Context mContext) {
        pullLayout.setDurationToCloseFooter(500);
        MaterialHeader loadMoreView = defaultMateriaHeader(mContext);
        loadMoreView.setPadding(0, 10, 0, 20);
        pullLayout.setFooterView(loadMoreView);
        pullLayout.addPtrUIHandler(loadMoreView);
    }

    public static boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
        return PtrDefaultHandler2.checkContentCanBePulledUp(frame, content, footer);
    }

    public static boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler2.checkContentCanBePulledDown(frame, content, header);
    }

}
