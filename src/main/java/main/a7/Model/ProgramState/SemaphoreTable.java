package main.a7.Model.ProgramState;

import javafx.util.Pair;
import main.a7.Model.DataStructures.MySemaphoreTable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SemaphoreTable implements MySemaphoreTable<Integer, Pair<Integer, List<Integer>>> {
    Lock lock = new ReentrantLock();
    int freeLocation = 0;
    private final Map<Integer, Pair<Integer, List<Integer>>> semaphoreTable;
    public SemaphoreTable() {
        semaphoreTable = new java.util.HashMap<>();
    }

    public SemaphoreTable(Map<Integer, Pair<Integer, List<Integer>>> semaphoreTable) {
        this.semaphoreTable = semaphoreTable;
    }

    @Override
    public Pair<Integer, List<Integer>> get(Integer key) {
        lock.lock();
        try {
            return semaphoreTable.getOrDefault(key, null);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isDefined(Integer key) {
        lock.lock();
        try {
            return semaphoreTable.containsKey(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int put(Pair<Integer, List<Integer>> value) {
        lock.lock();
        try {
            semaphoreTable.put(freeLocation, value);
            freeLocation++;
        } finally {
            lock.unlock();
        }
        return freeLocation - 1;
    }

    @Override
    public Map<Integer, Pair<Integer, List<Integer>>> getContent() {
        lock.lock();
        try {
            return semaphoreTable;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder();
        for (Integer key : semaphoreTable.keySet()) {
            resultString.append(key);
            resultString.append(" --> ");
            resultString.append(semaphoreTable.get(key).toString());
            resultString.append('\n');
        }
        return resultString.toString();
    }
}
