package main.a7.Model.ProgramState;

import main.a7.Model.DataStructures.MyDictionary;

import java.util.HashMap;
import java.util.Map;

public class FileTable<K, V> implements MyDictionary<K, V> {
    HashMap<K, V> filesTable;

    public FileTable() {
        filesTable = new HashMap<>();
    }

    @Override
    public V get(K key) {
        return filesTable.getOrDefault(key, null);
    }

    @Override
    public boolean isDefined(K key) {
        return filesTable.containsKey(key);
    }


    @Override
    public void put(K key, V value) {
        filesTable.put(key, value);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (K elem : filesTable.keySet()) {
            stringBuilder.append(elem.toString());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public void update(K key, V value) {
        filesTable.put(key, value);
    }

    @Override
    public void remove(K key) {
        filesTable.remove(key);
    }

    @Override
    public Map<K, V> getContent() {
        return filesTable;
    }

}
