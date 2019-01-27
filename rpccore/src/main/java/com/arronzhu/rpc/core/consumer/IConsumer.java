package com.arronzhu.rpc.core.consumer;

import com.arronzhu.rpc.core.exception.RPCRemoteCallException;
import com.arronzhu.rpc.core.exception.ServiceIDIllegalException;
import com.arronzhu.rpc.core.exception.ServiceNotFoundException;

import java.util.List;

/**
 * @author arronzhu
 * @date 2018/10/22
 * @description
 */
public interface IConsumer {
    Object serviceConsumer(String methodName, List<Object> params) throws ServiceIDIllegalException, ServiceNotFoundException, RPCRemoteCallException;
}
