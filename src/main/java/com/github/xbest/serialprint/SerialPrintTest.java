package com.github.xbest.serialprint;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by link on 2017/7/5.
 */
public class SerialPrintTest {
    public static void main(String[] args) throws InterruptedException {

        int printTime = 10;

        Lock lock = new ReentrantLock();

        Condition aCondition = lock.newCondition();
        Condition bCondition = lock.newCondition();
        Condition cCondition = lock.newCondition();

        Thread aThread = new Thread(new SerialPrintABC(aCondition, bCondition, "A", lock, printTime));
        Thread bThread = new Thread(new SerialPrintABC(bCondition, cCondition, "B", lock, printTime));
        Thread cThread = new Thread(new SerialPrintABC(cCondition, aCondition, "C", lock, printTime));

        bThread.start();
        aThread.start();
        cThread.start();

        TimeUnit.SECONDS.sleep(1);

        lock.lock();
        aCondition.signal();
        lock.unlock();

    }
}
