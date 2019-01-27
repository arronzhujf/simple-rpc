package com.arronzhu.rpc.core.provider;

import com.arronzhu.rpc.core.exception.ServiceInfoNotCompleteException;
import com.arronzhu.rpc.core.exception.ServiceListenFailedException;
import com.arronzhu.rpc.servicecenter.entity.ServiceInfoDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author arronzhu
 * @date 2018/10/23
 * @description
 */
@Component
public class ProviderImpl implements IProvider {

    private static boolean isListened = false;//是否已经开启监听

    @Autowired
    private IProviderService providerService;


    public void checkInfo(ServiceInfoDO serviceInfo) throws ServiceInfoNotCompleteException {
        //先判断服务参数信息是否完整
        if (serviceInfo.getInterfaceName() == null || serviceInfo.getInterfaceName().length() == 0 ||
                serviceInfo.getVersion() == null || serviceInfo.getVersion().length() == 0 ||
                serviceInfo.getImplClassName() == null || serviceInfo.getImplClassName().length() ==0 ||
                serviceInfo.getIp() == null || serviceInfo.getIp().length() ==0)
            throw new ServiceInfoNotCompleteException();
    }

    @Override
    public boolean servicePublish(ServiceInfoDO serviceInfo) throws ServiceInfoNotCompleteException, ServiceListenFailedException{
        checkInfo(serviceInfo);
        //step1. 注册服务.注册服务之前先判断服务是否开启,若没有开启,则首先开启服务
        synchronized (ProviderImpl.class){
            if (!isListened){
                if (providerService.startListen())
                    isListened = true;
                else throw new ServiceListenFailedException();
            }
            providerService.serviceregistry(serviceInfo);
        }
        return true;
    }

}
