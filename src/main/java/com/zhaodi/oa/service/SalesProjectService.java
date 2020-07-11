package com.zhaodi.oa.service;

import com.zhaodi.bean.Page;
import com.zhaodi.oa.entity.OATable;
import com.zhaodi.oa.entity.OATableFields;
import com.zhaodi.oa.entity.WorkAttendance;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @author WangBo
 * @version 1.0
 * @Describe 描述
 * @date 2020/6/12 11:26
 */
public interface SalesProjectService {
    Page<OATable> getOATable(Page<OATable> param);
    List<OATableFields> getOATableFields(String billId);
    void insertTableFields(String s, String s1, String path);
    /**
     * @Describe 获取市场部下面的销售人员
     */
    Page<WorkAttendance> getWorkAttendance(Page<WorkAttendance> pageData) throws ParseException;
    /**
     * @Describe 获取岗位
     */
    List<Map<String, String>> getJobTitle();
}
