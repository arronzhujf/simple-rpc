package com.arronzhu.rpc.core.consumer;

import com.arronzhu.rpc.core.entity.RequestDO;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author arronzhu
 * @date 2018/10/22
 * @description
 */
public interface IConsumerService {
    Set<String> getServiceIPsByID(String serviceID);

    String getIP(String serviceID, String methodName, List<Object> params, Set<String> ips);

    RequestDO getRequestDO(String interfaceName, String version, String methodName, List<Object> params);

    Object sendData(String ip, RequestDO requestDO) throws IOException, ClassNotFoundException;
}
