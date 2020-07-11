package com.zhaodi.oa.dao;
import com.zhaodi.bean.Page;
import com.zhaodi.oa.entity.OATable;
import com.zhaodi.oa.entity.OATableFields;
import com.zhaodi.oa.entity.WorkAttendance;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author WangBo
 * @version 1.0
 * @Describe 描述
 * @date 2020/6/12 14:58
 */
@Repository("SalesProjectDao")
public interface SalesProjectDao {
    /**
     * @Describe 获取表数据
     * @author WangBo
     */
    List<OATable> getOATable(@Param("page") Page<OATable> page);
    /**
     * @Describe 获取条数
     * @author WangBo
     */
    Integer getOATableCounts(@Param("page") Page<OATable> page);
    /**
     * @Describe 根据表ID查询对应表详细信息
     * @author WangBo
     */
    List<OATableFields> getOATableFields(@Param("billId") String billId);
    Integer insertTableFields(@Param("tableName") String tableName,@Param("tableDetail") String tableDetail,@Param("tableHtmlSrc") String tableHtmlSrc);
    /**
     * @Describe 获取市场部下面的销售人员
     * @author WangBo
     */
    List<WorkAttendance> getorkAttendance(@Param("page") Page<WorkAttendance> page);
    /**
     * @Describe 获取市场部下面的销售人员条数
     * @author WangBo
     */
    Integer getWorkCounts(@Param("page") Page<WorkAttendance> page);
    /**
     * @Describe 获取日期范围公共假日
     * @author WangBo
     */
    List<Map<String, String>> getHolidayDate(@Param("startDate") String startDate, @Param("endDate") String endDate);
    /**
     * @Describe 获取当前日期对应的假日情况
     * @author WangBo
     */
    Map<String, Object> getCurrentHoliday(String dayStr);
    /**
     * @Describe 获取该人改日签到时间集合
     * @author WangBo
     */
    List<Map<String, String>> getMobileSignTimes(@Param("user") Integer user, @Param("dateStr") String dateStr);
    /**
     * @Describe 获取该人改日服务日志填写次数
     * @author WangBo
     */
    Integer getFWLogCounts(@Param("user") Integer user, @Param("dateStr") String dateStr);
    /**
     * @Describe 获取该人改日项目日志填写次数
     * @author WangBo
     */
    Integer getYXLogCounts(@Param("user") Integer user, @Param("dateStr") String dateStr);
    /**
     * @Describe 获取人员请假情况
     * @author WangBo
     */
    List<Map<String, String>> getUserLeave(@Param("user") Integer user, @Param("startDate") String startDate, @Param("endDate") String endDate);
    /**
     * @Describe 获取市场部下的岗位
     * @author WangBo
     */
    List<Map<String, String>> getJobTitle();
}
