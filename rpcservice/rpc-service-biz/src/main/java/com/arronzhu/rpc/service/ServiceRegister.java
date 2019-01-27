package com.arronzhu.rpc.service;

import com.arronzhu.rpc.core.provider.IProvider;
import com.arronzhu.rpc.service.impl.CaculatorImpl;
import com.arronzhu.rpc.servicecenter.entity.ServiceInfoDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author arronzhu
 * @date 2018/10/31
 * @description
 */
@Component
public class ServiceRegister {
    private static IProvider provider;

    @Autowired
    private void setProvider(IProvider provider) {
        ServiceRegister.provider = provider;
    }

    public static boolean register(){
        try {
            ServiceInfoDO serviceInfoDO = new ServiceInfoDO();
            serviceInfoDO.setInterfaceName(ICaculator.class.getCanonicalName());
            serviceInfoDO.setVersion("1.0");
            serviceInfoDO.setImplClassName(CaculatorImpl.class.getCanonicalName());
            serviceInfoDO.setIp("127.0.0.1");
            boolean res = provider.servicePublish(serviceInfoDO);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
