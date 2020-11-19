package com.android.framework.base;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import com.android.framework.uitls.DataBindingHelper;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * The type Base recycler adapter.
 *
 * @param <DB> the type parameter
 * @author 陈自强
 * @version 1.0
 * @date 2020 /9/29
 */
public abstract class BaseRecyclerAdapter<T, DB extends ViewDataBinding> extends BaseQuickAdapter<T, BaseDataViewHolder<DB>> {
    protected Context mContext;
    protected int layoutResId;

    public BaseRecyclerAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
        this.layoutResId = layoutResId;
    }

    public BaseRecyclerAdapter(int layoutResId) {
        super(layoutResId);
        this.layoutResId = layoutResId;
    }

    @NonNull
    @Override
    public BaseDataViewHolder<DB> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        if (checkAdapterView(viewType)) {
            DB DB = DataBindingHelper.inflate(parent, layoutResId);
            return new BaseDataViewHolder<DB>(DB);
        } else {
            return super.onCreateViewHolder(parent, viewType);
        }
    }

    /**
     * 判断 RecyclerView 中item的类别
     *
     * @param viewType
     * @return
     */
    public boolean checkAdapterView(int viewType) {
        if (viewType != BaseQuickAdapter.LOAD_MORE_VIEW
                && viewType != BaseQuickAdapter.HEADER_VIEW
                && viewType != BaseQuickAdapter.EMPTY_VIEW
                && viewType != BaseQuickAdapter.FOOTER_VIEW) {
            return true;
        }
        return false;
    }

    @Override
    protected void convert(@NotNull BaseDataViewHolder<DB> holder, T t) {
        onBindItem(holder,holder.binding, t,holder.getAdapterPosition());
    }

    protected abstract void onBindItem(@NotNull BaseDataViewHolder<DB> holder,DB binding, T t,int position);
}
