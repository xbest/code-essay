package com.github.xbest.serialprint;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Created by link on 2017/7/6.
 */
public class SerialPrintSemaphoreTest {
    public static void main(String[] args) throws InterruptedException {

        Semaphore aSemaphore = new Semaphore(1);
        Semaphore bSemaphore = new Semaphore(1);
        Semaphore cSemaphore = new Semaphore(1);

        aSemaphore.acquire();
        bSemaphore.acquire();
        cSemaphore.acquire();

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(new SerialPrintSemaphore(aSemaphore, bSemaphore, "A"));
        executorService.submit(new SerialPrintSemaphore(bSemaphore, cSemaphore, "B"));
        executorService.submit(new SerialPrintSemaphore(cSemaphore, aSemaphore, "C"));
        executorService.shutdown();

        aSemaphore.release();

    }
}
