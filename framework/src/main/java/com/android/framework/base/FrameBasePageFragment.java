package com.android.framework.base;

import androidx.databinding.ViewDataBinding;

import com.android.framework.mvvm.FrameBaseViewModel;

/**
 * viewPage用 fragment,主要用于viewPage的懒加载
 *
 * @author admin
 * @version 1.0
 * @date 2022/7/28
 */
public abstract class FrameBasePageFragment<DB extends ViewDataBinding, VM extends FrameBaseViewModel> extends FrameBaseFragmentBindVM<DB, VM> {
    protected int page;
    private boolean isFirst = true;
    private boolean isCreated = false;

    public FrameBasePageFragment(int page) {
        this.page = page;
    }

    @Override
    protected void onFragmentCreated() {
        isCreated = true;
        if (!isFirst) {
            showFragmentPage(page, false);
        }
    }

    /**
     * viewpage 子fragment 懒加载
     *
     * @param showPage          做为第几个页面的判断
     * @param alwaysRefreshData 是否每次都刷新数据，false为只刷新一次数据，true为每次滑到页面都刷新
     */
    public void showFragmentPage(int showPage, boolean alwaysRefreshData) {
        if (page != showPage || !isCreated) {
            return;
        }

        if (isFirst) {
            isFirst = false;
            onShowFragment();
            refreshData();
            return;
        }
        if (alwaysRefreshData) {
            refreshData();
        }
    }

    protected abstract void onShowFragment();

    protected abstract void refreshData();
}
