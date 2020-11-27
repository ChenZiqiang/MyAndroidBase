package com.android.framework.recycler;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

/**
 * @author 陈自强
 * @version 1.0
 * @date 2020/11/26
 */
public interface ItemHelperListener {
    /**
     * 选中Item
     * @param holder 选中的Item holder
     */
    void onItemSelected(ViewHolder holder);

    /**
     * 长按移动
     * @param fromHolder 选中的Item holder
     * @param toHolder 移动到目标的Item holder
     * @param fromPos 选中的Item position
     * @param toPos 移动到目标的Item position
     */
    void onItemMove(ViewHolder fromHolder, ViewHolder toHolder, int fromPos, int toPos);

    /**
     * 轻滑删除
     * @param holder 选中的Item holder
     */
    void onItemSwiped(ViewHolder holder);

    /**
     * 移动结束
     * @param holder 选中的Item holder
     */
    void onItemFinish(ViewHolder holder);

    /**
     * 移动超出RecyclerView边界
     */
    void onItemOut();
}