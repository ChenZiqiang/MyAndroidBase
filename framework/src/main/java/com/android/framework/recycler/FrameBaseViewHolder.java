package com.android.framework.recycler;

import androidx.databinding.ViewDataBinding;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;

/**
 * @author 陈自强
 * @version 1.0
 * @date 2020/9/29
 */
public class FrameBaseViewHolder<DB extends ViewDataBinding> extends BaseViewHolder {

    public DB binding;

    public FrameBaseViewHolder(DB binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}
