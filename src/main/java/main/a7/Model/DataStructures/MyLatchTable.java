package main.a7.Model.DataStructures;

import java.util.Map;

public interface MyLatchTable<K, V> {
    V get(K key);

    boolean isDefined(K key);

    int put(V value);
    void update(K key, V value);

    Map<K, V> getContent();

}
