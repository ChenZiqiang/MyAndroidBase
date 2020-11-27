package com.android.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.android.base.databinding.ActivityMainBinding;
import com.android.framework.base.FrameBaseActivityBindVM;

/**
 * @author 陈自强
 */
public class MainActivity extends FrameBaseActivityBindVM<ActivityMainBinding, MainViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView2(R.layout.activity_main);
        binding.setActivity(this);

    }


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