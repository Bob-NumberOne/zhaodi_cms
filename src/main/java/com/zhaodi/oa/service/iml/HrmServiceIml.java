package com.zhaodi.oa.service.iml;
import com.zhaodi.oa.dao.HrmResourceDao;
import com.zhaodi.oa.entity.HrmResource;
import com.zhaodi.oa.service.HrmService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author WangBo
 * @version 1.0
 * @date 2020/3/31 10:21
 */
@Service("HrmService")
public class HrmServiceIml implements HrmService {
    @Resource(name="HrmResourceDao")
    private HrmResourceDao hrmResourceDao;

    @Override
    public List<HrmResource> getUsers() {
        return hrmResourceDao.getUsers();
    }
    /**
     * @Describe 根据loginid查人员相关信息（OA用户和管理员不在同一张表）
     */
    @Override
    public HrmResource getHrmResource(String loginId) {
        HrmResource hrmResource=hrmResourceDao.getHrmResource(loginId);//普通用户
        if(hrmResource==null){
            hrmResource=hrmResourceDao.findManager(loginId);//管理员
        }
        return hrmResource;
    }
    /**
     * @Describe 查询人员姓名和ID
     */
    @Override
    public List<Map<String, String>> getHrmResourcerowse() {
        return hrmResourceDao.getHrmResourcerowse();
    }

}
