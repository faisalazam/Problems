package com.algorithms.ctci.normal.stacksandqueues;

import java.util.LinkedList;

/**
 * Animal Shelter: An animal shelter, which holds only dogs and cats, operates on a strictly "first in, first out" basis.
 * People must adopt either the "oldest" (based on arrival time) of all animals at the shelter,
 * or they can select whether they would prefer a dog or a cat (and will receive the oldest animal of that type).
 * They cannot select which specific animal they would like. Create the data structures to maintain this system
 * and implement operations such as enqueue, dequeueAny, dequeueDog, and dequeueCat.
 * You may use the built-in LinkedList data structure.
 */
public class AnimalQueue {
    private int order = 0;
    private final LinkedList<Dog> dogsQueue = new LinkedList<>();
    private final LinkedList<Cat> catsQueue = new LinkedList<>();

    public void enqueue(Animal a) {
        a.setOrder(order++);
        if (a instanceof Dog) {
            dogsQueue.addLast((Dog) a);
        } else if (a instanceof Cat) {
            catsQueue.addLast((Cat) a);
        }
    }

    public Animal dequeueAny() {
        if (dogsQueue.isEmpty()) {
            return dequeueCats();
        } else if (catsQueue.isEmpty()) {
            return dequeueDogs();
        }
        final Dog dog = dogsQueue.peek();
        final Cat cat = catsQueue.peek();
        return dog.isOlderThan(cat) ? dogsQueue.poll() : catsQueue.poll();
    }

    public Animal peek() {
        if (dogsQueue.isEmpty()) {
            return catsQueue.peek();
        } else if (catsQueue.isEmpty()) {
            return dogsQueue.peek();
        }
        final Dog dog = dogsQueue.peek();
        final Cat cat = catsQueue.peek();
        return dog.isOlderThan(cat) ? dog : cat;
    }

    public int size() {
        return dogsQueue.size() + catsQueue.size();
    }

    private Dog dequeueDogs() {
        return dogsQueue.poll();
    }

    public Dog peekDogs() {
        return dogsQueue.peek();
    }

    private Cat dequeueCats() {
        return catsQueue.poll();
    }

    public Cat peekCats() {
        return catsQueue.peek();
    }

    private abstract static class Animal {
        private int order;
        String name;

        Animal(String n) {
            name = n;
        }

        public abstract String name();

        public void setOrder(int ord) {
            order = ord;
        }

        public int getOrder() {
            return order;
        }

        boolean isOlderThan(Animal a) {
            return this.order < a.getOrder();
        }
    }

    private static class Cat extends Animal {
        public Cat(String n) {
            super(n);
        }

        public String name() {
            return "Cat: " + name;
        }
    }

    private static class Dog extends Animal {
        public Dog(String n) {
            super(n);
        }

        public String name() {
            return "Dog: " + name;
        }
    }
}
