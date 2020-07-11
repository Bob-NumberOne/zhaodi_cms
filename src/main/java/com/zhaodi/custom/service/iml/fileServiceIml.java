package com.zhaodi.custom.service.iml;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhaodi.custom.dao.PatentDao;
import com.zhaodi.custom.entity.InnovationJournal;
import com.zhaodi.custom.entity.SysCatalog;
import com.zhaodi.custom.service.fileService;
import com.zhaodi.util.fileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangBo
 * @version 1.0
 * @Describe 描述
 * @date 2020/4/25 16:23
 */
@Service("fileService")
public class fileServiceIml implements fileService {
    @Resource(name="PatentDao")
    private PatentDao patentDao;
    /**
     * @Describe 获取轮播图片
     * @author WangBo
     */
    public JSONObject getPicData(InnovationJournal ino) {
        JSONObject json=new JSONObject();
        List<InnovationJournal> list=patentDao.getPicData();
        SysCatalog sc=patentDao.getFileUrl(1001);//默认给专利模块id
        String URL=sc.getServerUrl();
        List<Map<String,String>> m1=new ArrayList<>();
        List<Map<String,String>> m2=new ArrayList<>();
        List<Map<String,String>> m3=new ArrayList<>();
        for (InnovationJournal inso:list){
            Map<String,String> m=new HashMap<>();
            m.put("url",inso.getGkh().split("\"")[1]);
            m.put("img_url","data:image/*;base64," + fileUtil.fileToBase64(URL+inso.getImg_url()));
            m.put("zy",inso.getZy());
            if(inso.getBz().equals("特色专利")){
                m1.add(m);
            }else if(inso.getBz().equals("新技术")){
                m2.add(m);
            }else if(inso.getBz().equals("风险专利")){
                m3.add(m);
            }
        }
        json.put("m1",m1);
        json.put("m2",m2);
        json.put("m3",m3);
        return json;
    }

    @Override
    public SysCatalog getPath(int id) {
        return patentDao.getFileUrl(id);
    }
}
