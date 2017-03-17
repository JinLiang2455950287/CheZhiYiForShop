package com.ruanyun.chezhiyi.commonlib.util;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;

import com.hyphenate.util.HanziToPinyin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 * author: zhangsan on 16/8/6 下午2:17.
 */
public class StringUtil {
    /**
      * 获取字符串拼音首字母
      *@author zhangsan
      *@date   16/8/27 上午9:06
      */
    public static  String getUserLetter(String userNick){
        final String DefaultLetter = "#";
        String letter = DefaultLetter;
        final class GetInitialLetter {
            String getLetter(String name) {
                if (TextUtils.isEmpty(name)) {
                    return DefaultLetter;
                }
                char char0 = name.toLowerCase().charAt(0);
                if (Character.isDigit(char0)) {
                    return DefaultLetter;
                }
                ArrayList<HanziToPinyin.Token> l = HanziToPinyin.getInstance().get(name.substring(0, 1));
                if (l != null && l.size() > 0 && l.get(0).target.length() > 0)
                {
                    HanziToPinyin.Token token = l.get(0);
                    String letter = token.target.substring(0, 1).toUpperCase();
                    char c = letter.charAt(0);
                    if (c < 'A' || c > 'Z') {
                        return DefaultLetter;
                    }
                    return letter;
                }
                return DefaultLetter;
            }
        }

        if ( !TextUtils.isEmpty(userNick) ) {
            letter = new GetInitialLetter().getLetter(userNick);
            //user.setInitialLetter(letter);
            return letter;
        }
        if (letter == DefaultLetter && !TextUtils.isEmpty(userNick)) {
            letter = new GetInitialLetter().getLetter(userNick);
        }
        return letter;
    }


    private static SimpleDateFormat getTimeFormat(String format){
        return  new SimpleDateFormat(format);
    }

     private static SimpleDateFormat getTimeFormat(){
         return  getTimeFormat("yyyy-MM-dd HH:mm:ss");
     }

     public static String getTimeStrFromFormatStr(String format,String dateStr){
        SimpleDateFormat timeFormat=getTimeFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat dateFormat=new SimpleDateFormat(format);
        Date date= null;
        try {
            date = timeFormat.parse(dateStr);

        } catch (ParseException e) {
            e.printStackTrace();
            return  dateStr;
        }
        return  dateFormat.format(date);
    }


