package com.zhaodi.custom.entity;
import java.util.List;
/**
 * @version 1.0
 * @Describe 描述
 * @Author WangBo
 * @Date 2020/5/17 11:05
 */
public class MenuTree {
    //棠单ID
    private String id;
    //父级菜单ID
    private String superId;
    //菜单名称
    private String title;
    //树形节点链接，设置点击打开,未设置不会跳转
    private String href;
    private boolean spread = false;
    //棠单图标，图片链接或是图片附伴UUID
    private String icon;
    //是否本页打开
    private String target;
    //子菜单集合
    private List<MenuTree> child;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSuperId() {
        return superId;
    }

    public void setSuperId(String superId) {
        this.superId = superId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean isSpread() {
        return spread;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<MenuTree> getChild() {
        return child;
    }

    public void setChild(List<MenuTree> child) {
        this.child = child;
    }
}
