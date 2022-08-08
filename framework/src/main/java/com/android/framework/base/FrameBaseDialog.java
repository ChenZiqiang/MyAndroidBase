package com.android.framework.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.android.framework.R;
import com.android.framework.data.Tips;
import com.android.framework.mvvm.FrameBaseViewModel;
import com.kongzue.dialog.v3.TipDialog;
import com.kongzue.dialog.v3.WaitDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Fragment基类
 *
 * @author 陈自强
 */
public abstract class FrameBaseDialog extends DialogFragment {
    protected View rootView;
    protected Context mContext;
    protected int layoutId;
    private FrameBaseDialog.DismissListener dismissListener;

    public FrameBaseDialog() {
    }

    public FrameBaseDialog(int contentLayoutId) {
        super(contentLayoutId);
        layoutId = contentLayoutId;
    }

    public View getRootView() {
        return rootView;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    public void setDismissListener(DismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog == null || dialog.getWindow() == null) {
            return;
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawableResource(R.color.translucent);
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null) {
            return rootView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
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

    public boolean isShowing() {
        if (getDialog() != null && getDialog().isShowing()) {
            return true;
        } else {
            return false;
        }
    }

    protected FragmentManager getActivityFragmentManager() {
        return requireActivity().getSupportFragmentManager();
    }

    public boolean textEmpty(CharSequence str) {
        return TextUtils.isEmpty(str);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (dismissListener != null) {
            dismissListener.dismiss();
        }
    }


    public interface DismissListener {
        void dismiss();
    }


    /**
     * 显示加载进度条
     */
    protected void showLoading() {
        showLoading("");
    }

    protected FrameBaseActivity getBaseActivity() {
        if (getActivity() instanceof FrameBaseActivity) {
            return (FrameBaseActivity) getActivity();
        }
        return null;
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

    protected void showTips(Tips tips) {
        if (getBaseActivity() != null) {
            TipDialog.show(getBaseActivity(), tips.getMsg(), tips.getType());
        }
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

        vm.getTips().observe(this, this::showTips);

        vm.getToast().observe(this, this::showShortToast);
    }
}
