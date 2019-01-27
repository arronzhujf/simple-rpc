package com.arronzhu.rpc.servicecenter.controller;

import com.arronzhu.rpc.servicecenter.entity.ServiceInfoDO;
import com.arronzhu.rpc.servicecenter.service.impl.IServiceAccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author arronzhu
 * @date 2018/10/26
 * @description
 */
@RestController
@Slf4j
public class RegisterController {
    @Autowired
    IServiceAccess serviceAccess;

    @RequestMapping(value = "/serviceRegistry", method = RequestMethod.POST)
    public boolean serviceRegistry(@RequestBody ServiceInfoDO serviceInfoDO) {
        log.info("------serviceRegistry: "+ serviceInfoDO);
        return serviceAccess.serviceRegistry(serviceInfoDO);
    }

    @RequestMapping(value = "/queryServiceIPsByID")
    public Set<String> queryServiceIPsByID(@RequestParam(value = "serviceID") String serviceID) {
        Set<String> ips = serviceAccess.queryServiceIPsByID(serviceID);
        log.info("------queryServiceIPsByID: "+ serviceID + "------res:" + ips);
        return ips;
    }

    @RequestMapping(value = "/queryServiceInfoByID")
    public ServiceInfoDO queryServiceInfoByID(@RequestParam(value = "serviceID") String serviceID) {
        ServiceInfoDO serviceInfoDO = serviceAccess.queryServiceInfoByID(serviceID);
        log.info("------queryServiceIPsByID: "+ serviceID + "------res:" + serviceInfoDO);
        return serviceInfoDO;
    }

}
