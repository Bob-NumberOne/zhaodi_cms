package com.zhaodi.custom.service;
import com.alibaba.fastjson.JSONObject;
import com.zhaodi.bean.JsonResult;
import com.zhaodi.bean.Page;
import com.zhaodi.config.authentication.MyUserDetails;
import com.zhaodi.custom.entity.InnovationJournal;
import com.alibaba.fastjson.JSONArray;
import com.zhaodi.custom.entity.PatentFilterWord;
import com.zhaodi.custom.entity.SearchWords;
import com.zhaodi.custom.entity.UfPatentdataWord;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author WangBo
 * @version 1.0
 * @date 2020/3/31 10:21
 */
public interface PatentService {
    /**
     * @Describe 获取专利申请人对应条数
     * @author WangBo
     */
    JSONArray getPatentAdded(Integer top, String companyName,String block);
    /**
     * @Describe 获取气泡数据及条数
     */
    List<Map<String, Object>> getEffectBubble(String block);
    /**
     * @Describe 获取气泡数据及条数
     */
    List<Map<String, String>> getParticiple(Set<String> expectedNature,Map<String,String> m);
    /**
     * @Describe 获取雷达图数据
     */
    JSONArray getDirClass(JSONObject sendData);
    /**
     * @Describe 根据条件获取专利申请人
     * @author WangBo
     */
    public List<Map<String, String>> getCompanyNames();
    /**
     * @Describe 上传excel,解析数据
     * @author WangBo
     */
    JsonResult getExcel(MultipartFile file, MyUserDetails principal) throws Exception;
    /**
     * @Describe 获取数据
     * @author WangBo
     */
    Page<InnovationJournal> getPatentData(Page<InnovationJournal> pageData, String companyName,String yearRange);
    /**
     * @Describe 企业年份月份折线图分析
     * @author WangBo
     */
    JSONArray getPatentLine(SearchWords sw);
    /**
     * @Describe 获取分词关键词数据
     * @author WangBo
     */
    Page<UfPatentdataWord> findPartWords(Page<UfPatentdataWord> pageData);
    Integer deletePartWordsT(String idStr);
    Integer insertPartWords(UfPatentdataWord record);
    Integer updatePartWords(UfPatentdataWord record);
    String getWordsAndColName();
    /**
     * @Describe 获取分词结果
     * @author WangBo
     */
    List<Map<String, String>> WordCloudAnalysis(Set<String> expectedNature, Map<String, String> m, SearchWords sw) throws Exception;

    Page<PatentFilterWord> findFilterWords(Page<PatentFilterWord> pageData);

    Integer deleteFilterWordsF(String idStr);

    Integer insertFilterWords(PatentFilterWord record);

    Integer updateFilterWords(PatentFilterWord record);
}
