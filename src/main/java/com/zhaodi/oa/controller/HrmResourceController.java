package com.zhaodi.oa.controller;

import com.zhaodi.bean.JsonResult;
import com.zhaodi.oa.service.HrmService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author WangBo
 * @version 1.0
 * @date 2020/3/31 10:21
 */
@RestController
@RequestMapping("/hrm")
public class HrmResourceController{
    @Resource(name="HrmService")
    private HrmService hrmService;

    /*@RequestMapping("/login")
    public String getTest(){
        return "index";
    }*/
    /**
     * @Describe 获取人员信息浏览框信息
     * @author WangBo
     * @param
     */
    @RequestMapping("/getHrmResourcerowse.do")
    public JsonResult<List<Map<String, String>>> getHrmResourcerowse() {
        List<Map<String, String>> ms=hrmService.getHrmResourcerowse();
        return new JsonResult(ms);
    }
}
