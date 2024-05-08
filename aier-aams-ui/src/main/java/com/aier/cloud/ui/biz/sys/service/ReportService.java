package com.aier.cloud.ui.biz.sys.service;

import com.aier.cloud.basic.api.request.condition.sys.SysCommonCondition;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 报表权限
 * @author xiaokek
 * @since 2018年5月24日 下午4:54:28
 */
@FeignClient(name="aier-service-system")
public interface ReportService {
    
    @RequestMapping(value = "/api/sys/report/updatePerm", method = RequestMethod.POST)
	void updatePerm(SysCommonCondition m);

    @RequestMapping(value = "/api/sys/report/getStaffReport", method = RequestMethod.POST)
	Object getStaffReport(SysCommonCondition m);

    @RequestMapping(value = "/api/sys/report/updateReport", method = RequestMethod.POST)
	void updateReport(SysCommonCondition m);

    @RequestMapping(value = "/api/sys/report/getList", method = RequestMethod.POST)
    Object getList(SysCommonCondition m);

    @RequestMapping(value = "/api/sys/report/getReportStaff", method = RequestMethod.POST)
	Object getReportStaff(SysCommonCondition c);

    @RequestMapping(value = "/api/sys/report/updateReportPerm", method = RequestMethod.POST)
	void updateReportPerm(SysCommonCondition m);
}
