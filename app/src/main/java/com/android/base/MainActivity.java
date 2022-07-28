package com.android.base;

import android.os.Bundle;

import com.android.base.databinding.ActivityMainBinding;
import com.android.framework.base.FrameBaseActivityBindVM;
import com.android.framework.net.BaseCallBack;
import com.android.framework.net.BaseHttpBean;
import com.android.framework.uitls.GsonTools;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 演示界面
 *
 * @author 陈自强
 */
public class MainActivity extends FrameBaseActivityBindVM<ActivityMainBinding, MainViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindContentView(R.layout.activity_main);
        binding.setActivity(this);
        testDataBindingRecyclerView();
        binding.setRes(R.mipmap.ic_launcher);
    }

    public void testDataBindingRecyclerView() {
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
        testNet();
        vm.showTip();
    }

    /**
     * 网络请求演示
     */
    private void testNet() {
        String url = "http://weather-api.xiaochijiaoyu.cn/common/initialize/info";
        Map<String, String> map = new HashMap<>();
        map.put("token", "");
        map.put("version", "1.0.0");
        map.put("channel", "010");
        map.put("appClient", "100002");
        map.put("projectId", "2");
        OkGo.<String>post(url).upJson(GsonTools.object2json(map)).execute(new BaseCallBack<String>() {
            @Override
            public boolean isSuccess(BaseHttpBean<String> baseBean) {
                return false;
            }

            @Override
            public void onResultError(int code, String msg) {
                vm.dismissTip();
            }

            @Override
            public void httpSuccess(String data) {
                vm.dismissTip();
            }
        });

    }
}