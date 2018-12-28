package cn.com.gyl;

import cn.com.gsl.JedisPool;
import cn.com.gsl.RedisTool;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StockTest {

    private static Integer stockProduct = 10;

    public static void decreaseStock(String traceId) throws InterruptedException {
        if (RedisTool.tryGetDistributedLock(JedisPool.instance(), "stockProduct", traceId, 10)) {
            stockProduct = stockProduct - 1;
            Thread.sleep(1);
            System.out.println("traceId="+traceId+"       ThreadName=" + Thread.currentThread().getName() + ";    stockProduct=" + stockProduct);
            RedisTool.releaseDistributedLock(JedisPool.instance(), "stockProduct", traceId);
        }

    }

//    public void increaseStock() {
//        stockProduct = stockProduct + 200;
//        System.out.println("stockProduct=" + stockProduct);
//    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            String traceId = UUID.randomUUID().toString().replaceAll("-", "");
            newCachedThreadPool.execute(new MyThread(traceId));
        }
        Thread.sleep(1000);
        System.out.println("--------------------------------------------------------");
        System.out.println("ThreadName=" + Thread.currentThread().getName() + "stockProduct=" + stockProduct);
    }
}

class MyThread implements Runnable {

    private String traceId;

    public MyThread(String traceId) {
        this.traceId = traceId;
    }

    @Override
    public void run() {
        try {
            StockTest.decreaseStock(traceId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}
