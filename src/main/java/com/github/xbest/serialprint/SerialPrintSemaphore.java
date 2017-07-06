package com.github.xbest.serialprint;

import java.util.concurrent.Semaphore;

/**
 * Created by link on 2017/7/6.
 */
public class SerialPrintSemaphore implements Runnable {
    private Semaphore prev;
    private Semaphore next;
    private String letter;
    private final int time = 10;

    public SerialPrintSemaphore(Semaphore prev, Semaphore next, String letter) {
        this.prev = prev;
        this.next = next;
        this.letter = letter;
    }

    public void run() {

        for (int i = 0; i < time; i++) {
            try {
                prev.acquire();
                System.out.print(letter);
                next.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
