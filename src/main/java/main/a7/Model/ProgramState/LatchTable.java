package main.a7.Model.ProgramState;

import main.a7.Model.DataStructures.MyLatchTable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LatchTable implements MyLatchTable<Integer, Integer> {
    Map<Integer, Integer> latchTable;
    Lock lock;
    int freeLocation;

    public LatchTable() {
        latchTable = new HashMap<>();
        lock = new ReentrantLock();
        freeLocation = 1;
    }

    @Override
    public Integer get(Integer key) {
        lock.lock();
        try {
            return latchTable.getOrDefault(key, null);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isDefined(Integer key) {
        lock.lock();
        try {
            return latchTable.containsKey(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int put(Integer value) {
        lock.lock();
        try {
            latchTable.put(freeLocation, value);
            freeLocation += 1;
            return freeLocation - 1;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void update(Integer key, Integer value) {
        lock.lock();
        try {
            latchTable.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Map<Integer, Integer> getContent() {
        lock.lock();
        try {
            return latchTable;
        } finally {
            lock.unlock();
        }
    }
}
