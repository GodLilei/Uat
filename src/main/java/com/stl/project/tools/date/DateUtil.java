package com.stl.project.tools.date;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateUtil {
    /**
     * @return 今天的日期
     */
    public String nowDate(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    /**
     * @return 时分秒
     */
    public String hmsString(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss");
        return simpleDateFormat.format(date);
    }

    /**
     *
     * @return 年月日字符串
     */
    public String ymdString(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(date);
    }

    /**
     *
     * @return 四位随机数
     */
    public String fourRandom(){
        String four = (int)(Math.random()*10000) + "";
        while (four.length() != 4){
            four = (int)(Math.random()*10000) + "";
        }
        return four;
    }

    /**
     * 返回几天前或者几年前的数据
     * @param value 天:day,年:year,秒
     * @param num 天数
     * @return 日期
     */
    public String dateOfAfter(String value,int num){
        Calendar cal=Calendar.getInstance();
        switch (value){
            case "day":cal.add(Calendar.DATE,num);break;
            case "year":cal.add(Calendar.YEAR,num);break;
            case "second":cal.add(Calendar.SECOND,num);break;
            default:break;
        }
        Date time=cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
    }

    /**
     *
     * @param str 带时分秒的时间字符串
     * @return 不带时分秒的字符串
     */
    public String deleteHMS(String str) throws Exception {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf1.parse(str);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        return sdf2.format(date);
    }
}
