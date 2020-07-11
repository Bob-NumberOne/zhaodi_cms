package com.zhaodi.custom.dao;

import com.zhaodi.bean.Page;
import com.zhaodi.custom.entity.*;
import com.zhaodi.oa.entity.AtreeMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository("UserDao")
public interface BasicUserDao {
    List<User> getNames();
    /**
     * @Describe 根据人员id查找对应的资源列表
     */
    List<String> findResourcesByName(@Param("userId") long userId);
    /**
     * @Describe 根据人员id查找对应角色
     */
    List<Long> findRoleByName(@Param("userId") long userId);

    /*----------------------------------菜单设置----------------------------------/

    /**
     * @Describe 根据人员id查找对应菜单表
     */
    List<MenuBean> findMenuByParent(@Param("parentId") long parentId);
    /**
     * @Describe 获取默认菜单资源
     */
    List<MenuHome> findMenuHome();
    /**
     * @Describe 获取菜单资源
     */
    List<MenuTree> findAuthMenu(@Param("userId")long userId);
    List<MenuTree> findAuthMenu1();
    Integer authTop(@Param("userId")long userId);
    /**
     * @Describe 菜单设置--查询菜单资源
     */
    List<AtreeMenu> getMenuTree();
    /**
     * @Describe 菜单设置--根据ID查询菜单资源
     */
    MenuBean getMenuById(Long menuId);
    /**
     * @Describe 菜单设置--删除菜单资源
     */
    Integer deleteMenu(@Param("menuId") String menuId);
    /**
     * @Describe 菜单设置--修改菜单资源
     */
    Integer updateMenu(MenuBean menu);
    /**
     * @Describe 菜单设置--新增菜单资源
     */
    Integer addMenu(@Param("menu")MenuBean menu);
    /**
     * @Describe 菜单设置--查询是否有字节点
     */
    Integer findSonNode(String menuId);


    /*----------------------------------角色设置----------------------------------*/

    /**
     * @Describe 角色设置====角色查询
     */
    List<UseRole> findUserRole(@Param("page") Page<UseRole> page);
    /**
     * @Describe 角色设置====角色查询条数
     */
    Integer findUserRoleTotal(@Param("page") Page<UseRole> page);
    /**
     * @Describe 角色设置====角色修改
     */
    Integer updateUserRole(UseRole role);
    /**
     * @Describe 角色设置====角色添加
     */
    Integer addUserRole(UseRole role);
    /**
     * @Describe 角色设置====角色删除
     */
    Integer deleteUserRole(@Param("roleId") long roleId);
    /**
     * @Describe 角色设置====统计角色人员数量
     */
    Integer countRoleUsers(@Param("roleId") long roleId);


    /*----------------------------------角色设置==角色人员----------------------------------*/

    /**
     * @Describe 角色人员====根据角色查对应人员
     */
    List<UserRoleRelation> getUserByRole(@Param("page") Page<UserRoleRelation> page);
    /**
     * @Describe 角色人员====根据角色查对应人员条数
     */
    Integer countUserByRole(@Param("roleId") long roleId);
    /**
     * @Describe 角色人员====插入角色用户关系
     */
    Integer insertUserByRole(UserRoleRelation userRole);
    /**
     * @Describe 角色人员====删除角色用户关系
     */
    Integer deleteUserRoleRelation(@Param("userRoleId") long userRoleId);


    /*----------------------------------角色设置==角色菜单----------------------------------*/

    /**
     * @Describe 角色菜单====查角色菜单
     */
    List<MenuRoleRelation> getMenuRoleRelation(@Param("page") Page<MenuRoleRelation> page);
    /**
     * @Describe 角色菜单====查角色菜单条数
     */
    Integer countMenuRoleRelation(@Param("roleId") long roleId);
    /**
     * @Describe 角色菜单====插入角色菜单关系
     */
    Integer insertMenuRoleRelation(MenuRoleRelation menuRole);
    /**
     * @Describe 角色菜单====删除角色菜单关系
     */
    Integer deleteMenuRoleRelation(@Param("menuRoleId") long menuRoleId);
    /**
     * @Describe 查询菜单姓名和ID
     */
    List<Map<String, String>> getMenuName();


}
