package com.arronzhu.rpc.core.provider;

import com.arronzhu.rpc.core.Constant;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author arronzhu
 * @date 2018/10/23
 * @description
 */
public class ServerThread implements Runnable {
    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Constant.PORT);
            System.out.println("已经开始监听,可以注册服务了");
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ServerProcessThread(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
