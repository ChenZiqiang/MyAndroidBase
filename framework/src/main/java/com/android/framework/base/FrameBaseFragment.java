package com.android.framework.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.EventBus;

/**
 * Fragment基类
 */
public abstract class FrameBaseFragment extends Fragment {
    protected View rootView;
    protected Context mContext;
    public View getRootView() {
        return rootView;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null) {
            return rootView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected <T extends View> T findViewById(int viewId) {
        if (rootView != null) {
            return rootView.findViewById(viewId);
        }
        return null;
    }


    /**
     * 长时间显示Toast提示
     *
     * @param message (来自String)
     */
    protected void showLongToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast提示
     *
     * @param resId (来自res)
     */
    protected void showLongToast(int resId) {
        Toast.makeText(mContext, getString(resId), Toast.LENGTH_LONG).show();
    }

    /**
     * 短暂显示Toast提示
     *
     * @param resId (来自res)
     */
    protected void showShortToast(int resId) {
        Toast.makeText(mContext, getString(resId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 短暂显示Toast提示
     *
     * @param text (来自String)
     */
    protected void showShortToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }


    /**
     * 注册event bus
     */
    protected void registerEvent() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
