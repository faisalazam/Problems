package com.algorithms.ctci.normal.stacksandqueues;

import java.util.Stack;

/**
 * Stack Min: How would you design a stack which, in addition to push and pop, has a function min
 * which returns the minimum element? Push, pop and min should all operate in 0 (1) time.
 */
public class StackWithMin extends Stack<Integer> {
    private Stack<Integer> minStack;

    public StackWithMin() {
        minStack = new Stack<>();
    }

    public void push(int value) {
        if (value <= min()) {
            minStack.push(value);
        }
        super.push(value);
    }

    public Integer pop() {
        int value = super.pop();
        if (value == min()) {
            minStack.pop();
        }
        return value;
    }

    public int min() {
        if (minStack.isEmpty()) {
            return Integer.MAX_VALUE;
        } else {
            return minStack.peek();
        }
    }

    /**
     * There's just one issue with this: if we have a large stack,
     * we waste a lot of space by keeping track of the min for every single element.
     * <p>
     * Can we do better? Yes, look at StackWithMin
     */
    private class StackWithMinBad extends Stack<StackWithMinBad.NodeWithMin> {
        public void push(int value) {
            int newMin = Math.min(value, min());
            super.push(new NodeWithMin(value, newMin));
        }

        public int min() {
            if (this.isEmpty()) {
                return Integer.MAX_VALUE; // Error value
            } else {
                return peek().min;
            }
        }

        private class NodeWithMin {
            public int min;
            public int value;

            NodeWithMin(int v, int min) {
                value = v;
                this.min = min;
            }
        }
    }
}
