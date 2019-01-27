package com.arronzhu.rpc.service.factory;

import com.arronzhu.rpc.core.proxy.ConsumerInvocationHandler;
import com.arronzhu.rpc.service.ICaculator;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author arronzhu
 * @date 2018/11/1
 * @description
 */
@Component
class CaculatorFactoryBean implements FactoryBean<ICaculator> {
    @Override
    public ICaculator getObject() throws Exception {
        InvocationHandler handler = new ConsumerInvocationHandler(ICaculator.class.getCanonicalName(), "1.0");
        Class<?>[] interfaces = {ICaculator.class};
        ICaculator caculator = (ICaculator) Proxy.newProxyInstance(
                ICaculator.class.getClassLoader(), interfaces, handler);
        return caculator;
    }

    @Override
    public Class<?> getObjectType() {
        return ICaculator.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}

