package main.a7.Model.DataStructures;

import java.util.Deque;
import java.util.List;

public interface MyStack<T> {
    T pop();

    void push(T v);

    boolean isEmpty();

    Deque<T> getContent();
}

