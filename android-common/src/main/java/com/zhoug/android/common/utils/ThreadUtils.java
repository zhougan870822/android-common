package com.zhoug.android.common.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程工具
 * @Author HK-LJJ
 * @Date 2019/10/14
 * @Description TODO
 */
public class ThreadUtils {
    private static final String TAG = ">>>ThreadUtils";
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    /**
     * debug模式切换
     */
    private static boolean debug = false;
    /**
     * 核心线程数
     */
    private static final int CORE_POOL_SIZE = 3;
    /**
     * 最大线程数
     */
    private static final int MAX_POOL_SIZE = 50;
    /**
     * 非核心线程超时时长(单位秒)
     */
    private static final int KEEP_ALIVE_TIME = 60*2;
    /**
     * 阻塞任务队列
     */
    private static final BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(100);
    /**
     * 线程池
     */
    private static ThreadPoolExecutor executor;

    /**
     * 拒绝策略:丢弃任务
     */
    private static RejectedExecutionHandler rejectedExecutionHandler=new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            Log.e(TAG, "队列已满,丢弃任务:"+r.toString());
//          throw new RejectedExecutionException("队列已满,丢弃任务:"+r.toString());
        }
    };

    private ThreadUtils() {

    }

    static {
        //初始化线程池
        if (executor == null) {
            executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
                    KEEP_ALIVE_TIME, TimeUnit.SECONDS, workQueue,rejectedExecutionHandler);
        }
    }


    /**
     * 设置debug
     *
     * @param debug
     */
    public static void setDebug(boolean debug) {
        ThreadUtils.debug = debug;
    }

    /**
     * 是否为主线程
     * @return
     */
    public static boolean isMainThread(){
        return Thread.currentThread() == Looper.getMainLooper().getThread() || "main".equals(Thread.currentThread().getName());
    }

    /**
     * 在主线程中执行任务
     *
     * @param runnable
     */
    public static void runMainThread(Runnable runnable) {
        if (isMainThread()) {
            if (debug) {
                Log.d(TAG, "runMainThread:当前线程是主线程:" + Thread.currentThread().getName()+",直接执行任务");
            }
            runnable.run();
        } else {
            mainHandler.post(runnable);
        }
    }

    /**
     * 执行任务
     * @param task
     */
    public static void execute(Runnable task){
        executor.execute(task);
    }

    /**
     * 移除队列中等待的任务
     * @param task
     * @return
     */
    public static boolean remove(Runnable task){
        return executor.remove(task);
    }

    /**
     * 获取线程池
     * @return
     */
    public static ThreadPoolExecutor getExecutor() {
        return executor;
    }

}
