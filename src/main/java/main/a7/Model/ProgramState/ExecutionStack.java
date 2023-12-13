package main.a7.Model.ProgramState;

import main.a7.Model.DataStructures.MyStack;

import java.util.*;

public class ExecutionStack<T> implements MyStack<T> {
    Deque<T> stack;

    public ExecutionStack() {
        stack = new ArrayDeque<>();
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder();
        for (T elem : stack) {
            resultString.append(elem.toString());
            resultString.append('\n');
        }
        return resultString.toString();
    }

    @Override
    public T pop() {
        return stack.removeFirst();
    }

    @Override
    public void push(T v) {
        this.stack.addFirst(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

}