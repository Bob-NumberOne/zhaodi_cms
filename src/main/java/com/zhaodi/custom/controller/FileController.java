package com.zhaodi.custom.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhaodi.custom.dao.PatentDao;
import com.zhaodi.custom.entity.InnovationJournal;
import com.zhaodi.custom.entity.SysCatalog;
import com.zhaodi.custom.service.fileService;
import com.zhaodi.util.fileUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @author WangBo
 * @version 1.0
 * @Describe 文件相关控制层
 * @date 2020/4/25 11:57
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Resource(name="fileService")
    private fileService fs;

    /**
     * @Describe 获取图片信息
     * @author WangBo
     */
    @RequestMapping(value="/queryPic.xmj")
    public String queryPic(InnovationJournal ino){
        return fs.getPicData(ino).toString();
    }
    /**
     * 稿源周报excel表格下载
     * @return
     */

    @RequestMapping(value = "/downExcel.xmj")
    public void downExcel(HttpServletResponse response){
        SysCatalog sc=fs.getPath(1002);//默认给专利模块id
        String uploadSubDir=sc.getModeUrl();
        String uploadRoot=sc.getServerUrl();
        String filName="创新刊物-导入模板.xlsx";
        String path=uploadRoot+uploadSubDir+"/"+filName;
        HttpServletResponse dd=fileUtil.download(path,filName,response);
    }

}
