package com.zhaodi.custom.entity;

/**
 * @author WangBo
 * @version 1.0
 * @Describe 角色实体
 * @date 2020/7/1 16:01
 */
public class UseRole {
    private long roleId;//角色ID
    private String roleName;//角色名称
    private String roleDescribe;//角色描述
    private String roleCode;//角色编码
    private Integer sort;//角色排序
    private long creater;
    private String createTime;
    private String creatName;
    private String updateTime;
    private long updater;

    private Integer roleNum;//角色功能数量
    private Integer userNum;//角色用户数量

    public Integer getRoleNum() {
        return roleNum;
    }

    public void setRoleNum(Integer roleNum) {
        this.roleNum = roleNum;
    }

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescribe() {
        return roleDescribe;
    }

    public void setRoleDescribe(String roleDescribe) {
        this.roleDescribe = roleDescribe;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public long getCreater() {
        return creater;
    }

    public void setCreater(long creater) {
        this.creater = creater;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreatName() {
        return creatName;
    }

    public void setCreatName(String creatName) {
        this.creatName = creatName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public long getUpdater() {
        return updater;
    }

    public void setUpdater(long updater) {
        this.updater = updater;
    }
}
