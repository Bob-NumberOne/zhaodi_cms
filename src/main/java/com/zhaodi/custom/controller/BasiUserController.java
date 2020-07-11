package com.zhaodi.custom.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhaodi.bean.JsonResult;
import com.zhaodi.bean.Page;
import com.zhaodi.config.authentication.MyUserDetails;
import com.zhaodi.custom.entity.*;
import com.zhaodi.custom.service.BasicUserService;
import com.zhaodi.oa.entity.AtreeMenu;
import com.zhaodi.util.comMethod;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/basic")
public class BasiUserController {
    @Resource(name="UserServiceIml")
    private BasicUserService userService;
    @RequestMapping("/getCustomer")
    public String getCustomer(){
        List<User> hh= userService.getNames();
        for(User user:hh){
            System.out.println(user.getLoginName());
        }
        return "你真棒！";
    }
    /**
     * @Describe 首页获取菜单树
     * @author WangBo
     * @return
     */
    @RequestMapping("/getTree.xmj")
    public String getTree(HttpSession session){
        SecurityContextImpl sci=(SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        MyUserDetails principal = (MyUserDetails)sci.getAuthentication().getPrincipal();
        long userId=principal.getUserId();

        JSONObject jSONObject = new JSONObject();
        List<MenuHome> menuHomes=userService.findMenuHome();
        for (MenuHome mh:menuHomes){
            Map<String,String> m=new HashMap<>();
            if(mh.getIsMenu()==0){//不是菜单
                m.put("title",mh.getTitle());
                m.put("image",mh.getImage());
                m.put("href",mh.getHref());
                jSONObject.put(mh.getInfoName(),m);
            }else if(mh.getIsMenu()==1){//是菜单
                List<MenuTree> list=userService.findAuthMenu(userId);
                jSONObject.put(mh.getInfoName(),list);
            }
        }
        return jSONObject.toString();
    }
    /**
     * @Describe 菜单设置--查询菜单资源
     * @author WangBo
     * @return
     */
    @RequestMapping("/getMenuTree.xmj")
    public List<AtreeMenu> getMenuTree(){
        return userService.getMenuTree(3);
    }
    /**
     * @Describe 菜单设置--根据ID查询菜单资源
     * @author WangBo
     * @return
     */
    @RequestMapping("/getMenuById.xmj")
    public MenuBean getMenuById(String menuId){
        return userService.getMenuById(menuId);
    }
    /**
     * @Describe 菜单设置--修改、增加菜单资源
     * @author WangBo
     * @return
     */
    @RequestMapping("/updateMenuByType.xmj")
    public JsonResult updateMenuByType(MenuBean mb){
        MyUserDetails principal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        mb.setCreater(principal.getLastName());
        mb.setTarget("_self");
        return userService.updateMenuByType(mb,principal.getUserId());
    }
    /**
     * @Describe 菜单设置--删除菜单资源
     * @author WangBo
     * @return
     */
    @RequestMapping("/deleteMenuTree.xmj")
    public JsonResult deleteMenuTree(String menuId){
        return userService.deleteMenuTree(menuId);
    }

    /**
     * @Describe 角色设置====角色查询
     * @author WangBo
     * @return
     */
    @RequestMapping("/findUserRole.xmj")
    public Page<UseRole> findUserRole(String page,String limit,UseRole useRole){
        Page<UseRole> pageData = new Page<>();
        pageData.setBean(useRole);
        pageData.setPageNum(page);
        pageData.setPageSize(limit);
        pageData = userService.findUserRole(pageData);
        return pageData;
    }
    /**
     * @Describe 菜单设置--角色修改
     * @author WangBo
     * @return
     */
    @RequestMapping("/updateUserRole.xmj")
    public JsonResult updateUserRole(UseRole useRole){
        MyUserDetails principal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        useRole.setUpdateTime(comMethod.getDateTime1());
        useRole.setUpdater(principal.getUserId());
        return userService.updateUserRole(useRole);
    }
    /**
     * @Describe 菜单设置--角色添加
     * @author WangBo
     * @return
     */
    @RequestMapping("/addUserRole.xmj")
    public JsonResult addUserRole(UseRole useRole){
        MyUserDetails principal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        useRole.setCreatName(principal.getLastName());
        useRole.setCreater(principal.getUserId());
        useRole.setCreateTime(comMethod.getDateTime1());
        return userService.addUserRole(useRole);
    }
    /**
     * @Describe 菜单设置--角色删除
     * @author WangBo
     * @return
     */
    @RequestMapping("/deleteUserRole.xmj")
    public JsonResult deleteUserRole(UseRole useRole){
        MyUserDetails principal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Object[] arr=principal.getAuthorities().toArray();
        boolean flag=false;
        for (int i = 0; i < arr.length; i++) {
            SimpleGrantedAuthority role=(SimpleGrantedAuthority) arr[i];
            //System.out.println(role.getAuthority());
            if(role.getAuthority().equals("ROLE_1001")){//系统管理员
                flag=true;
            }
        }

        if(flag){//固定管理员可以删除
            return userService.deleteUserRole(useRole);
        }
        return new JsonResult("0","无权限操作！");
    }


    /**
     * @Describe 角色人员====根据角色查对应人员
     * @author WangBo
     * @return
     */
    @RequestMapping("/getUserByRole.xmj")
    public Page<UserRoleRelation> getUserByRole(String page,String limit,UserRoleRelation useRole){
        Page<UserRoleRelation> pageData = new Page<>();
        pageData.setBean(useRole);
        pageData.setPageNum(page);
        pageData.setPageSize(limit);
        pageData = userService.getUserByRole(pageData);
        return pageData;
    }
    /**
     * @Describe 角色人员====插入角色用户关系
     * @author WangBo
     * @return
     */
    @RequestMapping("/insertUserByRole.xmj")
    public JsonResult insertUserByRole(String userIdStr,long roleId){
        UserRoleRelation useRole=new UserRoleRelation();
        MyUserDetails principal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        useRole.setCreatName(principal.getLastName());
        useRole.setCreater(principal.getUserId());
        useRole.setCreatTime(comMethod.getDateTime1());
        useRole.setRoleId(roleId);
        return userService.insertUserByRole(useRole,userIdStr);
    }

    /**
     * @Describe 角色人员====删除角色用户关系
     * @author WangBo
     * @return
     */
    @RequestMapping("/deleteUserRoleRelation.xmj")
    public JsonResult deleteUserRoleRelation(String idStr){
        return userService.deleteUserRoleRelation(idStr);
    }



    /**
     * @Describe 角色菜单====查角色菜单
     * @author WangBo
     * @return
     */
    @RequestMapping("/getMenuRoleRelation.xmj")
    public Page<MenuRoleRelation> getMenuRoleRelation(String page,String limit,MenuRoleRelation useRole){
        Page<MenuRoleRelation> pageData = new Page<>();
        pageData.setBean(useRole);
        pageData.setPageNum(page);
        pageData.setPageSize(limit);
        pageData = userService.getMenuRoleRelation(pageData);
        return pageData;
    }
    /**
     * @Describe 角色菜单====插入角色菜单关系
     * @author WangBo
     * @return
     */
    @RequestMapping("/insertMenuRoleRelation.xmj")
    public JsonResult insertMenuRoleRelation(MenuRoleRelation useRole){
        MyUserDetails principal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        useRole.setCreater(principal.getUserId());
        useRole.setCreatTime(comMethod.getDateTime1());
        return userService.insertMenuRoleRelation(useRole);
    }

    /**
     * @Describe 角色菜单====删除角色用户关系
     * @author WangBo
     * @return
     */
    @RequestMapping("/deleteMenuRoleRelation.xmj")
    public JsonResult deleteMenuRoleRelation(String idStr){
        return userService.deleteMenuRoleRelation(idStr);
    }
    /**
     * @Describe 获取菜单浏览框信息
     * @author WangBo
     * @param
     */
    @RequestMapping("/getMenuName.do")
    public JsonResult<List<Map<String, String>>> getMenuName() {
        List<Map<String, String>> ms=userService.getMenuName();
        return new JsonResult(ms);
    }
}
