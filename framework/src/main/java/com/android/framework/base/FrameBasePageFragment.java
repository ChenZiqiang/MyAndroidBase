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
    private int page;
    private boolean isFirst = true;
    private boolean created = false;

    public FrameBasePageFragment(int page) {
        this.page = page;
    }

    @Override
    protected void onFragmentCreated() {
        created = true;
        if (!isFirst) {
            showPage(page);
        }
    }

    public void showPage(int showPage) {
        if (page == showPage && created) {
            onShowFragment();
        }
    }

    public void showPage(int showPage, boolean firstShow) {
        if (firstShow && isFirst) {
            isFirst = false;
            showPage(showPage);
        }
    }

    /**
     * 懒加载
     */
    protected abstract void onShowFragment();
}