    /**
     * @param dateStr
     * @return
     */
    public static Date getDateFromString(String dateStr){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }
        return date;
    }

    /**
     * Compare the receiver to the specified {@code Date} to determine the relative
     * ordering.
     *
     * @param formatSrc
     * @param dateSrcStr
     * @param dateSrcStr2
     * @return an {@code int < 0} if this {@code Date} is less than the specified {@code Date}, {@code 0} if
     *         they are equal, and an {@code int > 0} if this {@code Date} is greater.
     */
    public static int getDateFromStr(String formatSrc, String dateSrcStr, String dateSrcStr2) {
        SimpleDateFormat timeFormat = getTimeFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatSrc);
        Date date = null;
        Date date2 = null;
        try {
            date = timeFormat.parse(dateSrcStr);
            date2 = timeFormat.parse(dateSrcStr2);

            date = dateFormat.parse( dateFormat.format(date) );
            date2 = dateFormat.parse( dateFormat.format(date2) );

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.compareTo(date2);
    }


    public static int getDateCompare(String predictTime) {
        if (!"".equals(predictTime) && predictTime.length() >= 10) {
            Date currentDate = new Date(System.currentTimeMillis());
            Date predictDate;
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                predictDate = format.parse(predictTime.substring(0, 10) /*"2016-01-03"*/);
                currentDate = format.parse(format.format(currentDate));
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
            return predictDate.compareTo(currentDate);
        }
        return 0;
    }

    public static String getTimeStr(long time){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date(time);
       return dateFormat.format(date);
    }

    /**
     *
     * @param time   HH:mm
     * @return   毫秒数
     */
    public static Long getTimeLong(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date;
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date(System.currentTimeMillis());
        }
        return date.getTime();
    }


    public static String getTimeStr(String format, Date date){
        SimpleDateFormat dateFormat=new SimpleDateFormat(format);
       return dateFormat.format(date);
    }


    /**
      * 获取文章列表时间格式字符串
      *@author zhangsan
      *@date   16/9/13 上午11:56
      */
    public static String getArtileTime(String dateStr){
    /*    SimpleDateFormat timeFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat=new SimpleDateFormat("MM/dd HH:mm");
        Date date= null;
        try {
            date = timeFormat.parse(dateStr);

        } catch (ParseException e) {
            e.printStackTrace();
            return  dateStr;
        }
        return  dateFormat.format(date);*/
        return getTimeStrFromFormatStr("MM/dd HH:mm",dateStr);
    }
    /**
      * 从完整时间截取 小时分钟
      *@author zhangsan
      *@date   16/10/18 下午2:23
      */
    public static String getTimeFromDate(String date){
        if(TextUtils.isEmpty(date)){
            return "";
        }
        return getTimeStrFromFormatStr("HH:mm",date);
    }


    public static String getRegisterDate(String dateStr){
        if(TextUtils.isEmpty(dateStr)){
            return "";
        }
        return getTimeStrFromFormatStr("yyyy-MM-dd",dateStr);
    }

    /**
      * 获取案例时间戳
      *@author zhangsan
      *@date   16/9/13 上午11:56
      */
    public static String getCaseInfoTime(String dateStr){
        return getTimeStrFromFormatStr("MM月dd日",dateStr);
    }

    /**
     * 返回月日(MM.dd)
     * @param dateStr
     * @return
     */
    public static String getMonthDayTime(String dateStr){
        SimpleDateFormat timeFormat=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat=new SimpleDateFormat("MM.dd");
        Date date= null;
        try {
            date = timeFormat.parse(dateStr);

        } catch (ParseException e) {
            e.printStackTrace();
            return  dateStr;
        }
        return  dateFormat.format(date);
    }


    /**
     * 获取删除的图片的id  a,b,c,d 形式字符串
     * @param array
     * @return     String   a,b,c,d
     */
    public static String getDelAttachIdtoString(int[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(array[0]);
        for (int i = 1; i < array.length; i++) {
            sb.append(",");
            sb.append(array[i]);
        }
        return sb.toString();
    }

    /**
     * 是否是手机号
     * @param mobiles
     * @return
     */
    public static boolean isPhone(String mobiles) {
        if (TextUtils.isEmpty(mobiles)) return false;
//        ^((13[0-9])|(15[^4,\D])|(17[678])|(18[^4,\D]))\d{8}$
        Pattern p = Pattern.compile("^1[0-9]{10}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证车牌号
     * @param cph
     * @return  true-- 车牌号格式正确   false --车牌号格式不对
     */
    public  static  boolean isCarNum(String cph){
        //cph="WJ12345";
//      "^[\u4e00-\u9fa5|WJ]{1}[A-Z0-9]{6}$"
        Pattern pattern = Pattern.compile("^[\\u4e00-\\u9fa5|WJ]{1}[A-Z]{1}[A-Z0-9]{5}$");
        Matcher matcher = pattern.matcher(cph);
        return matcher.matches();
    }

    /**
     * 验证车牌号
     * @param cph
     * @return  true-- 车牌号格式正确   false --车牌号格式不对
     */
    public  static  boolean isCarNumWithoutFirst(String cph){
        Pattern pattern = Pattern.compile("^[A-Z]{1}[A-Z0-9]{5}$");
        Matcher matcher = pattern.matcher(cph);
        return matcher.matches();
    }

    public static SpannableStringBuilder builderString(SpannableStringBuilder builder, String text, Object what, int flags) {
        int start = builder.length();
        builder.append(text);
        builder.setSpan(what, start, builder.length(), flags);
        return builder;
    }

    public static Long getSelectStringTime(int hourOfDay, int minute) {
        StringBuilder sb = new StringBuilder();
        if (hourOfDay< 10) {
            sb.append("0");
        }
        sb.append(hourOfDay);
        sb.append(":");
        if (minute< 10) {
            sb.append("0");
        }
        sb.append(minute);
        return getTimeLong(sb.toString());
    }
}
