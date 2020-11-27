package com.android.framework.data;

import com.kongzue.dialog.v3.TipDialog;

/**
 * @author 陈自强
 * @version 1.0
 * @date 2020/11/27
 */
public class Tips {
    private String msg;
    private TipDialog.TYPE type;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public TipDialog.TYPE getType() {
        return type;
    }

    public void setType(TipDialog.TYPE type) {
        this.type = type;
    }
}
