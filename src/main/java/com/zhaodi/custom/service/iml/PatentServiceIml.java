package com.zhaodi.custom.service.iml;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhaodi.bean.JsonResult;
import com.zhaodi.bean.Page;
import com.zhaodi.config.authentication.MyUserDetails;
import com.zhaodi.custom.dao.PatentDao;
import com.zhaodi.custom.entity.*;
import com.zhaodi.custom.service.PatentService;
import com.zhaodi.custom.util.Participle;
import com.zhaodi.oa.dao.PatentClassDao;
import com.zhaodi.oa.entity.AtreeMenu;
import com.zhaodi.util.ExcelTool;
import com.zhaodi.util.comMethod;
import org.apache.poi.ss.usermodel.PictureData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

/**
 * @author WangBo
 * @version 1.0
 * @date 2020/3/31 10:21
 */
@Service("PatentService")
public class PatentServiceIml implements PatentService {

    @Resource(name="PatentDao")
    private PatentDao patentDao;

    @Resource(name = "PatentClassDao")
    private PatentClassDao patentClassDao;
    /**
     * @Describe 获取专利申请人对应条数
     * @author WangBo
     */
    public JSONArray getPatentAdded(Integer top,String companyName,String block) {
        List<String> company=new ArrayList<>();
        if(top!=null){
        }else{
            top=10;//默认前十条
        }
        if (companyName==null || companyName==""){
            companyName="";
        }else{
            String[] names=companyName.split(",");
            for (int i=0;i<names.length;i++){
                company.add(names[i]);
            }
        }
        List<String> blocks=new ArrayList<>();
        if (block!=null && block!=""){
            blocks=Arrays.asList(block);
        }

        List<InnovationJournal> datas=patentDao.getPatentAdded(top,company,1,blocks);
        JSONArray jsonArray = new JSONArray();
        List<String> names=new ArrayList<>();
        List<Integer> values=new ArrayList<>();
        //取出数据，拼成最终数据结构
        for(InnovationJournal inno:datas){
            names.add(inno.getDqsqzqr());
            values.add(inno.getTotal());
        }
        jsonArray.add(names);
        jsonArray.add(values);

        return jsonArray;
    }

    /**
     * @Describe 获取气泡数据及条数
     */
    public List<Map<String, Object>> getEffectBubble(String block) {
        List<String> blocks=new ArrayList<>();
        if (block!=null && block!=""){
            blocks=Arrays.asList(block);
        }
        return patentDao.getEffectBubble(blocks);
    }

    /**
     * @Describe 获取分词
     */
    public List<Map<String,String>> getParticiple(Set<String> expectedNature,Map<String,String> m) {
        List<InnovationJournal> list=patentDao.getParticiple();
        StringBuffer strs= new StringBuffer();
        for (InnovationJournal inno:list){
            strs.append(inno.getZy());
        }
        return Participle.extractParticiple(strs.toString(),expectedNature,m);
    }

