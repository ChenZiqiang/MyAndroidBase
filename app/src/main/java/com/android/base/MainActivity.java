package com.android.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.base.databinding.ActivityMainBinding;
import com.android.framework.base.FrameBaseActivityBindVM;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 陈自强
 */
public class MainActivity extends FrameBaseActivityBindVM<ActivityMainBinding, MainViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView2(R.layout.activity_main);
        binding.setActivity(this);
        TestAdapter adapter = new TestAdapter();
//        binding.setAdapter(adapter);

        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("g");
        list.add("h");
        list.add("i");
        list.add("j");
        list.add("k");
        adapter.setList(list);
        binding.setAdapter(adapter);
//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        manager.setOrientation(RecyclerView.VERTICAL);
//        binding.recycler.setLayoutManager(manager);
//        binding.recycler.setAdapter(adapter);
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