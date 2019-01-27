package com.arronzhu.rpc.core.provider;

import com.arronzhu.rpc.core.exception.ServiceInfoNotCompleteException;
import com.arronzhu.rpc.core.exception.ServiceListenFailedException;
import com.arronzhu.rpc.servicecenter.entity.ServiceInfoDO;

/**
 * @author arronzhu
 * @date 2018/10/23
 * @description
 */
public interface IProvider  {
    boolean servicePublish(ServiceInfoDO serviceInfo) throws ServiceInfoNotCompleteException, ServiceListenFailedException;
}
