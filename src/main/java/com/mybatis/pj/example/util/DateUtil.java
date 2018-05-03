package com.mybatis.pj.example.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    /**
     *  只获取到日期
     * */
    public static final String DATESHOWFORMAT = "yyyy-MM-dd";
    /**
     * 只获取时间
     * */
    public static final String TIMESHOWFORMAT = "HH:mm:ss";
    /**
     * 获取日期+时间的格式
     * */
    public static final String DATETIMESHOWFORMAT = "yyyy-MM-dd HH:mm:ss";


    /**
     * 日期格式化(String转换为Date)
     *
     * @param dateStr
     *            日期字符串
     * @param patten
     *            处理结果日期的显示格式，如："YYYY-MM-DD"
     * @return
     */
    public static Date getDateToString(String dateStr, String patten) {
        if(StringHelper.isBlank(dateStr)){
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(patten);
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期格式化(Date转换为String)
     *
     * @param _date
     *            日期
     * @param patternString
     *            处理结果日期的显示格式，如："YYYY-MM-DD"
     * @return
     */
    public static String getStringToDate(Date _date, String patternString) {
        String dateString = "";
        if (_date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(patternString);
            dateString = formatter.format(_date);
        }
        return dateString;
    }

    public static Date getSystemDate(String patternString) throws  Exception{
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(patternString);
        Date formatDate = format.parse(format.format(date));
        return formatDate;
    }
}
