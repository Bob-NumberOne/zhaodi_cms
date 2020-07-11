package com.zhaodi.oa.dao;
import com.zhaodi.oa.entity.AtreeMenu;
import com.zhaodi.oa.entity.UfClassification;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
 * @author WangBo
 * @version 1.0
 * @Describe 描述
 * @date 2020/5/9 10:55
 */
@Repository("PatentClassDao")
public interface PatentClassDao {
    /**
     * @Describe 根据父亲id获取树信息
     */
    List<Map<String,Object>> findPatentClass(String parentId);
    /**
     * @Describe 根据id修改树信息
     */
    Integer updatePatentClass(UfClassification record);
    /**
     * @Describe 插入树信息
     */
    Integer insertPatentClass(UfClassification record);
    /**
     * @Describe 根据id删除树信息
     */
    Integer deletePatentClass(@Param("id") Integer id);

    List<AtreeMenu> getAtreMenus();
    /**
     * @Describe 根据分类名称查询ID
     */
    Integer findClassIdByName(@Param("className") String className,@Param("topId") Integer topId);
}
