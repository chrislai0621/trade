package com.chrislai.onlineTrade.job;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 委託交易排程實作
 */
public class EntrustedThreadExecutor {
//    public static void main(String[] args) {
//        ExecuteThread(5,50,10,50,100,10);
//    }

    /**
     * 執行排程
     * @param corePoolSize 核心線程數
     * @param maxPoolSize 最大線程數
     * @param keepAliveTime 最大線程數可以存活的時間
     * @param capacity workQueue capacity
     * @param sleepTime sleepTime
     * @param taskCount 產生N個任務
     */

    public void ExecuteThread(int corePoolSize,int maxPoolSize,int keepAliveTime,int capacity,int sleepTime,int taskCount)
    {

        ThreadPoolExecutor tpe = new ThreadPoolExecutor(corePoolSize,
                maxPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(capacity),
                new BaseThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());

        // 增加N個任務
        int i=0;
        while(i < taskCount)
        {
            try {
                Thread.sleep(sleepTime);
                tpe.execute(new EntrustedRunnable(i));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
    }
}
