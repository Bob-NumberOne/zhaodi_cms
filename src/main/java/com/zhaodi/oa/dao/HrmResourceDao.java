package com.zhaodi.oa.dao;

import com.zhaodi.custom.entity.UserRoleRelation;
import com.zhaodi.oa.entity.HrmResource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
 * @author WangBo
 * @version 1.0
 * @date 2020/3/31 10:21
 */
@Repository("HrmResourceDao")
public interface HrmResourceDao {
    /**
     * @Describe 获取OA所有人员
     */
    List<HrmResource> getUsers();
    /**
     * @Describe 根据loginid查询人员相关信息
     */
    HrmResource getHrmResource(@Param("loginId") String loginId);
    /**
     * @Describe 根据loginid查询管理员相关信息
     */
    HrmResource findManager(@Param("loginId") String loginId);
    /**
     * @Describe 查询人员姓名和ID
     */
    List<Map<String, String>> getHrmResourcerowse();
    /**
     * @Describe 查询人员部门和组织
     */
    UserRoleRelation getUserInfoById(@Param("userId") long userId);
}
