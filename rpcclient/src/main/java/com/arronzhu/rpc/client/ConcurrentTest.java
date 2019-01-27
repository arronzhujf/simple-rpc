package com.arronzhu.rpc.client;

/**
 * @author arronzhu
 * @date 2018/11/2
 * @description
 */
public class ConcurrentTest {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String args[]) {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }
}
