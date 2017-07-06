package com.github.xbest.serialprint;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by link on 2017/7/6.
 */
public class SerialPrintCyclicBarrierTest {
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier aCyclicBarrier = new CyclicBarrier(2);
        CyclicBarrier bCyclicBarrier = new CyclicBarrier(2);
        CyclicBarrier cCyclicBarrier = new CyclicBarrier(2);

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(new SerialPrintCyclicBarrier(aCyclicBarrier, bCyclicBarrier, "A"));
        executorService.submit(new SerialPrintCyclicBarrier(bCyclicBarrier, cCyclicBarrier, "B"));
        executorService.submit(new SerialPrintCyclicBarrier(cCyclicBarrier, aCyclicBarrier, "C"));
        executorService.shutdown();

        aCyclicBarrier.await();

    }
}
