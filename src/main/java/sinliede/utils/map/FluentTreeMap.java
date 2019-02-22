package sinliede.utils.map;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * a treeMap for fluent putting and fluent removing
 *
 * @author  sinliede
 * @create  18-7-23 下午3:16
 * @see java.util.TreeMap
 **/
public class FluentTreeMap<K, V> extends TreeMap<K, V>{

    private static final long serialVersionUID = 325598590985906972L;

    public FluentTreeMap(Map<? extends K, ? extends V> m) {
        super(m);
    }

    public FluentTreeMap() {
    }

    public FluentTreeMap(Comparator<? super K> comparator) {
        super(comparator);
    }

    public FluentTreeMap<K, V> fluentPut(K k, V v){
        this.put(k, v);
        return this;
    }

    public FluentTreeMap<K, V> fluentRemove(K k, V v){
        this.remove(k, v);
        return this;
    }

    public FluentTreeMap<K, V> fluentRemove(K k) {
        this.remove(k);
        return this;
    }
}
