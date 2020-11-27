package com.android.framework.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.android.framework.mvvm.DataBindingHelper;

/**
 * @author 陈自强
 * @version 1.0
 * @date 2020/10/19
 */
public abstract class BaseDataBindFragment<DB extends ViewDataBinding> extends BaseFrameFragment {
    protected DB binding;

    public BaseDataBindFragment() {
    }

    public BaseDataBindFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    protected void setRootView(@LayoutRes int layoutID) {
        binding = DataBindingHelper.inflate(mContext, layoutID);
        rootView = binding.getRoot();
    }

    protected abstract int getRootViewResID();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getRootViewResID() != 0) {
            layoutId = getRootViewResID();
        }
        if (layoutId != 0) {
            setRootView(layoutId);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onFragmentCreated();
    }

    protected abstract void onFragmentCreated();
}
