package com.github.xbest.serialprint;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by link on 2017/7/5.
 */
public class SerialPrintTest {
    public static void main(String[] args) throws InterruptedException {

        Lock lock = new ReentrantLock();
        Condition aCondition = lock.newCondition();
        Condition bCondition = lock.newCondition();
        Condition cCondition = lock.newCondition();

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(new SerialPrintABC(aCondition, bCondition, "A", lock));
        executorService.submit(new SerialPrintABC(bCondition, cCondition, "B", lock));
        executorService.submit(new SerialPrintABC(cCondition, aCondition, "C", lock));
        executorService.shutdown();

        // 休眠一秒，防止还没有await就signal了，就丢了。
        TimeUnit.SECONDS.sleep(1);

        lock.lock();
        aCondition.signal();
        lock.unlock();

    }
}
