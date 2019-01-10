package sinliede.utils.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * a thread factory used for ThreadPoolExecutor{@link java.util.concurrent.ThreadPoolExecutor}
 * this class could automatically upgrade the thread number it generates for threadPoolExecutor
 * according to how many workers the threadPoolExecutor currently has.
 * <pre>{@code
 *     CountableThreadFactory threadFactory = new CountableThreadFactory(Thread.currentThread().getThreadGroup(),"child-thread");
 *     ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 100, 60L,
 *           TimeUnit.SECONDS, new LinkedBlockingQueue<>(), threadFactory);
 *     threadFactory.setThreadPoolExecutor(threadPoolExecutor);
 * }</pre>
 *
 * @author sinliede
 * @date 19-1-9 上午11:50
 * @see java.util.concurrent.ThreadFactory
 */
public class CountableThreadFactory implements ThreadFactory {

    // the ThreadPoolExecutor using this factory
    private ThreadPoolExecutor threadPoolExecutor;

    //the ThreadGroup that all threads this factory manufactured belong
    private ThreadGroup threadGroup;

    private String namePrefix;

    public CountableThreadFactory(String namePrefix, ThreadGroup threadGroup) {
        this.namePrefix = namePrefix;
        this.threadGroup = threadGroup;
    }

    public CountableThreadFactory(String namePrefix, ThreadGroup threadGroup, ThreadPoolExecutor threadPoolExecutor) {
        this.namePrefix = namePrefix;
        this.threadGroup = threadGroup;
        this.threadPoolExecutor = threadPoolExecutor; }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(threadGroup, r, namePrefix + (threadPoolExecutor.getActiveCount() + 1));

        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }

    public CountableThreadFactory setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
        return this;
    }

    public CountableThreadFactory setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
        return this;
    }
}
