package com.zhaodi.util;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class comMethod {
    /**
     * 根据属性，获取get方法
     * @param ob 对象
     * @param name 属性名
     * @return
     * @throws Exception
     */
    public static Object getGetMethod(Object ob , String name)throws Exception{
        Method[] m = ob.getClass().getMethods();
        for(int i = 0;i < m.length;i++){
            if(("get"+name).toLowerCase().equals(m[i].getName().toLowerCase())){
                return m[i].invoke(ob);
            }
        }
        return null;
    }


    /**获取时间yyyy-MM-dd HH:mm:ss格式
     * @author wangbo
     * */
    public static String getDateTime1()
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tim = df.format(new Date());

        return tim;
    }

    /**获取时间yyyyMMdd格式
     * @author wangbo
     * */
    public static String getDateTime2()
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String tim = df.format(new Date());

        return tim;
    }

    /**获取时间yyyy-MM-dd格式
     * @author wangbo
     * */
    public static String getDateTime3()
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String tim = df.format(new Date());

        return tim;
    }
    /**
     * @Describe 把一个字符串中的大写转为小写，小写转换为大写
     */
/*    public static String exChange(String str){
        for(int i=0;i<str.length();i++){
            //如果是小写
            if(str.substring(i, i+1).equals(str.substring(i, i+1).toLowerCase())){
                str.substring(i, i+1).toUpperCase();
            }else{
                str.substring(i, i+1).toLowerCase();
            }
        }
        return str;
    }*/
    /**
     * 利用正则表达式判断字符串是否是数字
     * @param str
     * @return
     */
    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
    /**
     * 对double数据进行取精度.
     * @param value  double数据.
     * @param scale  精度位数(保留的小数位数).
     * @param roundingMode  精度取值方式.
     * @return 精度计算后的数据.
     */
    public static double round(double value, int scale,int roundingMode) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, roundingMode);
        double d = bd.doubleValue();
        bd = null;
        return d;
    }
    /**
     * double 除法
     * @param d1
     * @param d2
     * @param scale 四舍五入 小数点位数
     * @return
     */
    public static double div(double d1,double d2,int scale){
        //  当然在此之前，你要判断分母是否为0，
        //  为0你可以根据实际需求做相应的处理
        if(d2==0){
            System.out.println("分母不能为0");
            return 0;
        }
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.divide(bd2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
