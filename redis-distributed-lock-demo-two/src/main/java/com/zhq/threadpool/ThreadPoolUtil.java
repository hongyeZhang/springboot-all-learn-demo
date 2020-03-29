package com.zhq.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : ZHQ
 * @date : 2020/3/29
 */
public final class ThreadPoolUtil {

    private ThreadPoolUtil() {
    }

    public static ExecutorService newThreadPool(int coreSize, int maxSize) {
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(500);
        ThreadFactory threadFactory = new NamedThreadFactory();
        return new ThreadPoolExecutor(coreSize, maxSize, 0, TimeUnit.SECONDS, queue, threadFactory,
                        new ThreadPoolExecutor.AbortPolicy());
    }

}
