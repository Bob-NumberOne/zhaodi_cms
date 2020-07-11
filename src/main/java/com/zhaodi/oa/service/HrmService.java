package com.zhaodi.oa.service;

import com.zhaodi.oa.entity.HrmResource;

import java.util.List;
import java.util.Map;

/**
 * @author WangBo
 * @version 1.0
 * @date 2020/3/31 10:21
 */
public interface HrmService {
    List<HrmResource> getUsers();
    /**
     * @Describe 根据loginid查询人员相关信息
     */
    HrmResource getHrmResource(String loginId);
    /**
     * @Describe 查询人员姓名和ID
     */
    List<Map<String, String>> getHrmResourcerowse();
}
