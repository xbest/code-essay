package com.github.xbest.serialprint;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 顺序打印 ABC N次
 * Created by link on 2017/7/5.
 */
public class SerialPrintABC implements Runnable {
    private Condition prev;
    private Condition next;
    private String letter;
    private Lock lock;
    private int time;

    public SerialPrintABC(Condition prev, Condition next, String letter, Lock lock, int time) {
        this.prev = prev;
        this.next = next;
        this.letter = letter;
        this.lock = lock;
        this.time = time;
    }

    public void run() {
        for (int i = 0; i < time; i++) {
            try {
                lock.lock();
                prev.await();
                System.out.print(letter);
                next.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
