package com.aier.cloud.biz.feign;

import com.aier.cloud.basic.api.request.domain.outp.DcgPotentialCustomer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 潜在客户信息
 *
 * @author 爱尔眼科
 * @since 2021-03-02 10:21:21
 */
@FeignClient(name = "aier-service-medical")
public interface PotentialCustomerService {
    /**
     * (保存潜在客户信息)
     *
     * @Title: save
     * @param entity 潜客信息对象
     * @return DcgPotentialCustomer
     */
    @RequestMapping(value = "/api/outp/potentialCustomer/save", method = RequestMethod.POST, consumes = "application/json")
    DcgPotentialCustomer save(@RequestBody DcgPotentialCustomer entity);

    /**
     * (删除潜在客户信息)
     *
     * @Title: deleteById
     * @param id 潜客ID
     * @return Boolean
     */
    @RequestMapping(value = "/api/outp/potentialCustomer/deleteById", method = RequestMethod.POST, consumes = "application/json")
    boolean deleteById(@RequestParam("id") Long id);

    /**
     * (查询潜在客户信息)
     *
     * @param id 潜客ID
     * @return 返回潜客对象
     */
    @RequestMapping(value = "/api/outp/potentialCustomer/getDcgPotentialCustomer", method = RequestMethod.POST, consumes = "application/json")
    DcgPotentialCustomer getById(@RequestParam("id") Long id);

    /***
     * 更新已到诊潜在顾客到诊状态
     * @param customerId 潜客ID
     * @return 返回更新状态
     */
    @RequestMapping(value = "/api/outp/potentialCustomer/updateVisitedStateById", method = RequestMethod.POST, consumes = "application/json")
    boolean updateVisitedStateById(@RequestParam("customerId") Long customerId);

    /***
     * 根据潜客ID迁移潜客数据
     * @param id 潜客ID
     * @return Boolean
     */
    @RequestMapping(value = "/api/outp/potentialCustomer/transfer", method = RequestMethod.POST, consumes = "application/json")
    boolean transfer(@RequestParam("id") Long id);
}
