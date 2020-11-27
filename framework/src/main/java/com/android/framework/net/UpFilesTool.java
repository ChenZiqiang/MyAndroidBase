package com.android.framework.net;

import com.android.framework.uitls.CommonTool;
import com.lzy.okgo.OkGo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 上传多个文件
 *
 * @author 陈自强
 * @version 1.0
 * @date 2020/11/27
 */
public class UpFilesTool {
    private final List<File> fileList;
    private final String url;
    private final List<File> upList = new ArrayList<>();
    private final List<File> errorList = new ArrayList<>();
    private UpFileCallBack callBack;
    private final int maxUpThread;

    public UpFilesTool(List<File> fileList, String url) {
        this(fileList, url, 3);
    }

    public UpFilesTool(List<File> fileList, String url,int maxUpThread) {
        this.fileList = fileList;
        this.url = url;
        this.maxUpThread = maxUpThread;
    }

    public void UpFiles() {
        for (int i = 0; i < maxUpThread && i < fileList.size(); i++) {
            upList.add(fileList.get(i));
        }

        for (File file : upList) {
            OkGo.<String>post(url).upFile(file).execute(new BaseCallBack() {
                @Override
                public void onFinish() {
                    super.onFinish();
                    fileList.remove(file);
                    upList.remove(file);
                }

                @Override
                public void onResultError(int code, String msg) {
                    errorList.add(file);
                    if(CommonTool.isListEmpty(upList) && CommonTool.isListEmpty(upList)){
                        if (callBack != null) {
                            callBack.callBack(errorList);
                        }
                    }
                }

                @Override
                public void httpSuccess(Object o) {
                    if (CommonTool.isListEmpty(upList) && !CommonTool.isListEmpty(upList)) {
                        UpFiles();
                    } else if (CommonTool.isListEmpty(upList) && CommonTool.isListEmpty(upList)) {
                        if (callBack != null) {
                            callBack.callBack(errorList);
                        }
                    }
                }
            });
        }
    }

    public interface UpFileCallBack {
        void callBack(List<File> errorList);
    }


    public void setCallBack(UpFileCallBack callBack) {
        this.callBack = callBack;
    }

}
