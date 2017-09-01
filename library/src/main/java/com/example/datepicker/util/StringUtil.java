package com.example.datepicker.util;

import android.text.TextUtils;

import java.util.Locale;

/**
 * 字符串工具类
 *
 * @author colin
 * @version 1.0, 2016-03-31 下午4:54
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public final class StringUtil {
    /**
     * Don't let anyone instantiate this class.
     */
    private StringUtil() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * Returns true if the string is null or 0-length.
     *
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(CharSequence str) {
        return TextUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(CharSequence str) {
        return !(str == null || str.length() == 0 || "null".equals(str));
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * byte[]数组转换为16进制的字符串
     *
     * @param data 要转换的字节数组
     * @return 转换后的结果
     */
    public static String byteArrayToHexString(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (byte b : data) {
            int v = b & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.getDefault());
    }

    /**
     * 16进制表示的字符串转换为字节数组
     *
     * @param s 16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] d = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
            d[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
                    .digit(s.charAt(i + 1), 16));
        }
        return d;
    }

    /**
     * 将给定的字符串中所有给定的关键字标红
     *
     * @param sourceString 给定的字符串
     * @param keyword      给定的关键字
     * @return 返回的是带Html标签的字符串，在使用时要通过Html.fromHtml()转换为Spanned对象再传递给TextView对象
     */
    public static String keywordMadeRed(String sourceString, String keyword) {
        String result = "";
        if (sourceString != null && !"".equals(sourceString.trim())) {
            if (keyword != null && !"".equals(keyword.trim())) {
                result = sourceString.replaceAll(keyword,
                        "<font color=\"red\">" + keyword + "</font>");
            } else {
                result = sourceString;
            }
        }
        return result;
    }

    /**
     * 为给定的字符串添加HTML红色标记，当使用Html.fromHtml()方式显示到TextView 的时候其将是红色的
     *
     * @param string 给定的字符串
     * @return
     */
    public static String addHtmlRedFlag(String string) {
        return "<font color=\"red\">" + string + "</font>";
    }

    /**
     * 截取字符串最后几位
     *
     * @param str 原字符串
     * @return 截取后的字符串
     */
    public static String getLastTwoChar(String str) {
        if (str != null && str.length() > 2) {
            return str.substring(str.length() - 2, str.length());
        } else if (str != null) {
            return str;
        } else {
            return "";
        }
    }

    /**
     * 获取不为空指针的字符串
     *
     * @param str
     * @return
     */
    public static String getSafeString(String str) {
        return getSafeString(str, "");
    }

    /**
     * 获取不为空指针的字符串
     *
     * @param str
     * @param defStr
     * @return
     */
    public static String getSafeString(String str, String defStr) {
        if (isEmpty(str)) {
            if (isEmpty(defStr))
                return "";
            else
                return defStr;
        } else {
            return str;
        }
    }

    /**
     * 格式化显示金额
     */
    public static String format(float money) {
        return format("%.2f", money);
    }

    /**
     * 为format方法添加默认的Locale
     */
    public static String format(String format, Object... args) {
        return String.format(Locale.getDefault(), format, args);
    }

    public static String numberFormat(double amount){
        return format("%.1f",amount);
    }

    public static String numberFormat(String format, Object... args) {
        return String.format(Locale.getDefault(), format, args);
    }

}
