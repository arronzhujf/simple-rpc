package com.arronzhu.rpc.service.impl;

import com.arronzhu.rpc.service.ICaculator;
import com.arronzhu.rpc.service.entity.ParamDO;

/**
 * @author arronzhu
 * @date 2018/10/23
 * @description
 */
public class CaculatorImpl implements ICaculator {
    @Override
    public double add(ParamDO paramDO) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return paramDO.getN1().doubleValue() + paramDO.getN2().doubleValue();
    }
}
