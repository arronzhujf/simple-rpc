package com.arronzhu.rpc.service.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author arronzhu
 * @date 2018/10/23
 * @description
 */
@Data
@Builder
public class ParamDO implements Serializable {
    private Number n1;
    private Number n2;
}
