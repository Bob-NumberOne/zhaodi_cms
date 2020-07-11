package com.zhaodi.custom.service.iml;
import com.zhaodi.bean.JsonResult;
import com.zhaodi.bean.Page;
import com.zhaodi.custom.dao.BasicUserDao;
import com.zhaodi.custom.entity.*;
import com.zhaodi.custom.service.BasicUserService;
import com.zhaodi.oa.dao.HrmResourceDao;
import com.zhaodi.oa.entity.AtreeMenu;
import com.zhaodi.oa.util.SystemMethod;
import com.zhaodi.util.comMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("UserServiceIml")
public class BasicUserServiceIml implements BasicUserService {
    @Resource(name="UserDao")
    private BasicUserDao userDao;

    @Resource(name="HrmResourceDao")
    private HrmResourceDao basicDao;

    @Override
    public List<User> getNames() {
        return userDao.getNames();
    }
    /**
     * @Describe 根据人员id查找对应的资源列表
     */
    @Override
    public List<String> findResourcesByName(long userId) {
        return userDao.findResourcesByName(userId);
    }
    /**
     * @Describe 根据人员id查找对应角色
     */
    @Override
    public List<Long> findRoleByName(long userId) {
        return userDao.findRoleByName(userId);
    }

    /**
     * @Describe 获取默认菜单资源
     */
    @Override
    public List<MenuHome> findMenuHome() {
        return userDao.findMenuHome();
    }

    /**
     * @Describe 菜单设置--查询菜单资源
     */
    public List<AtreeMenu> getMenuTree(Integer type) {
        //先根据角色查询角色-菜单关联表获取菜单ID集合，原始菜单数据集合
        List<AtreeMenu> menulist = userDao.getMenuTree();
        List<AtreeMenu> atreeMenus = new ArrayList<AtreeMenu>();
        for (AtreeMenu treeNode : menulist) {
            if (treeNode.getSuperId().equals("0")) {
                treeNode.setSpread(true);
                atreeMenus.add(SystemMethod.findChildren(treeNode, menulist));
            }
        }
        return atreeMenus;
    }
    /**
     * @Describe 菜单设置--根据ID查询菜单资源
     */
    @Override
    public MenuBean getMenuById(String menuId) {
        return userDao.getMenuById(Long.valueOf(menuId));
    }
    /**
     * @Describe 菜单设置--修改、增加菜单资源
     */
    @Override
    public JsonResult updateMenuByType(MenuBean mb, long userId) {
        //判断类型，根据不同的类型做不同的操作
        String type=mb.getMenuType();
        if (type == null || type == "") {
            return new JsonResult("0", "操作失败，系统不清楚操作类型！");
        } else if (type.equals("1")) {//更新
            //更新时间，更新人
            mb.setUpdater(userId);
            mb.setUpdateTime(comMethod.getDateTime1());
            Integer code = userDao.updateMenu(mb);
            return resultMsg(code, "更新");
        } else if (type.equals("2")) {//添加下级节点
            //创建时间，创建人
            mb.setCreaterId(userId);
            mb.setCreatTime(comMethod.getDateTime1());
            //添加下级节点，父ID为当前节点ID
            mb.setParentId(mb.getMenuId());
            Integer code = userDao.addMenu(mb);
            return resultMsg(code, "添加下级节点");
        } else if (type.equals("3")) {//添加同级节点
            //创建时间，创建人
            mb.setCreaterId(userId);
            mb.setCreatTime(comMethod.getDateTime1());
            //添加同级节点，父ID为当前节点的父级ID
            Integer code = userDao.addMenu(mb);
            return resultMsg(code, "添加同级节点");
        } else {
            return new JsonResult("0", "操作失败，系统不清楚操作类型！");
        }
    }
    private JsonResult resultMsg(Integer code,String msg){
        if(code==1){
            return new JsonResult("1",msg+"成功！");
        }else{
            return new JsonResult("0",msg+"失败！");
        }
    }
    /**
     * @Describe 菜单设置--删除菜单资源
     */
    @Override
    public JsonResult deleteMenuTree(String menuId) {
        //线判断该id下面是否有子节点，如果有是不能删除的
        Integer total=userDao.findSonNode(menuId);
        if(total==0){
            Integer code=userDao.deleteMenu(menuId);
            if(code==1){
                return new JsonResult("1","删除成功！");
            }else{
                return new JsonResult("0","删除失败！");
            }
        }else{
            return new JsonResult("0","当前节点下存在子节点，不能做删除操作！");
        }
    }

    /**
     * @Describe 角色设置--角色查询
     */
    @Override
    public Page<UseRole> findUserRole(Page<UseRole> pageData) {
        Page<UseRole> page = new Page<>();
        List<UseRole> partWords = userDao.findUserRole(pageData);
        page.setBeans(partWords);
        Integer num=userDao.findUserRoleTotal(pageData);
        page.setCounts(num);
        page.setStatus(200);
        return page;
    }
    /**
     * @Describe 角色设置--角色修改
     */
    @Override
    public JsonResult updateUserRole(UseRole useRole) {
        Integer code = userDao.updateUserRole(useRole);
        return resultMsg(code, "角色修改");
    }
    /**
     * @Describe 角色设置--角色添加
     */
    @Override
    public JsonResult addUserRole(UseRole useRole) {
        Integer code = userDao.addUserRole(useRole);
        return resultMsg(code, "角色添加");
    }
    /**
     * @Describe 角色设置--角色删除
     */
    @Override
    public JsonResult deleteUserRole(UseRole useRole) {
        long roleId=useRole.getRoleId();
        if(userDao.countRoleUsers(roleId)==0){
            Integer code = userDao.deleteUserRole(roleId);
            return resultMsg(code, "角色删除");
        }else if(roleId==1001){//系统管理员不能删除
            return new JsonResult("0","不能删除系统管理员！");
        }
        return new JsonResult("0","角色下存在人员不能做删除操作！");
    }



