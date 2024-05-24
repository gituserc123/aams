package com.aier.cloud.ui.biz.aams.feign;

import com.aier.cloud.basic.api.request.condition.sys.StaffCondition;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@FeignClient(name = "aier-aams-service")
public interface SysStaffFeignService {

    @PostMapping(value = "/api/service/biz/aams/sysStaff/pageByInst")
    @ResponseBody Object pageByInst(@RequestBody StaffCondition sc);

    @PostMapping(value = "/api/service/biz/aams/sysStaff/getStaffByCondition")
    @ResponseBody Object getStaffByCondition(@RequestBody StaffCondition sc);

}
