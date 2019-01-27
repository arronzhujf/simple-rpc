package com.arronzhu.rpc.core.exception;

/**
 * @author arronzhu
 * @date 2018/10/23
 * @description
 */
public class RPCRemoteCallException extends Exception {
    public RPCRemoteCallException() {
        super();
    }

    public RPCRemoteCallException(String message) {
        super(message);
    }
}
