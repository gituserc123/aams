package com.aier.cloud.biz.aams.service.impl;

import com.aier.cloud.biz.aams.dao.OrgMasterMapper;
import com.aier.cloud.biz.aams.entity.OrgMaster;
import com.aier.cloud.biz.aams.service.OrgMasterService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:52:58
 */
@Service
public class OrgMasterServiceImpl extends ServiceImpl<OrgMasterMapper, OrgMaster> implements OrgMasterService {

    @Override
    public List<OrgMaster> getOrgMasterHierarchy(String orgMasterId) {
        List<OrgMaster> result = new ArrayList<>();
        findParentDepartments(orgMasterId, result);
        return result;
    }

    private void findParentDepartments(String orgMasterId, List<OrgMaster> result) {
        OrgMaster orgMaster = this.baseMapper.selectByOrgMasterId(orgMasterId);
        if (orgMaster != null) {
            result.add(orgMaster);
            if (orgMaster.getOrgMasterParentCode() != null && !orgMaster.getOrgMasterParentCode().trim().equals("")) {
                findParentDepartments(orgMaster.getOrgMasterParentCode(), result);
            }
        }
    }
}
