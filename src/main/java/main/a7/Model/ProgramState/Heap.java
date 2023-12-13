package main.a7.Model.ProgramState;

import main.a7.Model.DataStructures.MyHeap;

import java.util.HashMap;
import java.util.Map;

public class Heap<K, V> implements MyHeap<K, V> {

    Map<Integer, V> heapTable;
    int freeLocation;

    public Heap() {
        heapTable = new HashMap<>();
        freeLocation = 1;
    }

    @Override
    public V get(K key) {
        return heapTable.getOrDefault((Integer) key, null);
    }

    @Override
    public boolean isDefined(K key) {
        return heapTable.containsKey((Integer) key);
    }

    @Override
    public int put(V value) {
        heapTable.put(freeLocation, value);
        freeLocation += 1;
        return freeLocation - 1;
    }

    @Override
    public void update(K key, V value) {
        heapTable.put((Integer) key, value);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer elem : heapTable.keySet()) {
            stringBuilder.append(elem.toString());
            stringBuilder.append(" --> ");
            stringBuilder.append(heapTable.get(elem).toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public void setContent(Map<Integer, V> integerValueMap) {
        heapTable = integerValueMap;
    }

    @Override
    public Map<Integer, V> getContent() {
        return heapTable;
    }
}
