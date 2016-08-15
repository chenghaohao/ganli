package com.ganli.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kiracheng on 2016/6/18.
 */
public class CommonUtils {
    public static String timeStamp2Date(String seconds,String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        SimpleDateFormat format2 =  new SimpleDateFormat(format);
        Long time= new Long(seconds);
        String d = format2.format(time);
        return d;
//        if(format == null || format.isEmpty()) format = "yyyy-MM-dd";
//        SimpleDateFormat sdf = new SimpleDateFormat(format);
//        return sdf.format(new Date(Long.valueOf(seconds+"000")));
    }
    /**
     * 字符串转换成日期
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
