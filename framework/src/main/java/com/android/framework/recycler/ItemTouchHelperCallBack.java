package com.android.framework.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 拖动回调类
 *
 * @author 陈自强
 * @version 1.0
 * @date 2020/11/26
 */
public class ItemTouchHelperCallBack extends ItemTouchHelper.Callback {
    /**
     * 是否是左右轻滑删除模式
     */
    private boolean isDelete;

    public ItemTouchHelperCallBack(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public ItemTouchHelperCallBack() {
    }

    /**
     * 这个方法用于让RecyclerView拦截向上滑动，向下滑动，想左滑动
     *
     * @param recyclerView
     * @param viewHolder
     * @return dragFlags是上下方向的滑动 swipeFlags是左右方向的滑动
     */
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags;
        int swipeFlags = 0;
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            //GridLayoutManager模式下为上下左右四方向滑动
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        } else {
            //LinearLayoutManager模式为上下滑动
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            // 支持左右滑动(删除)操作,
            if (isDelete) {
                swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            }
        }

        return makeMovementFlags(dragFlags, swipeFlags);

    }

    /**
     * 针对drag状态，当前target对应的item是否允许移动
     * 我们一般用drag来做一些换位置的操作，就是当前对应的target对应的Item可以移动
     */
    @Override
    public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder current, RecyclerView.ViewHolder target) {
        return true;
    }

    /**
     * 移动中 drag状态下，在canDropOver()返回true时，会调用该方法
     *
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        return true;
    }

    /**
     * 如果onMove 为 true 移动后调用该方法
     * 调用该方法让我们拖动换位置的逻辑(需要自己处理变换位置的逻辑)
     *
     * @param recyclerView
     * @param viewHolder   被按下拖拽时候的 viewHolder
     * @param target       当前拖拽到的目标item的 viewHolder
     */
    @Override
    public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, int fromPos, @NonNull RecyclerView.ViewHolder target, int toPos, int x, int y) {
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
        if (recyclerView.getAdapter() instanceof ItemHelperListener) {
            ((ItemHelperListener) recyclerView.getAdapter()).onItemMove(viewHolder, target,fromPos,toPos);
        }
    }


    /**
     * 针对drag状态，当drag ItemView跟底下ItemView重叠时，可以给drag ItemView设置一个边界值
     * 设置后根据缩进调用onSelectedChanged
     * 让重叠不容易发生，相当于增大了drag Item的区域,视图不会产生变化
     */
    @Override
    public int getBoundingBoxMargin() {
        return 0;
    }

    /**
     * 针对drag状态，当滑动超过多少就可以出发onMove()方法(这里指onMove()方法的调用，并不是随手指移动的View)
     */
    public float getMoveThreshold(RecyclerView.ViewHolder viewHolder) {
        return .5f;
    }

    /**
     * 针对drag状态，在drag的过程中获取drag itemView底下对应的ViewHolder(一般不用我们处理直接super就好了)
     */
    @Override
    public RecyclerView.ViewHolder chooseDropTarget(@NonNull RecyclerView.ViewHolder selected, @NonNull List<RecyclerView.ViewHolder> dropTargets, int curX, int curY) {
        return super.chooseDropTarget(selected, dropTargets, curX, curY);
    }


    /**
     * 轻滑移动调用
     * 针对swipe状态，swipe 到达滑动消失的距离回调函数,一般在这个函数里面处理删除item的逻辑
     * 确切的来讲是swipe item滑出屏幕动画结束的时候调用
     *
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if (viewHolder.getBindingAdapter() instanceof ItemHelperListener) {
            ((ItemHelperListener) viewHolder.getBindingAdapter()).onItemSwiped(viewHolder);
        }
    }

    /**
     * 针对swipe状态，swipe滑动的位置超过了百分之多少就消失
     */
    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        return .5f;
    }

    /**
     * 针对swipe状态，swipe的逃逸速度，换句话说就算没达到getSwipeThreshold设置的距离，达到了这个逃逸速度item也会被swipe消失掉
     */
    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        return defaultValue;
    }

    /**
     * 针对swipe状态，swipe滑动的阻尼系数,设置最大滑动速度
     */
    @Override
    public float getSwipeVelocityThreshold(float defaultValue) {
        return defaultValue;
    }

    /**
     * 选中状态调用
     *
     * @param viewHolder
     * @param actionState
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        // 不在闲置状态
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder.getBindingAdapter() instanceof ItemHelperListener) {
                ((ItemHelperListener) viewHolder.getBindingAdapter()).onItemSelected(viewHolder);
            }
        }
    }

    /**
     * 移动结束后调用
     * 针对swipe和drag状态，当一个item view在swipe、drag状态结束的时候调用
     * drag状态：当手指释放的时候会调用
     * swipe状态：当item从RecyclerView中删除的时候调用，一般我们会在onSwiped()函数里面删除掉指定的item view
     *
     * @param recyclerView
     * @param viewHolder
     */
    @Override
    public void clearView(@NotNull RecyclerView recyclerView, @NotNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (recyclerView.getAdapter() instanceof ItemHelperListener) {
            ((ItemHelperListener) recyclerView.getAdapter()).onItemFinish(viewHolder);
        }
    }

    /**
     * 超出边界时会调用
     *
     * @param recyclerView
     * @param viewSize
     * @param viewSizeOutOfBounds
     * @param totalSize
     * @param msSinceStartScroll
     * @return
     */
    @Override
    public int interpolateOutOfBoundsScroll(@NonNull RecyclerView recyclerView, int viewSize, int viewSizeOutOfBounds, int totalSize, long msSinceStartScroll) {
        if (recyclerView.getAdapter() instanceof ItemHelperListener) {
            ((ItemHelperListener) recyclerView.getAdapter()).onItemOut();
        }
        return super.interpolateOutOfBoundsScroll(recyclerView, viewSize, viewSizeOutOfBounds, totalSize, msSinceStartScroll);
    }
}
