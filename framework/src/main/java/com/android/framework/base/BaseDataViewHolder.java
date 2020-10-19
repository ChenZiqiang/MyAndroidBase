package com.android.framework.base;

import androidx.databinding.ViewDataBinding;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;

/**
 * @author 陈自强
 * @version 1.0
 * @date 2020/9/29
 */
public class BaseDataViewHolder<DB extends ViewDataBinding> extends BaseViewHolder {

    public DB binding;

    public BaseDataViewHolder(DB binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}
