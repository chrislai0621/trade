package com.chrislai.onlineTrade.job;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;
@SpringBootTest
public class EntrustedThreadExecutorTest {

    private EntrustedThreadExecutor threadExecutor = new EntrustedThreadExecutor();
    @Test
    public void executeThread() {
        threadExecutor.ExecuteThread(5,50,10,50,100,10);
    }
}