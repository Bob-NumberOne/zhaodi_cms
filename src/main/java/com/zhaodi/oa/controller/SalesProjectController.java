package com.zhaodi.oa.controller;

import com.zhaodi.bean.JsonResult;
import com.zhaodi.bean.Page;
import com.zhaodi.oa.entity.OATable;
import com.zhaodi.oa.entity.OATableFields;
import com.zhaodi.oa.entity.WorkAttendance;
import com.zhaodi.oa.service.SalesProjectService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @author WangBo
 * @version 1.0
 * @Describe 描述
 * @date 2020/6/12 11:19
 */
@RestController
@RequestMapping("/project")
public class SalesProjectController {
    @Resource(name="SalesProjectService")
    private SalesProjectService tableService;


    /**
     * @Describe 获取市场部下面的销售人员
     * @author WangBo
     * @param 分页参数
     */
    @CrossOrigin
    @RequestMapping("/getorkAttendance.xmj")
    public Page<WorkAttendance> getorkAttendance(String page, String limit,WorkAttendance wa) throws ParseException {
        Page<WorkAttendance> pageData = new Page<>();
        pageData.setPageNum(page);
        pageData.setPageSize(limit);
        //wa.setLastName("檀方兵");
        pageData.setBean(wa);
        Page<WorkAttendance> tableData = tableService.getWorkAttendance(pageData);
        return tableData;
    }
    /**
     * @Describe 获取人员岗位
     * @author WangBo
     */
    @RequestMapping("/getJobTitle.xmj")
    public JsonResult<List<Map<String, String>>> getJobTitle() {
        List<Map<String, String>> ms=tableService.getJobTitle();
        return new JsonResult(ms);
    }

    /**
     * @Describe 获取表数据
     * @author WangBo
     * @param 分页参数
     */
    @RequestMapping("/getTableList.xmj")
    public Page<OATable> getTableList(String page, String limit,String message) {
        Page<OATable> pageData = new Page<>();
        pageData.setPageNum(page);
        pageData.setPageSize(limit);
        if(message==null){
            pageData.setMessage("");
        }else{
            pageData.setMessage(message.trim());
        }
        Page<OATable> tableData = tableService.getOATable(pageData);
        return tableData;
    }
    /**
     * @Describe 根据表ID查询对应表详细信息
     * @author WangBo
     * @param 表ID
     */
    @RequestMapping("/getOATableFields.xmj")
    public List<OATableFields> getOATableFields( String billId) {
        return tableService.getOATableFields(billId);
    }
    /**
     * @Describe 插入系统表相关信息
     * @author WangBo
     */
/*    @RequestMapping("/insertTableFields.xmj")
    public void insertTableFields() throws IOException {
        readfile("C:\\Users\\jack\\Desktop\\OA开发\\表结构完整\\ecology80表结构文档\\ecology80");
    }*/
    public boolean readfile(String filepath) throws FileNotFoundException, IOException {
        try {
            File file = new File(filepath);
            if (!file.isDirectory()) {
                System.out.println("--------------------------------------------------------------------");
                System.out.println("文件");
                System.out.println("path=" + file.getPath());
                System.out.println("absolutepath=" + file.getAbsolutePath());
                System.out.println("name=" + file.getName());
                System.out.println("--------------------------------------------------------------------");

            } else if (file.isDirectory()) {
                System.out.println("文件夹");
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "\\" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        String path=readfile.getPath().replaceAll("C:\\\\Users\\\\jack\\\\Desktop\\\\OA开发\\\\表结构完整\\\\ecology80表结构文档\\\\","");
                        System.out.println("path=" + path);
                        // System.out.println("absolutepath="
                        //        + readfile.getAbsolutePath());
                        String[] name=readfile.getName().split("\\(");
                        if(name.length>1){
                            System.out.println("name1=" + name[0]);
                            System.out.println("name2=" + name[1].split("\\)")[0]);
                            tableService.insertTableFields(name[0],name[1].split("\\)")[0],path);
                        }else{
                            System.out.println("name1=" + name[0]);
                        }


                    } else if (readfile.isDirectory()) {
                        readfile(filepath + "\\" + filelist[i]);
                    }
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println("readfile()   Exception:" + e.getMessage());
        }
        return true;
    }

}
