package com.wavjaby.github;

import java.util.concurrent.*;

public class MyExecutor extends ThreadPoolExecutor {

    public static MyExecutor newCachedThreadPoolExecutor() {
        return new MyExecutor(
                0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>());
    }

    public static ExecutorService newFixedThreadExecutor(int nThreads) {
        return new MyExecutor(
                nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    public MyExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                      TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        // If submit() method is called instead of execute()
        if (t == null && r instanceof Future<?>) {
            try {
                ((Future<?>) r).get();
            } catch (CancellationException e) {
                t = e;
            } catch (ExecutionException e) {
                t = e.getCause();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (t != null) {
            // Exception occurred
            t.printStackTrace();
        }
    }
}
