package com.zhaodi.oa.entity;

import java.util.List;

/**
 * @author WangBo
 * @version 1.0
 * @Describe 考勤对应实体类
 * @date 2020/6/19 13:41
 */
public class WorkAttendance {
    public WorkAttendance(){}
    public WorkAttendance(WorkAttendance param) {
        this.id = param.id;
        this.supdepid = param.supdepid;
        this.departmentid = param.departmentid;
        this.jobtitle = param.jobtitle;
        this.region = param.region;
        this.departmentname = param.departmentname;
        this.lastName = param.lastName;
        this.jobtitlename = param.jobtitlename;
        this.project = param.project;
        this.attendanceCount = param.attendanceCount;
        this.leave = param.leave;
        this.noAttendance = param.noAttendance;
        this.actualAttendance = param.actualAttendance;
        this.lackDay = param.lackDay;
        this.completionRate = param.completionRate;
        this.days = param.days;
        this.yearMonth = param.yearMonth;
        this.whether=param.whether;
        this.maxDay=param.maxDay;
    }

    private Integer id;//人员ID
    private Integer supdepid;//部门上级ID
    private Integer departmentid;//部门ID
    private Integer jobtitle;//岗位ID
    private String region;//区域
    private String departmentname;//部门名称
    private String lastName;//人员名称
    private String jobtitlename;//岗位名称
    private String project;//项目

    private Integer attendanceCount;//应出勤天数合计
    private Integer leave;//请假次数
    private Integer noAttendance;//缺勤
    private Integer actualAttendance;//实际出勤天数
    private Integer lackDay;//缺签到/日志天数
    private double completionRate;//签到/日志完成率

    private List<String> days;//对应天数
    private String yearMonth;
    private Integer whether;//1代表是；2代表否
    private Integer maxDay;

    public Integer getMaxDay() {
        return maxDay;
    }

    public void setMaxDay(Integer maxDay) {
        this.maxDay = maxDay;
    }

    public Integer getWhether() {
        return whether;
    }

    public void setWhether(Integer whether) {
        this.whether = whether;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSupdepid() {
        return supdepid;
    }

    public void setSupdepid(Integer supdepid) {
        this.supdepid = supdepid;
    }

    public Integer getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(Integer departmentid) {
        this.departmentid = departmentid;
    }

    public Integer getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(Integer jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getRegion() {
        if(region!=null){
            return region.trim();
        }
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDepartmentname() {
        if(departmentname!=null){
            return departmentname.trim();
        }
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }

    public String getLastName() {
        if(lastName!=null){
            return lastName.trim();
        }
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJobtitlename() {
        if(jobtitlename!=null){
            return jobtitlename.trim();
        }
        return jobtitlename;
    }

    public void setJobtitlename(String jobtitlename) {
        this.jobtitlename = jobtitlename;
    }

    public Integer getAttendanceCount() {
        return attendanceCount;
    }

    public void setAttendanceCount(Integer attendanceCount) {
        this.attendanceCount = attendanceCount;
    }

    public Integer getLeave() {
        return leave;
    }

    public void setLeave(Integer leave) {
        this.leave = leave;
    }

    public Integer getNoAttendance() {
        return noAttendance;
    }

    public void setNoAttendance(Integer noAttendance) {
        this.noAttendance = noAttendance;
    }

    public Integer getActualAttendance() {
        return actualAttendance;
    }

    public void setActualAttendance(Integer actualAttendance) {
        this.actualAttendance = actualAttendance;
    }

    public Integer getLackDay() {
        return lackDay;
    }

    public void setLackDay(Integer lackDay) {
        this.lackDay = lackDay;
    }

    public double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(double completionRate) {
        this.completionRate = completionRate;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }
}
