package com.zhaodi.oa.service.iml;
import com.alibaba.fastjson.JSONArray;
import com.zhaodi.bean.Page;
import com.zhaodi.bean.TimeRange;
import com.zhaodi.oa.dao.SalesProjectDao;
import com.zhaodi.oa.entity.OATable;
import com.zhaodi.oa.entity.OATableFields;
import com.zhaodi.oa.entity.WorkAttendance;
import com.zhaodi.oa.service.SalesProjectService;
import com.zhaodi.oa.util.SystemMethod;
import com.zhaodi.util.comMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author WangBo
 * @version 1.0
 * @Describe 描述
 * @date 2020/6/12 11:26
 */
@Service("SalesProjectService")
public class SalesProjectServiceIml implements SalesProjectService {
    DateFormat df = new SimpleDateFormat("HH:mm:ss");//时间格式

    @Resource(name="SalesProjectDao")
    private SalesProjectDao spDao;
    //获取表数据
    @Override
    public Page<OATable> getOATable(Page<OATable> param) {
        Page<OATable> pageData = new Page<>();
        List<OATable> table = spDao.getOATable(param);
        pageData.setBeans(table);
        Integer num=spDao.getOATableCounts(param);
        pageData.setCounts(num);
        pageData.setStatus(200);
        return pageData;
    }
    //根据表ID查询对应表详细信息
    @Override
    public List<OATableFields> getOATableFields(String billId) {
        return spDao.getOATableFields(billId);
    }

