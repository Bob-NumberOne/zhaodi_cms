package com.zhaodi.custom.controller;
import com.alibaba.fastjson.JSONObject;
import com.zhaodi.bean.JsonResult;
import com.zhaodi.bean.Page;
import com.zhaodi.config.authentication.MyUserDetails;
import com.zhaodi.custom.entity.InnovationJournal;
import com.zhaodi.custom.entity.PatentFilterWord;
import com.zhaodi.custom.entity.SearchWords;
import com.zhaodi.custom.entity.UfPatentdataWord;
import com.zhaodi.custom.service.PatentService;
import com.alibaba.fastjson.JSONArray;
import com.zhaodi.util.comMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.util.*;

/**
 * @author WangBo
 * @version 1.0
 * @date 2020/3/31 10:21
 */
@RestController
@RequestMapping("/patent")
public class PatentController {

    @Resource(name="PatentService")
    private PatentService patentService;

/*    private  final Logger log = LoggerFactory.getLogger(PatentController.class);*/
    /**
     * @Describe 企业新增专利概述
     * @author WangBo
     * @return
     */
    @RequestMapping("/getPatentAdded.xmj")
    public String getPatentAdded(Integer top,String companyName,String block){
        JSONArray datas= patentService.getPatentAdded(top,companyName,block);
/*        System.out.println(datas);*/
        return datas.toString();
    }
    /**
     * @Describe 根据条件获取专利申请人
     * @author WangBo
     * @return
     */
    @RequestMapping("/getCompanyNames.xmj")
    public List<Map<String, String>> getCompanyNames(){
        return patentService.getCompanyNames();
    }
    /**
     * @Describe 获取词性和数据列
     * @author WangBo
     * @return
     */
    @RequestMapping("/getWordsAndColName.xmj")
    public String getWordsAndColName(){
        return patentService.getWordsAndColName();
    }

    /**
     * @Describe 获取气泡数据及条数
     */
    @RequestMapping("/getEffectBubble.xmj")
    public String getEffectBubble(String block){
        List<Map<String, Object>> datas=patentService.getEffectBubble(block);
        //System.out.println(datas.toString());
        List<String> list1=new ArrayList<>();
        List<String> list2=new ArrayList<>();
        List<Map<String,String>> list3=new ArrayList<Map<String,String>>();
        Set set1 = new  HashSet();
        Set set2 = new  HashSet();
        for (Map<String, Object> map:datas){
            Map<String,String> m=new HashMap<>();
            String jg=(String) map.get("jg");
            String xg=(String) map.get("xg");
            String num=map.get("num").toString();
            m.put("jg",jg);
            m.put("xg",xg);
            m.put("num",num);
            list3.add(m);
            if(set1.add(jg)){
                list1.add(jg);
            }
            if(set2.add(xg)){
                list2.add(xg);
            }
        }
        JSONArray jsonArray = new JSONArray() ;
        jsonArray.add(list1);
        jsonArray.add(list2);
        jsonArray.add(list3);
        return jsonArray.toString();
    }
    /**
     * @Describe 获取分词
     */
    @RequestMapping("/getParticiple.xmj")
    public String getParticiple(String total){
        //只关注这些词性的词
        Set<String> expectedNature = new HashSet<String>() {{
            add("n");add("v");add("vd");add("vn");add("vf");
            add("vx");add("vi");add("vl");add("vg");
            add("nt");add("nz");add("nw");add("nl");
            add("ng");add("userDefine");add("wh");
        }};
        Map<String,String> m=new HashMap<>();
        m.put("top","0");
        if(total==null || total==""){
            m.put("total","1000");
        }else{
            m.put("total",total);
        }
        List<Map<String, String>> list=patentService.getParticiple(expectedNature,m);
        JSONArray jsonArray = new JSONArray() ;
        jsonArray.add(list);
        return jsonArray.toString();
    }
    /**
     * @Describe 获取雷达图
     */
    @RequestMapping("/getDirClass.xmj")
    public String getDirClass(@RequestBody JSONObject sendData){
        return patentService.getDirClass(sendData).toString();
    }
    /**
     * @Describe 存储专利对应数据
     */
    @RequestMapping("/savePatentData.xmj")
    public JsonResult savePatentData(MultipartFile file) throws Exception {
        MyUserDetails principal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return patentService.getExcel(file,principal);
    }
    /**
     * @Describe 查询专利对应数据
     */
    @RequestMapping("/getPatentData.xmj")
    public Page<InnovationJournal> getPatentData(String page,String limit,InnovationJournal inn,String companyName,String yearRange) throws Exception {
        Page<InnovationJournal> pageData = new Page<>();
        pageData.setBean(inn);
        pageData.setPageNum(page);
        pageData.setPageSize(limit);
        pageData = patentService.getPatentData(pageData,companyName,yearRange);
        return pageData;
    }
    /**
     * @Describe 企业年份月份折线图分析
     * @author WangBo
     * @return
     */
    @RequestMapping("/getPatentLine.xmj")
    public String getPatentLine(SearchWords sw){
        JSONArray datas= patentService.getPatentLine(sw);
        return datas.toString();
    }
    /**
     * @Describe 获取分词关键词数据
     * @author WangBo
     */
    @RequestMapping("/findPartWords.xmj")
    public Page<UfPatentdataWord> findPartWords(String page,String limit,UfPatentdataWord ufp) {
        Page<UfPatentdataWord> pageData = new Page<>();
        pageData.setBean(ufp);
        pageData.setPageNum(page);
        pageData.setPageSize(limit);
        pageData = patentService.findPartWords(pageData);
        return pageData;
    }

