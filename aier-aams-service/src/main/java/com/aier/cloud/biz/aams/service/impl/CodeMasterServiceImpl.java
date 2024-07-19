package com.aier.cloud.biz.aams.service.impl;

import com.aier.cloud.biz.aams.dao.CodeMasterMapper;
import com.aier.cloud.biz.aams.entity.CodeMaster;
import com.aier.cloud.biz.aams.service.CodeMasterService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class CodeMasterServiceImpl extends ServiceImpl<CodeMasterMapper, CodeMaster> implements CodeMasterService {

    @Override
    public List<CodeMaster> getCodeMasterByType(String codeMasterType){
        EntityWrapper<CodeMaster> wrapper = new EntityWrapper<>();
        wrapper.eq("CodeMasterType",codeMasterType);
        wrapper.eq("CodeMasterIsdlt",false);
        return this.baseMapper.selectList(wrapper);
    }

}
