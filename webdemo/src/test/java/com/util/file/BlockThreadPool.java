package com.util.file;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class BlockThreadPool {
    private ThreadPoolExecutor pool;

    public BlockThreadPool(int poolSize) {
        this.pool = new ThreadPoolExecutor(poolSize, poolSize, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(poolSize), new CustomerThreadFactory(), new CustomerReject());
    }

    private class CustomerThreadFactory implements ThreadFactory {
        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            // 线程名字
            t.setName(BlockThreadPool.class.getName() + count.getAndIncrement());
            return t;
        }
    }

    /**
     * AbortPolicy  爆出异常 默认
     * DiscardPolicy 丢弃
     * DiscardOldestPolicy 抛弃队列旧的
     * CallerRunsPolicy 主线程执行
     */

    private class CustomerReject implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                // 不自定义拒绝策略的话 线程池默认时AbortPolicy 会抛出异常
                // put 会等待
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                log.info("...");
            }

        }
    }
}
