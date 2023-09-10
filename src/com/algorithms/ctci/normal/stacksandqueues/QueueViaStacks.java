package com.algorithms.ctci.normal.stacksandqueues;

import java.util.Stack;

/**
 * Queue via Stacks: Implement a QueueViaStacks class which implements a queue using two stacks.
 */
public class QueueViaStacks<T> {
    private Stack<T> stackNewest, stackOldest;

    public QueueViaStacks() {
        stackNewest = new Stack<T>();
        stackOldest = new Stack<T>();
    }

    public int size() {
        return stackNewest.size() + stackOldest.size();
    }

    public void add(T value) {
        stackNewest.push(value);
    }

    public T peek() {
        shiftStacks();
        return stackOldest.peek(); // retrieve the oldest item.
    }

    public T remove() {
        shiftStacks();
        return stackOldest.pop(); // pop the oldest item.
    }

    /**
     * Move elements from stackNewest into stackOldest.
     * This is usually done so that we can do operations on stackOldest.
     */
    private void shiftStacks() {
        if (stackOldest.isEmpty()) {
            while (!stackNewest.isEmpty()) {
                stackOldest.push(stackNewest.pop());
            }
        }
    }
}
