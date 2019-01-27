package com.arronzhu.rpc.client;

import com.arronzhu.rpc.service.ICaculator;
import com.arronzhu.rpc.service.entity.ParamDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author arronzhu
 * @date 2018/10/23
 * @description
 */
@Component
public class ConsumerTest {

    private static ICaculator caculator;
    @Autowired
    private void setCaculator(ICaculator caculator) {
        ConsumerTest.caculator = caculator;
    }

    public static void add() {
        ParamDO paramDO = ParamDO.builder().n1(1.0).n2(2.0).build();
        try {
            Double res = caculator.add(paramDO);
            System.out.println("add: " + res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
