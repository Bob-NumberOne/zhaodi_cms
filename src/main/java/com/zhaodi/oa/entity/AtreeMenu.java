package com.zhaodi.oa.entity;

import java.util.List;

/**
 *作为棠单树返回前台的数据模型 前端树形棠单使用layui-atree
 **/
 public class AtreeMenu {
     //棠单ID
     private String id;
     //菜单名称
     private String title;
     //是否默认展开
     private boolean spread = false;
     //树形节点链接，设置点击打开,未设置不会跳转
     private String href;
     //棠单图标，图片链接或是图片附伴UUID
     private String menuIcon;
     //父级菜单ID
     private String superId;
     //父级菜单名称
     private String superName ;
     //子菜单築合
     private List<AtreeMenu> children;
     //子菜单集合
     private List<Integer> childrenList;

    public List<Integer> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<Integer> childrenList) {
        this.childrenList = childrenList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSpread() {
        return spread;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public String getSuperId() {
        return superId;
    }

    public void setSuperId(String superId) {
        this.superId = superId;
    }

    public String getSuperName() {
        return superName;
    }

    public void setSuperName(String superName) {
        this.superName = superName;
    }

    public List<AtreeMenu> getChildren() {
        return children;
    }

    public void setChildren(List<AtreeMenu> children) {
        this.children = children;
    }
}

