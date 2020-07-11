package com.zhaodi.custom.service;
import com.zhaodi.bean.JsonResult;
import com.zhaodi.bean.Page;
import com.zhaodi.custom.entity.*;
import com.zhaodi.oa.entity.AtreeMenu;

import java.util.List;
import java.util.Map;

public interface BasicUserService {
    List<User> getNames();
    /**
     * @Describe 根据人员id查找对应的资源列表
     */
    List<String> findResourcesByName(long userId);
    /**
     * @Describe 根据人员id查找对应角色
     */
    List<Long> findRoleByName(long userId);
    /**
     * @Describe 查询子菜单
     */
    List<MenuTree> findAuthMenu(long userId);
    /**
     * @Describe 获取默认菜单资源
     */
    List<MenuHome> findMenuHome();
    /**
     * @Describe 菜单设置--查询菜单资源
     */
    List<AtreeMenu> getMenuTree(Integer type);
    /**
     * @Describe 菜单设置--根据ID查询菜单资源
     */
    MenuBean getMenuById(String menuId);
    /**
     * @Describe 菜单设置--修改、增加菜单资源
     */
    JsonResult updateMenuByType(MenuBean mb, long userId);
    /**
     * @Describe 菜单设置--删除菜单资源
     */
    JsonResult deleteMenuTree(String menuId);


    /**
     * @Describe 角色设置--角色查询
     */
    Page<UseRole> findUserRole(Page<UseRole> pageData);
    /**
     * @Describe 角色设置--角色修改
     */
    JsonResult updateUserRole(UseRole useRole);
    /**
     * @Describe 角色设置--角色添加
     */
    JsonResult addUserRole(UseRole useRole);
    /**
     * @Describe 角色设置--角色删除
     */
    JsonResult deleteUserRole(UseRole useRole);

    Page<UserRoleRelation> getUserByRole(Page<UserRoleRelation> pageData);

    JsonResult insertUserByRole(UserRoleRelation useRole, String userIdStr);

    JsonResult deleteUserRoleRelation(String idStr);

    Page<MenuRoleRelation> getMenuRoleRelation(Page<MenuRoleRelation> pageData);

    JsonResult insertMenuRoleRelation(MenuRoleRelation useRole);

    JsonResult deleteMenuRoleRelation(String idStr);

    List<Map<String, String>> getMenuName();
}
