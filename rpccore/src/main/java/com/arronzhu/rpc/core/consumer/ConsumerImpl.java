package com.arronzhu.rpc.core.consumer;

import com.arronzhu.rpc.core.Constant;
import com.arronzhu.rpc.core.entity.RequestDO;
import com.arronzhu.rpc.core.exception.RPCRemoteCallException;
import com.arronzhu.rpc.core.exception.ServiceIDIllegalException;
import com.arronzhu.rpc.core.exception.ServiceNotFoundException;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author arronzhu
 * @date 2018/10/23
 * @description
 */
@Component
public class ConsumerImpl implements IConsumer {
    @Value("com.arronzhu.rpc.service.ICaculator")
    private String interfaceName;//服务对应接口的全限定名
    @Value("1.0")
    private String version;//服务版本号

    @Autowired
    private IConsumerService consumerService;//初始化客户端辅助类

    @Override
    public Object serviceConsumer(String methodName, List<Object> params) throws ServiceIDIllegalException, ServiceNotFoundException, RPCRemoteCallException {
        if (Strings.isNullOrEmpty(interfaceName)
                || Strings.isNullOrEmpty(version)) {
            throw new ServiceIDIllegalException();
        }
        String serviceID = interfaceName + "_" + version;
        Set<String> ips = consumerService.getServiceIPsByID(serviceID);
        if (CollectionUtils.isEmpty(ips)) {
            throw new ServiceNotFoundException();
        }

        String serviceAddr = consumerService.getIP(serviceID, methodName, params, ips);

        RequestDO requestDO = consumerService.getRequestDO(interfaceName, version, methodName, params);

        Object res = null;
        try {
            res = consumerService.sendData(serviceAddr, requestDO);
        } catch (Exception e) {
            throw new RPCRemoteCallException(e.getMessage());
        }
        if (res == null) throw new RPCRemoteCallException(Constant.SERVICE_UNKNOWN);

        return res;
    }
}
