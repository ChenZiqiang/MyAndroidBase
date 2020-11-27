package com.android.framework.recycler;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;



import java.util.List;

/**
 * view page fragment adapter
 * @author 陈自强
 * @version 1.0
 * @date 2020/10/7
 */
public class ViewPagerFragmentAdapter extends FragmentStateAdapter {
    private List<Fragment> fragmentList;

    public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragmentList) {
        super(fragmentActivity);
        this.fragmentList = fragmentList;
    }

    public ViewPagerFragmentAdapter(@NonNull Fragment fragment, List<Fragment> fragmentList) {
        super(fragment);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