    /**
     * @Describe 获取雷达图数据
     */
    public JSONArray getDirClass(JSONObject Data) {
        JSONObject sendData=Data.getJSONObject("sendData");
        String companyName= Data.getString("companyName");
        JSONArray jsonArray=new JSONArray();
        if(sendData!=null){
            String parentId=sendData.getString("id");//获取父级ID
            JSONArray jsonData= sendData.getJSONArray("children");//获取儿子节点数据
            List<InnovationJournal> innos=new ArrayList<>();//用户自定义申请人
            Map<String,List<Integer>> mainClass=new HashMap<>();//用户点击后的第二级所有分类ID
            List<Integer> mainIds=new ArrayList<>();//用户点击后的所有分类ID()包括点击的父级Id
            mainIds.add(Integer.parseInt(parentId));

        /*{ name: '销售（sales）', max: 6500},
        { name: '管理（Administration）', max: 16000},
        { name: '信息技术（Information Techology）', max: 30000},*/
            List<Map<String,String>> mainData=new ArrayList<>();//分类数据
            /*['申请人1', '申请人2']*/
            List<String> names=new ArrayList<>();
        /*[
        {
            value: [4300, 10000, 28000],
            name: '申请人1'
        },
        {
            value: [5000, 14000, 28000],
            name: '申请人2'
        }
        ]*/
            List<Map<String,Object>> detail=new ArrayList<>();
            //1、解析数据,获取每个分类对应的最大数字
            Integer max=0;
            for(int i=0;i<jsonData.size();i++) {
                Map<String,String> m=new HashMap<>();
                JSONObject ob=jsonData.getJSONObject(i);
                List<Integer> list= (List<Integer>) ob.get("childrenList");
                mainClass.put(i+"",list);
                mainIds.addAll(list);
                //查询每个子分类下对应的最大数
                Integer maxClass=patentDao.getTopOne(list);
                if(maxClass!=null && maxClass>max){
                    max=maxClass;
                }
                m.put("max",maxClass==null?"0":maxClass.toString());
                m.put("name",ob.getString("title"));
                mainData.add(m);
            }
            for(Map<String,String> str: mainData){
                str.put("max",max+"");
            }

            //判断参数：申请人，逗号隔开
            if (companyName==null || companyName==""){//若为空获取默认前十的公司
                innos=patentDao.getTopTen(mainIds);
            }else{
                String[] namess=companyName.split(",");
                for (int i=0;i<namess.length;i++){
                    InnovationJournal iv=new InnovationJournal();
                    iv.setDqsqzqr(namess[i]);
                    innos.add(iv);
                }
            }
            //2、根据申请人和分类查找具体对应的申请条数
            for (InnovationJournal inno:innos){
                Map<String,Object> map=new HashMap<>();
                String applicant=inno.getDqsqzqr();//专利申请人
                List<Integer> name=new ArrayList<>();
                for (int i=0;i<mainClass.size();i++){
                    /*tree=treeMenuList(menulist,str);*/
                    Integer num=patentDao.getParticular(applicant,mainClass.get(i+""));
                    name.add(num);
                }
                names.add(applicant);
                map.put("name",applicant);
                map.put("value",name);
                detail.add(map);
            }

            jsonArray.add(mainData);//添加主数据
            jsonArray.add(detail);//添加明细数据
            jsonArray.add(names);//添加申请人名字集合
        }
        return jsonArray;
    }
    /**
     * 获取某个父节点下面的所有子节点
     * @param menuList
     * @param pid
     * @return
     */
    public List<Integer> treeMenuList(List<AtreeMenu> menuList, String pid) {
        List<Integer> childMenu = new ArrayList<Integer>();
        for (AtreeMenu mu : menuList) {
            //遍历出父id等于参数的id，add进子节点集合
            if (mu.getSuperId().equals(pid)) {
                childMenu.add(Integer.parseInt(mu.getId()));
                //递归遍历下一级
                List<Integer>  m=treeMenuList(menuList, mu.getId());
                childMenu.addAll(m);
            }
        }
        return childMenu;
    }

