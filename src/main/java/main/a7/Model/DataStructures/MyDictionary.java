package main.a7.Model.DataStructures;

import java.util.Map;

public interface MyDictionary<K, V> {
    V get(K key);

    boolean isDefined(K key);


    void put(K key, V value);

    void update(K key, V value);

    void remove(K key);


    Map<K, V> getContent();



}
