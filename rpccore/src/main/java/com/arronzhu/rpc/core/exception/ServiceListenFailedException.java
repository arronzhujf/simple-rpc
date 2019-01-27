package com.arronzhu.rpc.core.exception;

/**
 * @author arronzhu
 * @date 2018/10/23
 * @description
 */
public class ServiceListenFailedException extends Exception {
    public ServiceListenFailedException() {
        super();
    }

    public ServiceListenFailedException(String message) {
        super(message);
    }
}
