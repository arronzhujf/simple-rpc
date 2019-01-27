package com.arronzhu.rpc.core.proxy;

import com.arronzhu.rpc.core.Constant;
import com.arronzhu.rpc.core.consumer.ConsumerServiceImpl;
import com.arronzhu.rpc.core.consumer.IConsumerService;
import com.arronzhu.rpc.core.entity.RequestDO;
import com.arronzhu.rpc.core.exception.RPCRemoteCallException;
import com.arronzhu.rpc.core.exception.ServiceIDIllegalException;
import com.arronzhu.rpc.core.exception.ServiceNotFoundException;
import com.google.common.collect.Lists;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author arronzhu
 * @date 2018/11/1
 * @description
 */
public class ConsumerInvocationHandler implements InvocationHandler {

    private String interfaceName;

    private String version;

    private IConsumerService consumerService;

    public ConsumerInvocationHandler (String interfaceName, String version) {
        this.interfaceName = interfaceName;
        this.version = version;
        consumerService = new ConsumerServiceImpl();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //方法的名称
        String methodName = method.getName();
        //方法的返回类型
        Class returnType = method.getReturnType();

        //若服务唯一标识没有提供,则抛出异常
        if (interfaceName == null || interfaceName.length() == 0
                || version == null || version.length() == 0)
            throw new ServiceIDIllegalException();
        //step1. 根据服务的唯一标识获取该服务的ip地址列表
        String serviceID = interfaceName + "_" + version;
        Set<String> ips = consumerService.getServiceIPsByID(serviceID);
        if (ips == null || ips.size() == 0)
            throw new ServiceNotFoundException();

        //step2. 路由,获取该服务的地址,路由的结果会返回至少一个地址,所以这里不需要抛出异常
        String serviceAddr = consumerService.getIP(serviceID, methodName, Lists.newArrayList(args), ips);

        //step3. 根据传入的参数,拼装Request对象,这里一定会返回一个合法的request对象,所以不需要抛出异常
        RequestDO requestDO = consumerService.getRequestDO(interfaceName, version, methodName, Lists.newArrayList(args));

        //step3. 传入Request对象,序列化并传入服务端,拿到响应后,反序列化为object对象
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
