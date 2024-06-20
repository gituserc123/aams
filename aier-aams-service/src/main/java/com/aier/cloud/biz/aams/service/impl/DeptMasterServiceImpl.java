package com.aier.cloud.biz.aams.service.impl;

import com.aier.cloud.biz.aams.dao.DeptMasterMapper;
import com.aier.cloud.biz.aams.entity.DeptMaster;
import com.aier.cloud.biz.aams.service.DeptMasterService;
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
 * @since 2024-04-20 11:47:37
 */
@Service
public class DeptMasterServiceImpl extends ServiceImpl<DeptMasterMapper, DeptMaster> implements DeptMasterService {


    @Override
    public List<DeptMaster> getDepartmentHierarchy(String deptMasterCode) {
        List<DeptMaster> result = new ArrayList<>();
        findParentDepartments(deptMasterCode, result);
        return result;
    }

    private void findParentDepartments(String deptMasterCode, List<DeptMaster> result) {
        DeptMaster department = this.baseMapper.selectByDeptMasterCode(deptMasterCode);
        if (department != null) {
            result.add(department);
            if (department.getDeptMasterParentCode() != null && !department.getDeptMasterParentCode().trim().equals("")) {
                findParentDepartments(department.getDeptMasterParentCode(), result);
            }
        }
    }
}
