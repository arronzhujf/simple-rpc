package com.arronzhu.rpc.servicecenter.service;

import com.arronzhu.rpc.servicecenter.entity.ServiceInfoDO;
import com.arronzhu.rpc.servicecenter.service.impl.IServiceAccess;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

/**
 * @author arronzhu
 * @date 2018/10/22
 * @description
 */
@Component
public class ServiceAccessImpl implements IServiceAccess {
    @Override
    public boolean serviceRegistry(ServiceInfoDO serviceInfoDO) {
        if (!serviceInfoDO.valid()) return false;

        String serviceID = serviceInfoDO.getInterfaceName() + "_" + serviceInfoDO.getVersion();
        if (ServicesSingle.getInstance().getServices().containsKey(serviceID)) {
            ServicesSingle.getInstance().getServices().get(serviceID).getIps().add(serviceInfoDO.getIp());
        } else {
            serviceInfoDO.getIps().add(serviceInfoDO.getIp());
            ServicesSingle.getInstance().getServices().put(serviceID, serviceInfoDO);
        }
        return true;
    }

    @Override
    public Set<String> queryServiceIPsByID(String serviceID) {
        if (!ServicesSingle.getInstance().getServices().containsKey(serviceID))
            return Collections.emptySet();

        return ServicesSingle.getInstance().getServices().get(serviceID).getIps();
    }

    @Override
    public ServiceInfoDO queryServiceInfoByID(String serviceID) {
        if (!ServicesSingle.getInstance().getServices().containsKey(serviceID)) {
            return null;
        }

        return ServicesSingle.getInstance().getServices().get(serviceID);
    }
}
