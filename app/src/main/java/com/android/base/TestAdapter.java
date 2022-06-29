package com.android.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.base.databinding.ItemBinding;
import com.android.framework.recycler.FrameBaseAdapter;
import com.android.framework.recycler.FrameBaseViewHolder;

import java.util.List;

/**
 * @author admin
 * @version 1.0
 * @date 2022/6/28
 */
public class TestAdapter extends FrameBaseAdapter<String, ItemBinding> {

    public TestAdapter(@Nullable List<String> data) {
        super(R.layout.item, data);
    }

    public TestAdapter() {
        super(R.layout.item);
    }

    @Override
    protected void onBindItem(@NonNull FrameBaseViewHolder<ItemBinding> holder, ItemBinding binding, String data, int position) {
        holder.binding.setText(data);
    }
}
