package sinliede.utils.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * this class is a map designed for getting value until value is available,
 * note that each key-value pair could only be obtained ONCE
 *
 * @author sinliede
 * @date 19-1-17 下午5:50
 * @desc
 */
public class BlockingHashMap<K, V> extends HashMap<K, V> implements BlockingMap<K, V> {

    //key-thread map stores thread that try to get the value through key
    private Map<K, Thread> map = new ConcurrentHashMap<>();

    private ReentrantLock putLock = new ReentrantLock();

    public BlockingHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public BlockingHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    public BlockingHashMap() {
    }

    public BlockingHashMap(Map<? extends K, ? extends V> m) {
        super(m);
    }

    @Override
    public boolean offer(K k, V v) {
        ReentrantLock putLock = this.putLock;
        V value;
        putLock.lock();
        try {
            value = put(k, v);
            Thread thread = map.get(k);
            if (null != thread) {
                LockSupport.unpark(thread);
            }
        } finally {
            putLock.unlock();
        }
        return value != null;
    }

    @Override
    public V take(K k) {
        ReentrantLock putLock = this.putLock;
        V value;

        Thread thread = Thread.currentThread();
        //every key-value pair could only be obtained once
        if (map.containsKey(k)) {
            return null;
        }
        putLock.lock();
        map.put(k, thread);
        putLock.unlock();
        while (null == (value = get(k))) {
            LockSupport.park(thread);
        }
        putLock.lock();
        remove(k);
        putLock.unlock();
        return value;
    }

    @Override
    public V poll(K k, long timeout, TimeUnit timeUnit) {
        ReentrantLock putLock = this.putLock;
        long nanos = timeUnit.toNanos(timeout);
        if (map.containsKey(k)) {
            return null;
        }
        putLock.lock();
        map.put(k, Thread.currentThread());
        putLock.unlock();
        if (null == get(k)) {
            LockSupport.parkNanos(Thread.currentThread(), nanos);
        }

        putLock.lock();
        V value = get(k);
        remove(k);
        putLock.unlock();
        return value;
    }
}
