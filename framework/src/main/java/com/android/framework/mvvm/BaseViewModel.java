package com.android.framework.mvvm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * view model 基类
 * @author 陈自强
 * @version 1.0
 * @date 2020/10/19
 */
public class BaseViewModel extends ViewModel {
    private MutableLiveData<String> message = new MutableLiveData<>();
    private MutableLiveData<Integer> typeCode = new MutableLiveData<>();

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public MutableLiveData<Integer> getTypeCode() {
        return typeCode;
    }
}
