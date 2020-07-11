package com.zhaodi.custom.dao;
import com.zhaodi.bean.Page;
import com.zhaodi.custom.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
 * @author WangBo
 * @version 1.0
 * @date 2020/3/31 10:21
 */
@Repository("PatentDao")
public interface PatentDao {
    /**
     * @Describe 获取专利申请人对应条数
     */
    List<InnovationJournal> getPatentAdded(@Param("top") Integer top, @Param("companyName") List<String> companyName, @Param("isASC") Integer isASC,@Param("block") List<String> block);
    /**
     * @Describe 获取气泡数据及条数
     * @param blocks
     */
    List<Map<String, Object>> getEffectBubble(@Param("block") List<String> block);
    /**
     * @Describe 获取词库中的词
     */
    List<InnovationJournal> getParticiple();
    /**
     * @Describe 查询专利申请方向大类
     */
    List<Map<String, Object>> getDirClass();
    /**
     * @Describe 查询专利申请方向大类最多的前十条
     * @param tree
     */
    List<InnovationJournal> getTopTen(@Param("tree")List<Integer> tree);
/*    List<InnovationJournal> getTopTen(List<String> mainClass, int top);*/
    /**
     * @Describe 根据申请人和分类查找具体对应的申请条数
     */
    Integer getParticular(@Param("applicant") String applicant, @Param("tree")List<Integer> tree);
    /**
     * @Describe 根据分类id查出该分类中某公司最大值
     */
    Integer getTopOne(@Param("tree") List<Integer> tree);
    /**
     * @Describe 存储专利对应数据
     */
    Integer savePatentData(@Param("data") InnovationJournal data);
    /**
     * @Describe 根据模块获取路径
     */
    SysCatalog getFileUrl(@Param("modeId") long modeId);
    /**
     * @Describe 获取创新刊物数据条数
     */
    Integer getPatentDataCounts(@Param("page")Page<InnovationJournal> page,@Param("companyName") List<String>  companyName,@Param("startYear") String startYear,@Param("endYear") String endYear);
    /**
     * @Describe 获取创新刊物数据
     */
    List<InnovationJournal> getPatentData(@Param("page") Page<InnovationJournal> page, @Param("companyName") List<String> companyName, @Param("startYear") String startYear, @Param("endYear") String endYear);
    /**
     * @Describe 专利申请人月数据
     */
    List<Map<String, Object>> getMonthOfPatent(@Param("sw") SearchWords sw);
    /**
     * @Describe 动态获取专利年份
     */
    List<String> getYears(@Param("startYear") String startYear,@Param("endYear") String endYear);
    /**
     * @Describe 专利申请人年数据
     */
    List<Map<String, Object>> getYearOfPatent(@Param("listYear")List<String> listYear,@Param("sw") SearchWords sw);
    /**
     * @Describe 获取轮播图片
     */
    List<InnovationJournal> getPicData();
    /**
     * @Describe 关键词词之增删改查
     */
    List<UfPatentdataWord> findPartWords(@Param("page")Page<UfPatentdataWord> page);

    Integer findPartWordsCounts(@Param("page")Page<UfPatentdataWord> page);

    Integer insertPartWords(UfPatentdataWord record);

    Integer updatePartWords(UfPatentdataWord record);

    Integer deletePartWordsF(@Param("id")Integer id);

    Integer deletePartWordsT(@Param("id")Integer id);

    List<Map<String, String>> findWordOfCX();

    List<Map<String, String>> findWordOfCol();

    List<UfPatentdataWord> findAllPartWords();
    /**
     * @Describe 获取词库中的词
     */
    List<InnovationJournal> getParticipleWords(@Param("ColName") List<String> ColName, @Param("block")List<String> block);

    /**
     * @Describe 过滤分词之增删改查
     */
    List<PatentFilterWord> findFilterWords(@Param("page")Page<PatentFilterWord> page);
    List<PatentFilterWord> findFilterWords1();

    Integer findPartFilterCounts(@Param("page")Page<PatentFilterWord> page);

    Integer insertFilterWords(PatentFilterWord record);

    Integer updateFilterWords(PatentFilterWord record);

    Integer deleteFilterWordsF(@Param("id")Integer id);

    Integer countDuplicateNumber(@Param("gkh")String gkh);

}
