package com.android.framework.uitls;

import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期时间工具类
 *
 * @create 陈自强
 * @date 2020/7/6
 */
public class DateTools {
    private final static String TAG = "DateTools";
    public final static String Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";
    public final static String Y_M_D_H_M = "yyyy-MM-dd HH:mm";
    public final static String Y_M_D = "yyyy-MM-dd";
    public final static String H_M_S = "HH:mm:ss";
    public final static String H_M = "HH:mm";

    /**
     * 每天23:59:59的毫秒数
     */
    public final static long ONE_DAY_S = 86399000;

    /**
     * String 类型时间转换long型
     *
     * @param time 要转换的时间
     * @param sdf  时间格式
     * @return 毫秒值
     */
    public static long stringDate2Long(String time, String sdf) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(sdf);
        long t = 0;
        try {
            Date date = dateFormat.parse(time);
            t = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * string 类型时间转换Date
     *
     * @param time 2020-1-1 0:0:0
     * @param sdf  yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date string2Date(String time, String sdf) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(sdf);
        Date date = new Date();

        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }


    /**
     * long型转换String型日期时间
     *
     * @param time 时间戳
     * @param sdf  转换样式
     * @return
     */
    public static String longDate2String(long time, String sdf) {
        SimpleDateFormat format = new SimpleDateFormat(sdf);
        Date date = new Date(time);
        return format.format(date);
    }

}
