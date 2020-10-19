package com.android.framework.uitls;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * 通用工具类.
 */
public class CommonTool {
    public static boolean isListEmpty(List list) {
        return list == null || list.isEmpty();
    }

    /**
     * 获取Activity的名字
     * @param context
     * @return
     */
    public static String getSimpleActivityName(Context context) {
        String contextString = context.toString();
        String activityName = contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@"));
        return activityName;
    }

    /**
     * 获取Activity完整的路径和名字
     * @param context
     * @return
     */
    public static String getActivityName(@NonNull Context context) {
        String contextString = context.toString();
        String packPager = contextString.substring(0, contextString.indexOf("@"));
        return packPager;
    }


    /**
     * 获取当前程序版本号
     */
    public static int getAppVersionCode(Context context) {
        int versioncode = 1;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versioncode = pi.versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception" + e.getMessage());
        }
        return versioncode;
    }

    /**
     * 获取当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception" + e.getMessage());
        }
        return versionName;
    }


}
