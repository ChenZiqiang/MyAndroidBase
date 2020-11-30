package com.android.framework.base;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.android.framework.mvvm.BaseViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author 陈自强
 * @version 1.0
 * @date 2020/11/27
 */
public abstract class FrameBaseFragmentBindVM<DB extends ViewDataBinding, VM extends BaseViewModel> extends FrameBaseFragmentBind<DB> {
    protected VM vm;

    @Override
    public void createViewModel() {
        vm = new ViewModelProvider(this).get(getViewModelClazz());
        if (vm != null) {
            initDialogVM(vm);
        }
    }

    public Class<? extends VM> getViewModelClazz() {
        //注意！！默认通过反射获取ViewModel的class，如果对稳定性与性能有要求，请在子类中重写此方法，返回viewmodel的class
        Type type = getClass().getGenericSuperclass();
        Type[] types = ((ParameterizedType) type).getActualTypeArguments();
        return (Class<? extends VM>) types[1];
    }
}
