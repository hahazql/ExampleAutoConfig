package model.threader;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zql on 2015/10/13.
 */
public class DaemonThreadFactory implements ThreadFactory {
    static final AtomicInteger poolNumber = new AtomicInteger(1);
    final ThreadGroup group;
    final AtomicInteger threadNumber = new AtomicInteger(1);
    final String namePrefix;
    private boolean daemon = true;

    public DaemonThreadFactory() {
        SecurityManager s = System.getSecurityManager();
        this.group = s != null?s.getThreadGroup():Thread.currentThread().getThreadGroup();
        this.namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(this.group, r, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
        t.setDaemon(this.daemon);
        if(t.getPriority() != 5) {
            t.setPriority(5);
        }

        return t;
    }
}
