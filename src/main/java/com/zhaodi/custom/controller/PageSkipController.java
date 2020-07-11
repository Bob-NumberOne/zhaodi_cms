package com.zhaodi.custom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author WangBo
 * @version 1.0
 * @Describe 页面跳转配置
 * @date 2020/3/31 15:02
 */
@Controller
@RequestMapping("/page")
public class PageSkipController {
    /**
     * @Describe 进入登录页面
     */
    @RequestMapping("/login")
    public String login(){
        return "layuiAdmin/page/login.html";
    }

    /**
     * @Describe 进入首页图形化页面
     */
    @RequestMapping("/toWelcome.xmj")
    public String toWelcome(){
        return "page/welcome.html";
    }

    /**
     * @Describe 进入分词页面
     */
    @RequestMapping("/toArticiple.xmj")
    public String toArticiple(){
        return "patent/wordcloud.html";
    }

    /**
     * @Describe 进入企业专利按月新增概述
     */
    @RequestMapping("/toPatentAdded.xmj")
    public String toPatentAdded(){
        return "patent/PatentAdded.html";
    }
    /**
     * @Describe 进入企业专利按月新增概述
     */
    @RequestMapping("/toEffectBubble.xmj")
    public String toEffectBubble(){
        return "patent/effect.html";
    }
    /**
     * @Describe 进入企业方向雷达图
     */
    @RequestMapping("/toTechDirection.xmj")
    public String toTechDirection(){
        return "patent/techDirection.html";
    }
    /**
     * @Describe 进入导入专利数据页面
     */
    @RequestMapping("/toPatentUpload.xmj")
    public String toPatentUpload(){
        return "patent/upload.html";
    }
    /**
     * @Describe 进入专利数据页面
     */
    @RequestMapping("/toPatentTable.xmj")
    public String toPatentTable(){
        return "patent/patentTable.html";
    }
    /**
     * @Describe 进入专利折线图数据页面
     */
    @RequestMapping("/toPatentBrokenLine.xmj")
    public String toPatentBrokenLine(){
        return "patent/patentBrokenLine.html";
    }

    /**
     * @Describe 进入专利折线图数据页面
     */
    @RequestMapping("/toPatentCharacteristic.xmj")
    public String toPatentCharacteristic(){
        return "patent/patentCharacteristic.html";
    }

    /**
     * @Describe 查询OA表
     */
    @RequestMapping("/toOATable.do")
    public String toOATable(){
        return "project/oaTable.html";
    }
    /**
     * @Describe 考勤查询
     */
    @RequestMapping("/toWorkAttendance.do")
    public String toWorkAttendance(){
        return "project/workAttendance.html";
    }
    /**
     * @Describe 进入菜单设置页面
     */
    @RequestMapping("/tomenuSetting.xmj")
    public String tomenuSetting(){
        return "systemSetting/menuSetting.html";
    }
    /**
     * @Describe 角色设置页面
     */
    @RequestMapping("/toUserRole.xmj")
    public String toUserRole(){
        return "systemSetting/userRole.html";
    }

}
