package com.zhaodi.oa.entity;

/**
 * @author WangBo
 * @version 1.0
 * @Describe 表信息
 * @date 2020/6/12 15:00
 */
public class OATable {
    private String id;//表ID
    private String tableName;//表名称
    private String indexDesc;//表名称
    private String workflowName;//流程名称
    private String tableDetail;
    private String tableHtmlSrc;
    private Integer isValid;//是否有效 0：无效  1：有效  2：测试  3：已删除

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableDetail() {
        return tableDetail;
    }

    public void setTableDetail(String tableDetail) {
        this.tableDetail = tableDetail;
    }

    public String getTableHtmlSrc() {
        return tableHtmlSrc;
    }

    public void setTableHtmlSrc(String tableHtmlSrc) {
        this.tableHtmlSrc = tableHtmlSrc;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIndexDesc() {
        return indexDesc;
    }

    public void setIndexDesc(String indexDesc) {
        this.indexDesc = indexDesc;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}
