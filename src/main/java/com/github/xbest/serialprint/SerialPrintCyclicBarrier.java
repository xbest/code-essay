package com.github.xbest.serialprint;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by link on 2017/7/6.
 */
public class SerialPrintCyclicBarrier implements Runnable {
    private CyclicBarrier prev;
    private CyclicBarrier next;
    private String letter;
    private final int time = 10;

    public SerialPrintCyclicBarrier(CyclicBarrier prev, CyclicBarrier next, String letter) {
        this.prev = prev;
        this.next = next;
        this.letter = letter;
    }

    public void run() {
        for (int i = 0; i < time; i++) {
            try {
                prev.await();
                System.out.print(letter);
                // 控制最后一次不要再等待，以免阻塞线程，不能退出。
                boolean isAwait = "C".equals(letter) && i == time - 1;
                if (!isAwait) {
                    next.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        }


    }

}
