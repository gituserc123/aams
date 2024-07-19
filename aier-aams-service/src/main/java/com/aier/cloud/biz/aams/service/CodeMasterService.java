package com.aier.cloud.biz.aams.service;

import com.aier.cloud.biz.aams.entity.CodeMaster;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 柯南
 * @since 2024-04-20 11:47:37
 */
public interface CodeMasterService extends IService<CodeMaster> {
    List<CodeMaster> getCodeMasterByType(String codeMasterType);
}
