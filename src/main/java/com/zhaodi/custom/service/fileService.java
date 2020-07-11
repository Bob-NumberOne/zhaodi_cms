package com.zhaodi.custom.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhaodi.custom.entity.InnovationJournal;
import com.zhaodi.custom.entity.SysCatalog;

/**
 * @author WangBo
 * @version 1.0
 * @Describe 描述
 * @date 2020/4/25 16:23
 */
public interface fileService {
    /**
     * @Describe 获取轮播图片
     * @author WangBo
     */
    JSONObject getPicData(InnovationJournal ino);

    SysCatalog getPath(int id);
}
