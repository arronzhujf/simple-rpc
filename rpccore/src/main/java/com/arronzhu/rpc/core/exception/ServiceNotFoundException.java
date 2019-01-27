package com.arronzhu.rpc.core.exception;

/**
 * @author arronzhu
 * @date 2018/10/23
 * @description
 */
public class ServiceNotFoundException extends Exception {
    public ServiceNotFoundException() {
        super();
    }

    public ServiceNotFoundException(String message) {
        super(message);
    }
}
