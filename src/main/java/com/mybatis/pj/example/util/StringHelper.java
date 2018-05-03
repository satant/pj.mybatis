package com.mybatis.pj.example.util;


import static java.lang.Math.random;

import java.util.Date;

/**
 * Created by ace on 2017/9/10.
 */
public class StringHelper {
    public static String getObjectValue(Object obj){
        return obj==null?"":obj.toString();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !StringHelper.isEmpty(str);
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !StringHelper.isBlank(str);
    }
    /**
     * 填充左边字符
     *
     * @param source
     *            源字符串
     * @param fillChar
     *            填充字符
     * @param len
     *            填充到的长度
     * @return 填充后的字符串
     */
    public static String fillLeft(String source, char fillChar, int len) {
        StringBuffer ret = new StringBuffer();
        if (null == source)
            ret.append("");
        if (source.length() > len) {
            ret.append(source);
        } else {
            int slen = source.length();
            while (ret.toString().length() + slen < len) {
                ret.append(fillChar);
            }
            ret.append(source);
        }
        return ret.toString();
    }
    /**
     * 按照规则生成编码
     * @param rule 编码规则
     * @return
     */
    public static String getBillCodeByRule(String rule){
        rule=rule.trim();//去掉两边空格
        if(StringHelper.isBlank(rule)) rule="yyMMdd4";
        int len = rule.length()-1;//获取编码规则中日期长度
        String BillCode = "";
        String str = rule.substring(len);
        int n = Integer.parseInt(str);//获取编码规则中随机数的位数

        if(len>0) {
            rule = rule.substring(0, len);
            rule=rule.replace("yyyyMMdd", DateUtil.getStringToDate(new Date(), "yyyyMMdd"));//先替换8位的日期
            rule = rule.replace("yyMMdd", DateUtil.getStringToDate(new Date(), "yyMMdd"));//在替换6位的日期
            rule=rule.replace("yyMM", DateUtil.getStringToDate(new Date(), "yyMM"));//在替换4位的日期
        }

        int Str = (int)(Math.pow(10,n)*random());//得到随机数
        BillCode = rule+StringHelper.fillLeft(String.valueOf(Str),'0',n);
        return BillCode;
    }
}
