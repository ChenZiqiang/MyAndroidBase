package com.android.framework.uitls;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Fragment 管理
 * @author 陈自强
 * @date 2019/8/14
 */
public class FragmentMangerTool {
    private FragmentManager manager;
    private Fragment currentFragment;
    private int frameLayoutId;
    private FragmentTransaction transaction;

    public FragmentMangerTool(FragmentManager manager, int frameLayoutId) {
        this.manager = manager;
        this.frameLayoutId = frameLayoutId;

    }

    public void addOrShowFragment(Fragment fragment) {
        transaction = manager.beginTransaction();
        if (currentFragment != null) {
            if (currentFragment == fragment) {
                if (currentFragment.isHidden()) {
                    transaction.show(fragment).commit();
                }
                return;
            }
            // 如果当前fragment未被添加，则添加到Fragment管理器中
            if (!fragment.isAdded()) {
                transaction.hide(currentFragment)
                        .add(frameLayoutId, fragment).commit();
            } else {
                transaction.hide(currentFragment).show(fragment).commit();
            }
        } else {
            transaction.add(frameLayoutId, fragment).commit();
        }
        currentFragment = fragment;
    }

    public void hideFragment() {
        transaction = manager.beginTransaction();
        if (currentFragment != null) {
            transaction.hide(currentFragment).commit();
        }
    }

    public boolean isShow() {
        if (currentFragment == null) {
            return false;
        }
        if (!currentFragment.isAdded()) {
            return false;
        }
        return !currentFragment.isHidden();
    }

    public void setCurrentFragment(Fragment fragment) {
        currentFragment = fragment;
    }

    public void showFragment() {
        transaction = manager.beginTransaction();
        if (currentFragment != null) {
            if (!currentFragment.isAdded()) {
                transaction.add(frameLayoutId, currentFragment).commit();
            } else {
                if (currentFragment.isHidden()) {
                    transaction.show(currentFragment).commit();
                }
            }
        }
    }
}
