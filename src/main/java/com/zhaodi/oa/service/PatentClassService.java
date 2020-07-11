package com.zhaodi.oa.service;

import com.alibaba.fastjson.JSONArray;
import com.zhaodi.oa.entity.AtreeMenu;

import java.util.List;

/**
 * @author WangBo
 * @version 1.0
 * @Describe 描述
 * @date 2020/5/9 10:56
 */
public interface PatentClassService {
    List<AtreeMenu> getAtreeMenu(Integer type);
}