    /**
     * @Describe 角色人员====根据角色查对应人员
     * @author WangBo
     * @return
     */
    @Override
    public Page<UserRoleRelation> getUserByRole(Page<UserRoleRelation> pageData) {
        Page<UserRoleRelation> page = new Page<>();
        List<UserRoleRelation> partWords = userDao.getUserByRole(pageData);
        List<UserRoleRelation> list=new ArrayList<>();
        for (UserRoleRelation u:partWords){
            UserRoleRelation urr=new UserRoleRelation();
            if(u.getUserId()==1){
                urr.setUserName("系统管理员");
                urr.setId(u.getId());
                urr.setUserId(u.getUserId());
            }else{
                urr= basicDao.getUserInfoById(u.getUserId());
                urr.setId(u.getId());
            }
            list.add(urr);
        }
        page.setBeans(list);
        Integer num=userDao.countUserByRole(pageData.getBean().getRoleId());
        page.setCounts(num);
        page.setStatus(200);
        return page;
    }
    /**
     * @Describe 角色人员====插入角色用户关系
     * @author WangBo
     * @return
     */
    @Override
    @Transactional
    public JsonResult insertUserByRole(UserRoleRelation useRole, String userIdStr) {
        Integer num=0;
        try {
            String[] str=userIdStr.split(",");
            for (int i=0;i<str.length;i++){
                long userId=Long.parseLong(str[i]);
                useRole.setUserId(userId);
                Integer code = userDao.insertUserByRole(useRole);
                num+=code;
            }
            if(num==str.length){
                num=1;
            }
            return resultMsg(num,"角色人员添加");
        } catch (Exception e) {
            System.out.println(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return resultMsg(0,"角色人员添加");
        }
    }
    /**
     * @Describe 角色人员====删除角色用户关系
     * @author WangBo
     * @return
     */
    @Override
    @Transactional
    public JsonResult deleteUserRoleRelation(String idStr) {
        Integer num=0;
        try {
            String[] str=idStr.split(",");
            for (int i=0;i<str.length;i++){
                long userRoleId=Long.parseLong(str[i]);
                if(userRoleId==1001){//系统管理员不能删除
                    return new JsonResult("0","不能删除系统管理员！");
                }
                Integer code = userDao.deleteUserRoleRelation(userRoleId);
                num+=code;
            }
            if(num==str.length){
                num=1;
            }
            return resultMsg(num,"角色人员删除");
        } catch (Exception e) {
            System.out.println(e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return resultMsg(0,"角色人员删除");
        }
    }
    /**
     * @Describe 角色菜单====查角色菜单
     * @author WangBo
     * @return
     */
    @Override
    public Page<MenuRoleRelation> getMenuRoleRelation(Page<MenuRoleRelation> pageData) {
        Page<MenuRoleRelation> page = new Page<>();
        List<MenuRoleRelation> partWords = userDao.getMenuRoleRelation(pageData);
        page.setBeans(partWords);
        Integer num=userDao.countMenuRoleRelation(pageData.getBean().getRoleId());
        page.setCounts(num);
        page.setStatus(200);
        return page;
    }
    /**
     * @Describe 角色菜单====插入角色菜单关系
     * @author WangBo
     * @return
     */
    @Override
    public JsonResult insertMenuRoleRelation(MenuRoleRelation useRole) {
        Integer code = userDao.insertMenuRoleRelation(useRole);
        return resultMsg(code, "角色权限功能添加");
    }
    /**
     * @Describe 角色人员====删除角色用户关系
     * @author WangBo
     * @return
     */
    @Override
    @Transactional
    public JsonResult deleteMenuRoleRelation(String idStr) {
        Integer num=0;
        try {
            String[] str=idStr.split(",");
            for (int i=0;i<str.length;i++){
                long menuRoleId=Long.parseLong(str[i]);
                if(menuRoleId==1001){//系统管理员不能删除
                    return new JsonResult("0","不能删除系统管理员权限！");
                }
                Integer code = userDao.deleteMenuRoleRelation(menuRoleId);
                num+=code;
            }
            if(num==str.length){
                num=1;
            }
            return resultMsg(num,"角色权限功能删除");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return resultMsg(0,"角色权限功能删除");
        }

    }

    @Override
    public List<Map<String, String>> getMenuName() {
        return userDao.getMenuName();
    }


    @Override
    public List<MenuTree> findAuthMenu(long userId) {
        //先根据角色查询角色-菜单关联表获取菜单ID集合，原始菜单数据集合
        Integer flag=userDao.authTop(userId);
        List<MenuTree> menulist =new ArrayList<>();
        if(flag>=1){//如果是管理员
            menulist = userDao.findAuthMenu1();
        }else{//如果不是管理员
            menulist = userDao.findAuthMenu(userId);
        }

        List<MenuTree> atreeMenus = new ArrayList<MenuTree>();
        for (MenuTree treeNode : menulist) {
            if (treeNode.getSuperId().equals("0")) {
                atreeMenus.add(findChildren(treeNode, menulist));
            }
        }
        return atreeMenus;
    }

    /**
     * 递归查找子节点
     * @param menu
     * @param menulist
     * @return
     **/
    public static MenuTree findChildren(MenuTree menu, List<MenuTree> menulist) {
        for (MenuTree item : menulist) {
            if (menu.getId().equals(item.getSuperId())) {
                if (menu.getChild() == null) {
                    menu.setChild(new ArrayList<MenuTree>());
                }
                menu.getChild().add(findChildren(item, menulist));
            }
        }
        return menu;
    }
}
