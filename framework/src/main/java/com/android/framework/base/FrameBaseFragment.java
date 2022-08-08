package com.android.framework.base;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.framework.mvvm.FrameBaseViewModel;
import com.kongzue.dialog.v3.WaitDialog;

import org.greenrobot.eventbus.EventBus;

/**
 * Fragment基类
 *
 * @author 陈自强
 */
public abstract class FrameBaseFragment extends Fragment {
    protected View rootView;
    protected Context mContext;
    protected int layoutId;

    public FrameBaseFragment() {
    }

    public FrameBaseFragment(int contentLayoutId) {
        super(contentLayoutId);
        layoutId = contentLayoutId;
    }

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
        if (rootView == null) {
            rootView = view;
        }
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

    protected FrameBaseActivity getBaseActivity() {
        if (getActivity() instanceof FrameBaseActivity) {
            return (FrameBaseActivity) getActivity();
        }
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 显示加载进度条
     */
    protected void showLoading() {
        showLoading("");
    }

    protected void showLoading(String msg) {
        if (getBaseActivity() != null) {
            WaitDialog.reset();
            getBaseActivity().showLoading(msg);
        }
    }

    protected void dismissLoading() {
        WaitDialog.dismiss();
    }

    public void initViewModel(FrameBaseViewModel vm) {
        vm.getShowTip().observe(this, msg -> {
            if (TextUtils.isEmpty(msg)) {
                showLoading();
            } else {
                showLoading(msg);
            }
        });

        vm.getDismissTip().observe(this, msg -> {
            dismissLoading();
        });

        vm.getTips().observe(this, tips -> {
            if (getBaseActivity() != null) {
                getBaseActivity().showTips(tips);
            }
        });

        vm.getToast().observe(this, this::showShortToast);
    }
}
