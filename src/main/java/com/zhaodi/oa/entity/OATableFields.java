package com.zhaodi.oa.entity;

/**
 * @author WangBo
 * @version 1.0
 * @Describe 描述
 * @date 2020/6/12 15:08
 */
public class OATableFields {
    private String fieldId;
    private String fieldName;//数据库表对应英文字段
    private String fieldDbType;//数据库表对应字段类型
    private String indexDesc;//数据库表对应中文字段
    private Integer viewType;//主表字段还是从表字段  0：主  1：从表
    /*单行文本框子类型包括：
    1：文本
    2：整数
    3：浮点数
    浏览按钮子类型包括：
    1：人力资源
    2：日期
    3：会议室联系单
    4：部门
    5：仓库
    6：成本中心
    7：客户
    8：项目
    9：文档
    10：入库方式
    11：出库方式
    12：币种
    13：资产种类
    14：科目－全部
    15：科目－明细
    16：请求
    17：多人力资源
    18：多客户
    19：时间
    20：计划类型
    21：计划种类
    22：报销费用类型
    23：资产
    24：职务
    25：资产组
    26：车辆
    27：应聘人
    28：会议
    29：奖惩种类
    30：学历
    31：用工性质
    32：培训安排
    33：加班类型
    34：请假类型
    35：业务合同
    36：合同性质
    37：多文档
    38：相关产品*/
    private Integer type;//单据字段详细类型
    /*1：单行文本框
    2：多行文本框
    3：浏览按钮
    4：Check框
    5：选择框*/
    private Integer fieldHtmlType;//单据字段页面类型
    private Integer dsporder;//显示顺序
    private String detailTable;//明细表

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldDbType() {
        return fieldDbType;
    }

    public void setFieldDbType(String fieldDbType) {
        this.fieldDbType = fieldDbType;
    }

    public String getIndexDesc() {
        return indexDesc;
    }

    public void setIndexDesc(String indexDesc) {
        this.indexDesc = indexDesc;
    }

    public Integer getViewType() {
        return viewType;
    }

    public void setViewType(Integer viewType) {
        this.viewType = viewType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getFieldHtmlType() {
        return fieldHtmlType;
    }

    public void setFieldHtmlType(Integer fieldHtmlType) {
        this.fieldHtmlType = fieldHtmlType;
    }

    public Integer getDsporder() {
        return dsporder;
    }

    public void setDsporder(Integer dsporder) {
        this.dsporder = dsporder;
    }

    public String getDetailTable() {
        return detailTable;
    }

    public void setDetailTable(String detailTable) {
        this.detailTable = detailTable;
    }
}
