package com.android.framework.mvvm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.framework.data.Tips;
import com.kongzue.dialog.v3.TipDialog;

/**
 * view model 基类
 *
 * @author 陈自强
 * @version 1.0
 * @date 2020/10/19
 */
public class FrameBaseViewModel extends ViewModel {
    private MutableLiveData<String> message = new MutableLiveData<>();
    private MutableLiveData<Integer> typeCode = new MutableLiveData<>();
    private MutableLiveData<String> showTip = new MutableLiveData<>();
    private MutableLiveData<String> dismissTip = new MutableLiveData<>();
    private MutableLiveData<Tips> tips = new MutableLiveData<>();
    private MutableLiveData<String> toast = new MutableLiveData<>();

    public MutableLiveData<String> getShowTip() {
        return showTip;
    }

    public MutableLiveData<String> getDismissTip() {
        return dismissTip;
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public MutableLiveData<Integer> getTypeCode() {
        return typeCode;
    }

    public MutableLiveData<Tips> getTips() {
        return tips;
    }

    public MutableLiveData<String> getToast() {
        return toast;
    }

    public void showTip() {
        showTip("");
    }

    public void showTip(String msg) {
        showTip.postValue(msg);
    }

    public void showTip(Tips tips) {
        this.tips.postValue(tips);
    }

    public void showTip(String msg, TipDialog.TYPE type) {
        tips.postValue(new Tips(msg, type));
    }

    public void dismissTip() {
        dismissTip.postValue("");
    }

    public void showToast(String msg) {
        toast.postValue(msg);
    }
}
