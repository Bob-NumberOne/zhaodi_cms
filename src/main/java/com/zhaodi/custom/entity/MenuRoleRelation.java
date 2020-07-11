package com.zhaodi.custom.entity;

/**
 * @author WangBo
 * @version 1.0
 * @Describe 菜单角色关系
 * @date 2020/7/3 9:08
 */
public class MenuRoleRelation {
    private long id;//表ID
    private long roleId;//角色ID
    private long menuId;//菜单ID
    private String menuName;//菜单名称
    private String authorityName;//功能名称
    private long creater;
    private String creatTime;
    private long updater;
    private String updateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public long getCreater() {
        return creater;
    }

    public void setCreater(long creater) {
        this.creater = creater;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public long getUpdater() {
        return updater;
    }

    public void setUpdater(long updater) {
        this.updater = updater;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