    /**
     * @Describe 删除关键词数据
     * @author WangBo
     */
    @RequestMapping("/deletePartWordsT.xmj")
    public JsonResult deletePartWordsT(String idStr) {
        Integer status=patentService.deletePartWordsT(idStr);
        if(status==0){
            return new JsonResult("1","删除失败！");
        }
        return new JsonResult("1","成功删除"+status+"条数据！");
    }

    /**
     * @Describe 插入关键词数据
     * @author WangBo
     */
    @RequestMapping("/insertPartWords.xmj")
    public JsonResult insertPartWords(UfPatentdataWord record) {
        MyUserDetails principal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        /*log.info(principal.getUserId()+"==============");*/
        record.setCreater(principal.getUserId());
        record.setCreattime(comMethod.getDateTime1());
        record.setStatus((byte) 1);
        Integer status=patentService.insertPartWords(record);
        if(status!=1){
            return new JsonResult("1","插入失败！");
        }
        return new JsonResult("1","成功插入"+status+"条数据！");
    }

    /**
     * @Describe 修改分词关键词数据
     * @author WangBo
     */
    @RequestMapping("/updatePartWords.xmj")
    public JsonResult updatePartWords(UfPatentdataWord record) {
        MyUserDetails principal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        record.setUpdater(principal.getUserId());
        record.setUpdatetime(comMethod.getDateTime1());

        Integer status=patentService.updatePartWords(record);
        if(status!=1){
            return new JsonResult("1","修改失败！");
        }
        return new JsonResult("1","成功修改"+status+"条数据！");
    }

    /**
     * @Describe 获取分词过滤词数据
     * @author WangBo
     */
    @RequestMapping("/findFilterWords.xmj")
    public Page<PatentFilterWord> findFilterWords(String page, String limit, PatentFilterWord ufp) {
        Page<PatentFilterWord> pageData = new Page<>();
        pageData.setBean(ufp);
        pageData.setPageNum(page);
        pageData.setPageSize(limit);
        pageData = patentService.findFilterWords(pageData);
        return pageData;
    }

    /**
     * @Describe 删除过滤词数据
     * @author WangBo
     */
    @RequestMapping("/deleteFilterWordsF.xmj")
    public JsonResult deleteFilterWordsF(String idStr) {
        Integer status=patentService.deleteFilterWordsF(idStr);
        if(status==0){
            return new JsonResult("1","删除失败！");
        }
        return new JsonResult("1","成功删除"+status+"条数据！");
    }

    /**
     * @Describe 插入过滤词数据
     * @author WangBo
     */
    @RequestMapping("/insertFilterWords.xmj")
    public JsonResult insertFilterWords(PatentFilterWord record) {
/*        MyUserDetails principal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        *//*log.info(principal.getUserId()+"==============");*//*
        record.setCreater(principal.getUserId());
        record.setCreattime(comMethod.getDateTime1());*/
        record.setStatus(1);
        Integer status=patentService.insertFilterWords(record);
        if(status!=1){
            return new JsonResult("1","插入失败！");
        }
        return new JsonResult("1","成功插入"+status+"条数据！");
    }

    /**
     * @Describe 修改分词过滤词数据
     * @author WangBo
     */
    @RequestMapping("/updateFilterWords.xmj")
    public JsonResult updateFilterWords(PatentFilterWord record) {
/*        MyUserDetails principal = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        record.setUpdater(principal.getUserId());
        record.setUpdatetime(comMethod.getDateTime1());*/

        Integer status=patentService.updateFilterWords(record);
        if(status!=1){
            return new JsonResult("1","修改失败！");
        }
        return new JsonResult("1","成功修改"+status+"条数据！");
    }
    /**
     * @Describe 根据参数获取分词
     * @author WangBo
     */
    @RequestMapping("/WordCloudAnalysis.xmj")
    public String WordCloudAnalysis(SearchWords sw) throws Exception {
        String wordPart=sw.getWordPart();
        String total=sw.getNumber();
        String[] wordParts=null;
        //只关注这些词性的词
        Set<String> expectedNature = new HashSet<String>();
        expectedNature.add("n");
        if(wordPart!=null && wordPart!=""){
            wordParts=wordPart.split(",");
            for (int i=0;i<wordParts.length;i++){
                expectedNature.add(wordParts[i]);
            }
        }else{
            expectedNature.add("v");expectedNature.add("vd");expectedNature.add("vn");expectedNature.add("vf");
            expectedNature.add("vx");expectedNature.add("vi");expectedNature.add("vl");expectedNature.add("vg");
            expectedNature.add("nt");expectedNature.add("nz");expectedNature.add("nw");expectedNature.add("nl");
            expectedNature.add("ng");expectedNature.add("userDefine");expectedNature.add("wh");
        }

        Map<String,String> m=new HashMap<>();
        m.put("top","0");
        if(total==null || total==""){
            m.put("total","1000");
        }else{
            m.put("total",total);
        }
        List<Map<String, String>> list=patentService.WordCloudAnalysis(expectedNature,m,sw);
        JSONArray jsonArray = new JSONArray() ;
        jsonArray.add(list);
        return jsonArray.toString();
    }

}
