package com.arronzhu.rpc.servicecenter.service.impl;

import com.arronzhu.rpc.servicecenter.entity.ServiceInfoDO;

import java.util.Set;

/**
 * @author arronzhu
 * @date 2018/10/22
 * @description
 */
public interface IServiceAccess {
    boolean serviceRegistry(ServiceInfoDO serviceInfoDO);

    Set<String> queryServiceIPsByID(String serviceID);

    ServiceInfoDO queryServiceInfoByID(String serviceID);
}
