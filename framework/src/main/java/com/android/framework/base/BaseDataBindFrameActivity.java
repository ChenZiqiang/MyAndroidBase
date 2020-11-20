package com.android.framework.base;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @author 陈自强
 * @version 1.0
 * @date 2020/10/19
 */
public class BaseDataBindFrameActivity<DB extends ViewDataBinding> extends BaseFrameActivity {
    protected DB binding;

    public void setContentView2(int layoutResID) {
        binding = DataBindingUtil.setContentView(this, layoutResID);
    }
}
