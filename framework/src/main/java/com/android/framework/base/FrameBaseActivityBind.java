package com.android.framework.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @author 陈自强
 * @version 1.0
 * @date 2020/10/19
 */
public class FrameBaseActivityBind<DB extends ViewDataBinding> extends FrameBaseActivity {
    protected DB binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * 采用DataBinding方案时，需用bindContentView代替setContentView
     * @param layoutResID
     */
    public void bindContentView(int layoutResID) {
        binding = DataBindingUtil.setContentView(this, layoutResID);
    }
}