    @Override
    public void insertTableFields(String s, String s1, String path) {
        spDao.insertTableFields(s, s1, path);
    }
    /**
     * @Describe 获取市场部下面的销售人员
     * @author WangBo
     */
    @Override
    public Page<WorkAttendance> getWorkAttendance(Page<WorkAttendance> page) throws ParseException {
        /*
         * 1、条件筛选，按条件查询
         */
        Integer whether=page.getBean().getWhether();
        boolean isLimit=true;
        if(whether!=null && whether==2){
            isLimit=false;
        }
//        System.out.println(whether);
//        System.out.println(isLimit);

        Page<WorkAttendance> pageData = new Page<>();
        List<WorkAttendance> workList = spDao.getorkAttendance(page);
        /*
         * 2、计算公共假日，当月天数
         */
        String[] currentDate=new String[3];
        String pageYearMonth=page.getBean().getYearMonth();
        Integer month=0;
        if(pageYearMonth==null || pageYearMonth==""){
            currentDate= comMethod.getDateTime3().split("-");
            month=Integer.parseInt(currentDate[1])-1;
            //month=Integer.parseInt(currentDate[1]);
        }else{
            currentDate=pageYearMonth.split("-");
            month=Integer.parseInt(currentDate[1]);
        }
        Integer year=Integer.parseInt(currentDate[0]);

        Integer currentDays= SystemMethod.getDaysByYearMonth(year,month);//当月天数

        String startDate=year+"-"+month+"-01";
        String endDate=year+"-"+month+"-"+currentDays;
        //获取公共假日
        //select * from HrmPubHoliday where holidaydate<='2020-06-30' and holidaydate>='2020-06-01'
        //List<Map<String, String>> holidaysList=spDao.getHolidayDate(startDate,endDate);

        //循环当月天数
        Map<String,String> dayMap=getCurrentHoliday(currentDays,year,month);
        /*
         * 3、遍历计算日志和签到，如果有请假需要计算请假时间
         * 备注：每个人一栏签到，一栏日志，签到一天是4次，时间分别是9点之前，9点至十点半一次，1点至3点一次，3点至4点一次，
         *******否则就是无效签到，不计入这四次以内；一天当中没有签到的，记为缺勤。日志一天一篇，没有日志的记为0，超过一篇记为一篇。
         *******如果有请假，日志和签到处都写请假。
         */
        //查询签到有效时间规则

        //循环判断赋值
        List<WorkAttendance> attendanceList=new ArrayList<>();
        for(WorkAttendance workItem:workList){
            Integer userId=workItem.getId();//人员ID
            Integer jobtitle=workItem.getJobtitle();//岗位ID
            //查询该人员该月请假情况
            List<Map<String, String>> userLeave=spDao.getUserLeave(userId,startDate,endDate);
            JSONArray leaves=getLeaveSituation(userLeave,month);
            Set<String> leaveDays= (Set<String>) leaves.get(0);//请假天集合
            Map<String, String> leaveSituation= (Map<String, String>) leaves.get(1);//请假具体情况

            WorkAttendance work1 = new WorkAttendance(workItem);
            WorkAttendance work2 = new WorkAttendance(workItem);

            List<String> days1=new ArrayList<>();//签到==该月每日对应数据
            List<String> days2=new ArrayList<>();//日志==该月每日对应数据

            Integer attendanceCount=0;//应出勤天数合计
            Integer leave=0;//请假次数
            Integer noAttendance1=0;//缺勤
            Integer noAttendance2=0;//缺勤
            /*Integer totalQD=0;//总签到数
            Integer totalRZ=0;//总日志数*/
            //Integer actualAttendance=0;//实际出勤天数
            //Integer lackDay=0;//缺签到/日志天数
            //double completionRate=0;//签到/日志完成率
            //循环当月天数
            for(int i=1;i<=currentDays;i++){
                String dayStr=year+"-"+month+"-"+i;
                String holidayType=dayMap.get(""+i).split("===")[0];
                String holidayName=dayMap.get(""+i).split("===")[1];

                //查询签到
                List<Map<String, String>> m=spDao.getMobileSignTimes(userId,dayStr);

                if(holidayType.equals("0") || holidayType.equals("2")){//如果是工作日
                    //System.out.println(workItem.getLastName()+"  "+year+"-"+month+"-"+i+":"+"工作日");

                    //请假判断
                    if(leaveDays.contains(i+"") && leaveSituation.get(i+"").equals("1")){//如果当天请假
                        days1.add("请假");
                        //查询日志
                        days2.add("请假");
                        leave++;
                    }else if(leaveDays.contains(i+"") && leaveSituation.get(i+"").equals("2")){//含有请假
                        String qd=getMobileSignCounts(m,2,isLimit);
                        days1.add(qd);
                        if(qd.contains("0")){
                            noAttendance1++;
                        }/*else{
                            totalQD=totalQD+Integer.parseInt(qd.split("\\/")[0]);
                        }*/
                        //查询日志
                        if(jobtitle==386 || jobtitle==49){//岗位是386：服务经理 || 岗位是49：服务==技术指导工程师
                            Integer fwNum=spDao.getFWLogCounts(userId,dayStr);
                            days2.add(fwNum+"/含请假");//查询服务日志
                            /*totalRZ=totalRZ+fwNum;*/
                        }else {
                            Integer xmNum=spDao.getYXLogCounts(userId,dayStr);
                            days2.add(xmNum+"/含请假");//查询项目日志
                            /*totalRZ=totalRZ+xmNum;*/
                        }
                        leave++;
                    }else{//没有请假
                        String qd=getMobileSignCounts(m,3,isLimit);
                        days1.add(qd);

                        if(qd.equals("缺勤")){
                            noAttendance1++;
                        }
                        /*else{
                            totalQD=totalQD+Integer.parseInt(qd);
                        }*/
                        //查询日志
                        if(jobtitle==386 || jobtitle==49){//岗位是386：服务经理 || 岗位是49：服务==技术指导工程师
                            Integer num=spDao.getFWLogCounts(userId,dayStr);
                            if(num<1){
                                days2.add("缺勤");//查询服务日志
                                noAttendance2++;
                            }else{
                                days2.add(num+"");//查询服务日志
                                //totalRZ=totalRZ+num;
                            }
                        }else {
                            Integer num=spDao.getYXLogCounts(userId,dayStr);
                            if(num<1){
                                days2.add("缺勤");//查询项目日志
                                noAttendance2++;
                            }else{
                                days2.add(num+"");//查询项目日志
                                //totalRZ=totalRZ+num;
                            }
                        }
                    }


                    attendanceCount++;//应出勤天数合计
                }else{//非工作日

                    //System.out.println(workItem.getLastName()+"  "+year+"-"+month+"-"+i+":"+holidayName);
                    if(jobtitle==386 || jobtitle==49) {//岗位是386：服务经理 || 岗位是49：服务==技术指导工程师

                        if(leaveDays.contains(i+"") && leaveSituation.get(i+"").equals("1")){//如果当天请假
                            days1.add("请假");
                            //查询日志
                            days2.add("请假");
                            leave++;
                        }else if(leaveDays.contains(i+"") && leaveSituation.get(i+"").equals("2")) {//含有请假
                            String qd=getMobileSignCounts(m,2,isLimit);
                            days1.add(qd);
                            if(qd.contains("0")){
                                noAttendance1++;
                            }
                            Integer fwNum=spDao.getFWLogCounts(userId,dayStr);
                            /*else{
                                totalQD=totalQD+Integer.parseInt(qd.split("\\/")[0]);
                            }*/
                            days2.add(fwNum+"/含请假");//查询服务日志
                            leave++;
                        }else{
                            String qd=getMobileSignCounts(m,3,isLimit);
                            days1.add(qd);
                            if(qd.equals("缺勤")){
                                noAttendance1++;
                            }
                            Integer num=spDao.getFWLogCounts(userId,dayStr);
                            if(num<1){
                                days2.add("缺勤");//查询服务日志
                                noAttendance2++;
                            }else{
                                days2.add(num+"");//查询服务日志
                                //totalRZ=totalRZ+num;
                            }
                        }
                        attendanceCount++;//应出勤天数合计
                    }else{
                        days1.add(holidayName);
                        days2.add(holidayName);
                    }
                }
            }
            DecimalFormat df = new DecimalFormat("#.0000");

            //添加数据
            work1.setProject("签到");
            work1.setAttendanceCount(attendanceCount);
            work1.setLeave(userLeave.size());
            work1.setNoAttendance(noAttendance1);
            work1.setActualAttendance(attendanceCount-noAttendance1-leave);
            //double completionRate=0;//签到/日志完成率      实际签到次数/应出勤天数*4
            //work1.setCompletionRate(comMethod.div(totalQD,attendanceCount*4,2));
            work1.setLackDay(noAttendance1);
            work1.setMaxDay(currentDays);
            work1.setDays(days1);

            work2.setProject("日志");
            work2.setAttendanceCount(attendanceCount);
            work2.setLeave(userLeave.size());
            work2.setNoAttendance(noAttendance2);
            work2.setActualAttendance(attendanceCount-noAttendance2-leave);
            //double completionRate=0;//签到/日志完成率
            //work2.setCompletionRate(comMethod.div(totalRZ,attendanceCount,2));
            work2.setMaxDay(currentDays);
            work2.setLackDay(noAttendance2);
            work2.setDays(days2);

            attendanceList.add(work1);
            attendanceList.add(work2);
        }
        //返回数据
        pageData.setBeans(attendanceList);
        Integer num=spDao.getWorkCounts(page);
        pageData.setCounts(num);
        pageData.setStatus(200);
        return pageData;
    }
    /**
     * @Describe 请假情况
     * @author WangBo
     * @param list：人员请假情况
     */
    private JSONArray getLeaveSituation(List<Map<String, String>> list,Integer toMonth){
        DateFormat dff = new SimpleDateFormat("HH:mm");
        JSONArray json=new JSONArray();
        Set<String> leave = new HashSet<>();
        Map<String, String> leaveSituation=new HashMap<>();
        for(Map<String, String> ll:list){
            String[] dates1=ll.get("qjksrq").split("-");
            String[] dates2=ll.get("qjjsrq").split("-");
            Integer beginDate=Integer.parseInt(dates1[2]);//申请开始日期

            Integer beginDateMonth=Integer.parseInt(dates1[1]);//申请开始日期月
            //Integer endDateMonth=Integer.parseInt(dates2[1]);//申请结束日期月

//            leave.add(beginDate+"");
//            leave.add(endDate+"");
            long beginTime=0;
            long endTime=0;
            try {
                beginTime=dff.parse(ll.get("qjkssj")).getTime();//开始时间
                endTime=dff.parse(ll.get("qjjssj")).getTime();//结束时间
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Integer days=SystemMethod.between_days(ll.get("qjksrq"),ll.get("qjjsrq"))+1;
            if(beginDateMonth<toMonth){//开始日期小于当前月日期，代表跨月
                beginDate=1;//前月的日期不算，从后一月第一天开始算
                String ksrq=dates2[0]+"-"+dates2[1]+"-01";
                days=SystemMethod.between_days(ksrq,ll.get("qjjsrq"))+1;
            }
            //beginTime>TimeRange.TIME_08 || endTime < TimeRange.TIME_17_30
            if(days==1){
                if(beginTime<=TimeRange.TIME_08 && endTime >=TimeRange.TIME_17_30){//判断时间是一天的
                    leaveSituation.put((beginDate)+"","1");
                } else{
                    leaveSituation.put((beginDate)+"","2");
                }
                leave.add((beginDate)+"");
            }else{
                for (int i=0;i<days;i++){//1、代表请假（代表满满一天）  2、代表含有请假（小于一天的） 3、没有请假(或者空)
                    if(i==0 && (beginTime>TimeRange.TIME_08)){//判断时间是一天的
                        leaveSituation.put((beginDate+i)+"","2");
                    }else if(i==(days-1) && endTime < TimeRange.TIME_17_30){//判断时间是一天的
                        leaveSituation.put((beginDate+i)+"","2");
                    }else {
                        leaveSituation.put((beginDate+i)+"","1");
                    }
                    leave.add((beginDate+i)+"");
                }
            }
        }
        json.add(leave);
        json.add(leaveSituation);
        return json;
    }
    /**
     * @Describe 根据考勤标准统计对应签到的次数
     * @author WangBo
     * @param listMap:签到时间集合 关键字段：operateTime
     *        isLeave:1、代表请假（代表满满一天）  2、代表含有请假（小于一天的） 3、没有请假
     *        limit:false不做限制，签到多少次就是多少次,true刚好相反
     */
    private String getMobileSignCounts(List<Map<String, String>> listMap,Integer isLeave,boolean limit){
        Integer total=0;
        String timeStr="";
        if(limit){//如果严格做时间限制计算次数
            boolean limit1=true;
            boolean limit2=true;
            boolean limit3=true;
            boolean limit4=true;
            for (Map<String, String> m:listMap){
                long itemTime= 0;
                try {
                    itemTime = df.parse(m.get("operateTime")).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //时间在范围内才做统计
                if((itemTime > TimeRange.TIME_16 || itemTime < TimeRange.TIME_09) && limit1){
                    total++;
                    limit1=false;
                }else if(itemTime >= TimeRange.TIME_09 && itemTime < TimeRange.TIME_10_30 && limit2){
                    total++;
                    limit2=false;
                }else if(itemTime >= TimeRange.TIME_13 && itemTime < TimeRange.TIME_15 && limit3){
                    total++;
                    limit3=false;
                }else if(itemTime >= TimeRange.TIME_15 && itemTime < TimeRange.TIME_18 && limit4){
                    total++;
                    limit4=false;
                }
            }
        }else{
            total=listMap.size();//不做限制，签到多少次就是多少次
        }

        if(isLeave==1){
            timeStr="请假";
        }else if(isLeave==2){
            timeStr=total+"/含请假";
        }else if(total>0){
            timeStr=""+total;
        }else {
            timeStr="缺勤";
        }
        return timeStr;
    }
    /**
     * @Describe 查询当月每天对应节假日情况
     * @author WangBo
     */
    private Map<String,String> getCurrentHoliday(Integer currentDays,Integer year,Integer month) throws ParseException {
        Map<String,String> dayMap=new HashMap<>();
        for(int i=1;i<=currentDays;i++){
            String dayStr=year+"-"+String.format("%02d", month)+"-"+String.format("%02d", i);
            //获取公共假日
            Map<String, Object> holidays=spDao.getCurrentHoliday(dayStr);
            if(holidays!=null){//changetype:1、公共假日；2、调配工作日
                if(SystemMethod.isWeekend(dayStr)){
                    dayMap.put(""+i,holidays.get("changetype").toString()+"==="+holidays.get("holidayname")+"&&星期日");
                }else{
                    dayMap.put(""+i,holidays.get("changetype").toString()+"==="+holidays.get("holidayname"));
                }
            }else if(SystemMethod.isWeekend(dayStr)){//如果是星期日
                dayMap.put(""+i,"7===星期日");
            }else{
                dayMap.put(""+i,"0===工作日");
            }
        }
        return dayMap;
    }
    /**
     * @Describe 获取人员岗位
     * @author WangBo
     */
    @Override
    public List<Map<String, String>> getJobTitle() {
        return spDao.getJobTitle();
    }

}
