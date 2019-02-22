package sinliede.utils.concurrent;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author sinliede
 * @date 19-1-17 下午5:31
 */
public interface BlockingMap<K, V> extends Map<K, V> {

    /**
     * add a key-value pair to this map.
     *
     * @param k key
     * @param v value
     * @return false when map is full, true otherwise.
     */
    boolean offer(K k, V v);

    /**
     * take a value and then remove this key-value pair from the map
     * the method will blocked if the map does not contains this key until key-value put into the map.
     *
     * @param k key
     * @return
     * @throws InterruptedException if interrupted while waiting
     */
    V take(K k) throws InterruptedException;

    /**
     * take a value and then remove this key-value pair
     * the method will blocked if the map does not contains this key and wait up to the specified time until
     * given key is available
     *
     * @param k        key
     * @param timeOut  time to wait
     * @param timeUnit time unit
     * @return null if overtime and value if not
     * @throws InterruptedException if interrupted while waiting
     */
    V poll(K k, long timeOut, TimeUnit timeUnit) throws InterruptedException;
}
