package com.arronzhu.rpc.service;

import com.arronzhu.rpc.service.entity.ParamDO;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author arronzhu
 * @date 2018/10/23
 * @description
 */
public interface ICaculator {
    /**
     * 加
     */
     double add(ParamDO paramDO);

}