    /**
     * @Describe 根据条件获取专利申请人
     * @author WangBo
     */
    @Override
    public List<Map<String, String>> getCompanyNames() {
        List<String> blocks=new ArrayList<>();
        List<InnovationJournal> datas=patentDao.getPatentAdded(10,new ArrayList<>(),0, blocks);
        List<Map<String, String>> list=new ArrayList<>();
        for (int i=0;i<datas.size();i++){
            Map<String, String> map=new HashMap<>();
            map.put("value",(i+1)+"");
            map.put("title",datas.get(i).getDqsqzqr());
            list.add(map);
        }
        return list;
    }
    /**
     * @Describe 上传excel,解析数据
     * @author WangBo
     */
    @Override
    @Transactional
    public JsonResult getExcel(MultipartFile file, MyUserDetails principal) throws Exception {
        Long userID=principal.getUserId();
        String userName=principal.getLastName();
        List<String> fileNames=new ArrayList<>();
        String msgSuccess="导入成功!";
        String msgErro="导入失败!";
        try {
            //读取数据
            List<Integer> erros = new ArrayList<Integer>();
            File f=ExcelTool.multipartFileToFile(file);
            Map<String,String> m=new HashMap<>();
            SysCatalog sc=patentDao.getFileUrl(1001);//默认给专利模块id
            String uploadSubDir=sc.getModeUrl();
            String uploadRoot=sc.getServerUrl();
            m.put("uploadRoot",uploadRoot);//服务器地址
            m.put("uploadSubDir",uploadSubDir);//上传的目录
            m.put("isImage","1");//是否要保存图片 1代表是
            m.put("imageFiled","img_url");//图片列名
            // 判断文件夹是否存在
            ExcelTool.isExistDir(uploadRoot+uploadSubDir);

            //获取列名list(应该数据库取，暂时只能做实例)
            String[] cols=new String[]{"gkh","img_url","sqh","bt","dqsqzqr","fmr","sqny","fk","bz","ipcflh","locflh","zy","sqr","gkr","sqrr","yyzl","yxqgj","yxqh","yxqr","dqsq","dljg","dlr","qlyq","flztsj","flztgx","dqsqzlq","dlql","kztzyy","kztzcy","kztz","yssq","zllx","byyzl","slj","ipczflh","byyzls","yyzlsl","jdtzbyy","jdtzcy","jdtz","jswt","jssd","jsxg"};
            List<String> list=Arrays.asList(cols);

            //读取数据
            JSONArray arr = ExcelTool.readExcel(f,m,list);
            Integer arr_length=arr.size()-1;
            //获取图片信息（放在最后）
            Map<Integer, PictureData> imgData= (Map<Integer, PictureData>) arr.get(arr_length);
            Integer successNum=0;
            Integer fkNull=0;
            Integer cfNum=0;
            //存储数据
            if(arr.size()>0){
                for(int i=0;i<arr_length;i++){
                    boolean GO=true;
                    JSONObject job = arr.getJSONObject(i);   // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    InnovationJournal inData =job.toJavaObject(InnovationJournal.class);
                    String fkk=inData.getFk();//分块
                    String gkh=inData.getGkh();//公开号
                    Integer reNum=patentDao.countDuplicateNumber(gkh);
                    if(fkk.equals("")){
                        fkNull+=1;
                    }
                    if(reNum>=1){
                        cfNum+=1;
                    }
                    if(!fkk.equals("") && reNum==0){//分块是空，不导入
                        // 获取图片流
                        PictureData pic = imgData.get(i+1);
                        //获取图片格式
                        String ext="";
                        //保存的文件名
                        String fileName="";
                        String url="";

                        if (pic==null){
                            GO=false;
                        }else{
                            ext = pic.suggestFileExtension();
                            //保存的文件名
                            fileName = UUID.randomUUID().toString() + "." + ext;
                            url=uploadSubDir + "/" + fileName;
                        }


                        inData.setImg_url(url);
                        inData.setCreater(userID);
                        inData.setCreatTime(comMethod.getDateTime1());
                        inData.setStatus(1);//状态有效
                        inData.setCreatName(userName);
                        //分块处理

                        String[] fk=inData.getFk().split(">");
                        String className=fk[fk.length-1];
                        inData.setFk(className);//设置分块中文
                        //根据分块名称查询ID
                        Integer parentId=patentClassDao.findClassIdByName(fk[0],0);
                        inData.setBigClass(parentId+"");
                        Integer classId=patentClassDao.findClassIdByName(className,parentId);

                        if(classId==null || classId==0){
                            cleanFile(fileNames);
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            msgErro=msgErro+"  原因：导入第"+(i+2)+"行时所属分类不存在或者不符合规范（规范如：‘顶层父类名称>分块名称’），请重新检查！";
                            return new JsonResult("0",msgErro);
                        }else {
                            inData.setSsfl(classId+"");
                        }

                        Integer state=patentDao.savePatentData(inData);//存数据
                        if(state!=1){
                            erros.add(i);
                        }else{
                            successNum+=1;
                        }
                        //输出全路径
                        String outPath = uploadRoot + url;
                        fileNames.add(outPath);
                        if(GO){
                            byte[] data = pic.getData();
                            FileOutputStream out = new FileOutputStream(outPath);
                            out.write(data);
                            out.close();
                        }
                    }
                }
            }
            msgSuccess=msgSuccess+"  PS：总共导入数据："+arr_length+"条；成功插入数据："+successNum+"条；分块为空的数量："+fkNull+"条（分块为空未导入）；重复数据："+cfNum+"条（重复数据未导入）";
            return new JsonResult(erros,"1",msgSuccess);
        } catch (Exception e) {
            System.out.println(e);
            //图片回滚===》删除图片
            cleanFile(fileNames);
            msgErro=msgErro+"  原因：导入时存在异常（可能是不符和导入规定的模板，或者是数据某一列长度超出数据库字段长度），数据回滚。";

            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new JsonResult("导入异常，数据回滚","0",msgErro);
        }
    }
    private void cleanFile(List<String> fileNames) throws Exception {
        for (String str:fileNames){
            File file=new File(str);
            if (file.exists()) {
                file.delete();//删除原先的图片
            }
        }

    }
    /**
     * @Describe 获取数据
     * @author WangBo
     */
    @Override
    public Page<InnovationJournal> getPatentData(Page<InnovationJournal> pageData, String companyName,String yearRange) {
        String startYear="";
        String endYear="";
        List<String> company=new ArrayList<>();
/*        System.out.println(yearRange);*/
        if(yearRange!=null && !yearRange.equals("")){
            String[] str=yearRange.split(" - ");
            startYear=str[0].trim();
            endYear=str[1].trim();
        }
        if (companyName==null || companyName==""){
            companyName="";
        }else{
            String[] names=companyName.split(",");
            for (int i=0;i<names.length;i++){
                company.add(names[i]);
            }
        }
        /*List<String> blocks=new ArrayList<>();
        String obj=pageData.getBean().getBelongToBlock();
        if (obj!=null && obj!=""){
            blocks=Arrays.asList(obj);
        }*/
        Page<InnovationJournal> page = new Page<>();
        List<InnovationJournal> innos = patentDao.getPatentData(pageData,company,startYear,endYear);
        page.setBeans(innos);
        Integer num=patentDao.getPatentDataCounts(pageData,company,startYear,endYear);
        page.setCounts(num);
        page.setStatus(200);
        return page;
    }
    /**
     * @Describe 企业年份月份折线图分析
     * @author WangBo
     */
    @Override
    public JSONArray getPatentLine(SearchWords sw) {
        Integer type=sw.getType();
        String companyName=sw.getCompanyName();
        Integer top=sw.getTop();
        String yearRange=sw.getYearRange();

        List<String> companyData=new ArrayList<>();
        List<JSONObject> listData=new ArrayList<>();
        JSONArray data=new JSONArray();

        // 获取月(月份是固定的)
        String[] month={"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
        List<String> YM_TYPE=new ArrayList<>();
        List<String> company=new ArrayList<>();
        String startYear="";
        String endYear="";
        if(yearRange!=null && !yearRange.equals("")){
            String[] str=yearRange.split("-");
            startYear=str[0].trim();
            endYear=str[1].trim();
        }
        if (type==null){
            YM_TYPE=Arrays.asList(month);
            type=2;//默认
        }
        else if(type==1){//按年份查询
            // 获取年(获取已有数据的年份)
            YM_TYPE=patentDao.getYears(startYear,endYear);//年份
        }else if(type==2){//按月份查询
            YM_TYPE=Arrays.asList(month);
        }else{
            YM_TYPE=Arrays.asList(month);
            type=2;//默认
        }

        if (companyName==null || companyName==""){
            companyName="";
        }else{
            String[] names=companyName.split(",");
            for (int i=0;i<names.length;i++){
                company.add(names[i]);
            }
        }
        if(top==null || top==0){
            top=10;
        }
        sw.setCompany(company);
        sw.setStartYear(startYear);
        sw.setEndYear(endYear);
        sw.setTop(top);

        //获取数据
       /*{
            name: '直接访问',
            type: 'line',
            stack: '总量',
            data: [320, 332, 301, 334, 390, 330, 320]
        }*/
        if(type==null || type==2){//按月获取数据
            List<Map<String,Object>> list=patentDao.getMonthOfPatent(sw);
            for (Map<String,Object> m:list){
                JSONObject json=new JSONObject();
                String name=m.get("dqsqzqr").toString();//专利申请人
                companyData.add(name);//专利申请人
                List<String> month_list=new ArrayList<>();
                for (int i=1;i<=12;i++){
                    month_list.add(m.get("month"+i).toString()+"");//月数据
                }
                json.put("name",name);
                json.put("type","line");
                /*json.put("stack","总量");*/
                json.put("data",month_list);

                //添加到listData
                listData.add(json);
            }

        }else if(type==1){//按年
            //对年进行数据分析
            List<Map<String,Object>> lists=patentDao.getYearOfPatent(YM_TYPE,sw);
            for (Map<String,Object> m:lists){
                JSONObject json=new JSONObject();
                String name=m.get("dqsqzqr").toString();//专利申请人
                companyData.add(name);//专利申请人
                List<String> year_list=new ArrayList<>();
                for (String ss:YM_TYPE){
                    year_list.add(m.get(ss).toString()+"");//年数据
                }
                json.put("name",name);
                json.put("type","line");
                /*json.put("stack","总量");*/
                json.put("data",year_list);

                //添加到listData
                listData.add(json);
            }
        }

        data.add(companyData);
        data.add(YM_TYPE);
        data.add(listData);
        return data;
    }

    /**
     * @Describe 获取分词关键词数据
     * @author WangBo
     */
    @Override
    public Page<UfPatentdataWord> findPartWords(Page<UfPatentdataWord> pageData) {
        Page<UfPatentdataWord> page = new Page<>();
        List<UfPatentdataWord> partWords = patentDao.findPartWords(pageData);
        page.setBeans(partWords);
        Integer num=patentDao.findPartWordsCounts(pageData);
        page.setCounts(num);
        page.setStatus(200);
        return page;
    }

    /**
     * @Describe 删除关键词数据
     * @author WangBo
     */
    @Override
    @Transactional
    public Integer deletePartWordsT(String idStr) {
        Integer num=0;
         try {
            String[] str=idStr.split(",");
            for (int i=0;i<str.length;i++){
                Integer id=Integer.parseInt(str[i]);
                patentDao.deletePartWordsT(id);
                num++;
            }
            return num;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
    }

    /**
     * @Describe 插入关键词数据
     * @author WangBo
     */
    @Override
    public Integer insertPartWords(UfPatentdataWord record) {
        return patentDao.insertPartWords(record);
    }

    /**
     * @Describe 修改分词关键词数据
     * @author WangBo
     */
    @Override
    public Integer updatePartWords(UfPatentdataWord record) {
        return patentDao.updatePartWords(record);
    }

    @Override
    public String getWordsAndColName() {
        List<Map<String, String>> list1=patentDao.findWordOfCol();
        List<Map<String, String>> list2=patentDao.findWordOfCX();
        JSONArray jsonArray = new JSONArray() ;
        jsonArray.add(list1);
        jsonArray.add(list2);
        return jsonArray.toString();
    }

    @Override
    public List<Map<String, String>> WordCloudAnalysis(Set<String> expectedNature, Map<String, String> m, SearchWords sw) throws Exception {
        //定义过滤词
        List<String> filterWord=new ArrayList<>();
        List<PatentFilterWord> filterWords=patentDao.findFilterWords1();
        for (PatentFilterWord p:filterWords){
            filterWord.add(p.getWord());
        }

        //定义关键词
        List<UfPatentdataWord> partWords=new ArrayList<>();
        StringBuffer strs= new StringBuffer();

        if(sw.getFlag()==null || sw.getFlag().equals("1")){//默认使用分词库
            //查询分词库用户自定义关键词
            partWords = patentDao.findAllPartWords();
            List<String> company=new ArrayList<>();
            String companyName=sw.getWordColumn();
            if (companyName==null || companyName==""){
                company.add("zy");
            }else{
                String[] names=companyName.split(",");
                for (int i=0;i<names.length;i++){
                    company.add(names[i]);
                }
            }
            List<String> blocks=new ArrayList<>();
            if (sw.getBelongToBlock()!=null && sw.getBelongToBlock()!=""){
                blocks=Arrays.asList(sw.getBelongToBlock());
            }
            //查询词
            List<InnovationJournal> list=patentDao.getParticipleWords(company,blocks);

            for (InnovationJournal inno:list){
                for(String s:company){
                    strs.append(comMethod.getGetMethod(inno,s));
                }
            }
            return Participle.executeParticiple(strs.toString(),expectedNature,m,partWords,filterWord);
        }else {
            //查询分词库用户自定义关键词
            partWords = patentDao.findAllPartWords();
            //获取自定义词句
            return Participle.executeParticiple(sw.getSelfWords(),expectedNature,m,partWords,filterWord);
        }

    }

    @Override
    public Page<PatentFilterWord> findFilterWords(Page<PatentFilterWord> pageData) {
        Page<PatentFilterWord> page = new Page<>();
        List<PatentFilterWord> partWords = patentDao.findFilterWords(pageData);
        page.setBeans(partWords);
        Integer num=patentDao.findPartFilterCounts(pageData);
        page.setCounts(num);
        page.setStatus(200);
        return page;
    }

    @Override
    @Transactional
    public Integer deleteFilterWordsF(String idStr) {
        Integer num=0;
        try {
            String[] str=idStr.split(",");
            for (int i=0;i<str.length;i++){
                Integer id=Integer.parseInt(str[i]);
                patentDao.deleteFilterWordsF(id);
                num++;
            }
            return num;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
    }

    @Override
    public Integer insertFilterWords(PatentFilterWord record) {
        return patentDao.insertFilterWords(record);
    }

    @Override
    public Integer updateFilterWords(PatentFilterWord record) {
        return patentDao.updateFilterWords(record);
    }
}