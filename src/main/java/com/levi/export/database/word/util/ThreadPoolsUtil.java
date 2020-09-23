package com.levi.export.database.word.util;

import java.util.concurrent.*;

public class ThreadPoolsUtil {
    private static ExecutorService exec = new ThreadPoolExecutor(
            20,
            300,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r);
                }
            },
            new ThreadPoolExecutor.AbortPolicy());

    /**
     * 线程池调用
     * @param runnable
     */
    public static void exec(Runnable runnable){
        exec.execute(runnable);
    }
}
