package com.zhaodi.oa.util;

import com.zhaodi.oa.entity.AtreeMenu;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author WangBo
 * @version 1.0
 * @Describe 描述
 * @date 2020/5/23 11:36
 */
public class SystemMethod {
    /**
     * 递归查找子节点
     * @param menu
     * @param menulist
     * @return
     **/
    public static AtreeMenu findChildren(AtreeMenu menu, List<AtreeMenu> menulist) {
        for (AtreeMenu item : menulist) {
            if (menu.getId().equals(item.getSuperId())) {
                if (menu.getChildren() == null) {
                    menu.setChildren(new ArrayList<AtreeMenu>());
                }
                menu.getChildren().add(findChildren(item, menulist));
            }
        }
        return menu;
    }
    /**
     * @Describe 根据年 月 获取对应的月份 天数
     * @author WangBo
     * @param year 年
     * @param month 月
     * */
    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
    /**
     * @Describe 根据日期 找到对应日期的 星期
     * @author WangBo
     * @param date 日期
     * */
    public static String getDayOfWeekByDate(String date) {
        String dayOfweek = "-1";
        try {
            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date myDate = myFormatter.parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat("E");
            String str = formatter.format(myDate);
            dayOfweek = str;

        } catch (Exception e) {
            System.out.println("错误!");
        }
        return dayOfweek;
    }
    /**
     * @Describe 判断日期是否是星期日
     * @author WangBo
     * @param bDate 年月日字符
     * */
    public static boolean isWeekend(String bDate) throws ParseException {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date bdate = format1.parse(bDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(bdate);
        if( cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            return true;
        } else{
            return false;
        }
    }
    /**
     * 判断两个日期直接的天数差
     * @author wangbo
     * @param a:日期a
     * @param b:日期b
     * 大日期参数放在参数b中
     */
    public static Integer between_days(String a, String b) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// 自定义时间格式

        Calendar calendar_a = Calendar.getInstance();// 获取日历对象
        Calendar calendar_b = Calendar.getInstance();

        Date date_a = null;
        Date date_b = null;

        try {
            date_a = simpleDateFormat.parse(a);//字符串转Date
            date_b = simpleDateFormat.parse(b);
            calendar_a.setTime(date_a);// 设置日历
            calendar_b.setTime(date_b);
        } catch (ParseException e) {
            e.printStackTrace();//格式化异常
        }

        long time_a = calendar_a.getTimeInMillis();
        long time_b = calendar_b.getTimeInMillis();

        long between_days = (time_b - time_a) / (1000 * 3600 * 24);//计算相差天数

        return Integer.parseInt(String.valueOf(between_days));
    }
}
