package com.chrislai.onlineTrade.job;

import java.util.concurrent.ThreadFactory;

class BaseThreadFactory implements ThreadFactory {

    // 創建Thread Factory統一此Thread Pool的Thread都在同個Group

    ThreadGroup tg = new ThreadGroup("Example Group");

    @Override
    public Thread newThread(Runnable r) {
        System.out.println("Create Thread");
        return new Thread(tg, r, "thread-%d");
    }
}