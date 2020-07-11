package com.zhaodi.custom.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangBo
 * @version 1.0
 * @Describe 菜单实体
 * @date 2020/4/8 9:04
 */
public class MenuBean {
    private long menuId;//菜单id
    private String menuName;//菜单名称
    private String href;//菜单路径
    private long parentId;//父级id
    private Integer grade;//等级
    private Integer sorting;//排序
    private Integer flag;//标志
    private long createrId;//创建人ID
    private String creater;//创建人
    private String target;//_self或者_blank
    private String icon;//图标
    private String creatTime;
    private String updateTime;
    private long updater;
    private String menuType;//1、更新；2、添加同级下级节点；3、添加同级节点
    private List<MenuBean> child = new ArrayList<MenuBean>();

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getSorting() {
        return sorting;
    }

    public void setSorting(Integer sorting) {
        this.sorting = sorting;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public long getCreaterId() {
        return createrId;
    }

    public void setCreaterId(long createrId) {
        this.createrId = createrId;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
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

    public List<MenuBean> getChild() {
        return child;
    }

    public void setChild(List<MenuBean> child) {
        this.child = child;
    }
}
