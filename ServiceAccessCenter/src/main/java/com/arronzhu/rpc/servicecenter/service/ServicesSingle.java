package com.arronzhu.rpc.servicecenter.service;

import com.arronzhu.rpc.servicecenter.entity.ServiceInfoDO;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author arronzhu
 * @date 2018/10/22
 * @description
 */
@Data
public class ServicesSingle {
    private static final ServicesSingle INSTANCE = new ServicesSingle();
    private Map<String, ServiceInfoDO> services;
    private ServicesSingle() {
        services = new ConcurrentHashMap<>();
    }

    public static ServicesSingle getInstance() {
        return INSTANCE;
    }
}
