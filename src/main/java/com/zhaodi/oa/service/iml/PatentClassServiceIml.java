package com.zhaodi.oa.service.iml;
import com.zhaodi.oa.dao.PatentClassDao;
import com.zhaodi.oa.entity.AtreeMenu;
import com.zhaodi.oa.service.PatentClassService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WangBo
 * @version 1.0
 * @Describe 描述
 * @date 2020/5/9 10:56
 */
@Service("PatentClassService")
public class PatentClassServiceIml implements PatentClassService {
    @Resource(name = "PatentClassDao")
    private PatentClassDao patentClassDao;

    @Override
    public List<AtreeMenu> getAtreeMenu(Integer type) {
        //先根据角色查询角色-菜单关联表获取菜单ID集合，原始菜单数据集合
        List<AtreeMenu> menulist = patentClassDao.getAtreMenus();
        List<AtreeMenu> atreeMenus = new ArrayList<AtreeMenu>();
        for (AtreeMenu treeNode : menulist) {
            if (StringUtils.isBlank(treeNode.getSuperId())) {
                treeNode.setSpread(true);
                atreeMenus.add(findChildren(treeNode, menulist));
            }
        }
        return atreeMenus;
    }

    /**
     * 递归查找子节点
     * @param menu
     * @param menulist
     * @return
     **/
    public static AtreeMenu findChildren(AtreeMenu menu, List<AtreeMenu> menulist) {
        for (AtreeMenu item : menulist) {
            if (menu.getId().equals(item.getSuperId())) {
                if (menu.getChildren() == null) {
                    menu.setChildren(new ArrayList<AtreeMenu>());
                }
                menu.getChildren().add(findChildren(item, menulist));
            }
        }
        return menu;
    }
}
