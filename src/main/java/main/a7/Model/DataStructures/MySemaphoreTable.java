package main.a7.Model.DataStructures;

import java.util.Map;

public interface MySemaphoreTable<K, V> {
    V get(K key);

    boolean isDefined(K key);
    int put(V value);
    Map<K, V> getContent();
}
