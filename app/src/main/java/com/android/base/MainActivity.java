package com.android.base;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.android.base.databinding.ActivityMainBinding;
import com.android.framework.base.FrameBaseActivityBindVM;
import com.android.framework.mvvm.DataBindingHelper;
import com.bumptech.glide.Glide;

/**
 * @author 陈自强
 */
public class MainActivity extends FrameBaseActivityBindVM<ActivityMainBinding, MainViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView2(R.layout.activity_main);
        binding.setActivity(this);

//        Glide.with(this).load(R.drawable.loading).into(binding.image);
    }

//    @Override
//    public Class<? extends MainViewModel> getViewModelClazz() {
//        return MainViewModel.class;
//    }

    public void onDialog() {
        vm.showTip();
        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                vm.showTip("test");
                new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        vm.dismissTip();
                    }
                }, 2000);
            }
        }, 2000);


    }
}