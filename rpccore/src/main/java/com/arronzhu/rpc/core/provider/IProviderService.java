package com.arronzhu.rpc.core.provider;

import com.arronzhu.rpc.servicecenter.entity.ServiceInfoDO;

/**
 * @author arronzhu
 * @date 2018/10/23
 * @description
 */
public interface IProviderService {
    boolean startListen();

    boolean serviceregistry(ServiceInfoDO serviceInfo);
}
