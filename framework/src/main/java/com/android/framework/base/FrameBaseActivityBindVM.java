package com.android.framework.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.android.framework.mvvm.FrameBaseViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author 陈自强
 * @version 1.0
 * @date 2020/11/27
 */
public abstract class FrameBaseActivityBindVM<DB extends ViewDataBinding, VM extends FrameBaseViewModel> extends FrameBaseActivityBind<DB> {

    protected VM vm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createViewModel();
    }

    private void createViewModel() {
        vm = new ViewModelProvider(this).get(getViewModelClazz());
        if (vm != null) {
            initDialogVM(vm);
        }
    }


    public Class<? extends VM> getViewModelClazz() {
        Type type = getClass().getGenericSuperclass();
        Type[] types = ((ParameterizedType) type).getActualTypeArguments();
        return (Class<? extends VM>) types[1];
        //注意！！默认通过反射获取ViewModel的class，如果对稳定性与性能有要求，请在子类中重写此方法，返回viewmodel的class
//        return (Class<? extends VM>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

}
