package com.zhaodi.oa.controller;
import com.zhaodi.oa.entity.AtreeMenu;
import com.zhaodi.oa.service.PatentClassService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author WangBo
 * @version 1.0
 * @date 2020/3/31 10:21
 */
@RestController
@RequestMapping("/OA")
public class PatentClassController {
    @Resource(name = "PatentClassService")
    private PatentClassService pcs;

    @RequestMapping("/getAtreeMenu.xmj")
    public List<AtreeMenu> getAtreeMenu(){
        return pcs.getAtreeMenu(2);
    }



}
