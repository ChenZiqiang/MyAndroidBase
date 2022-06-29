package com.android.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.base.databinding.ActivityMainBinding;
import com.android.framework.base.FrameBaseActivityBindVM;
import com.android.framework.mvvm.DataBindingKotlin;
import com.android.framework.net.BaseCallBack;
import com.android.framework.net.BaseHttpBean;
import com.android.framework.net.BaseHttpHelper;
import com.orhanobut.logger.Logger;

import org.litepal.crud.callback.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler2;

/**
 * 演示界面
 * @author 陈自强
 */
public class MainActivity extends FrameBaseActivityBindVM<ActivityMainBinding, MainViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView2(R.layout.activity_main);
        binding.setActivity(this);
        TestAdapter adapter = new TestAdapter();

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
        list.add("l");
        list.add("m");
        list.add("n");
        list.add("o");
        list.add("p");
        list.add("q");
        list.add("r");
        list.add("s");
        list.add("t");
        adapter.setList(list);
        binding.setAdapter(adapter);
        binding.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                frame.refreshComplete();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.refreshComplete();
            }
        });

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

    /**
     * 网络请求演示
     */
    private void testNet() {
        BaseHttpHelper.onPost("url", new BaseCallBack<String>() {

            @Override
            public boolean isSuccess(BaseHttpBean<String> baseBean) {
                return false;
            }

            @Override
            public void onResultError(int code, String msg) {

            }

            @Override
            public void httpSuccess(String data) {

            }
        });
    }
}