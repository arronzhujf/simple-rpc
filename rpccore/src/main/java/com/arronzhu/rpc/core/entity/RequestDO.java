package com.arronzhu.rpc.core.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author arronzhu
 * @date 2018/10/22
 * @description
 */
@Data
public class RequestDO implements Serializable {
    private String interfaceName;
    private String version;
    private String methodName;
    private List<Object> params;
}
