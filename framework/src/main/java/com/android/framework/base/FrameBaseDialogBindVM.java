package com.android.framework.base;

import android.content.DialogInterface;
import android.widget.Toast;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.android.framework.mvvm.FrameBaseViewModel;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author 陈自强
 * @version 1.0
 * @date 2020/11/27
 */
public abstract class FrameBaseDialogBindVM<DB extends ViewDataBinding, VM extends FrameBaseViewModel> extends FrameBaseDialogBind<DB> {
    protected VM model;


    @Override
    public void createViewModel() {
        model = new ViewModelProvider(this).get(getViewModelClazz());
        initViewModel(model);
    }

    @Override
    public void onDismiss(@NotNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public Class<? extends VM> getViewModelClazz() {
        //注意！！默认通过反射获取ViewModel的class，如果对稳定性与性能有要求，请在子类中重写此方法，返回viewmodel的class
        Type type = getClass().getGenericSuperclass();
        Type[] types = ((ParameterizedType) type).getActualTypeArguments();
        return (Class<? extends VM>) types[1];
    }
}
