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
    private LinkedList<Dog> dogs = new LinkedList<>();
    private LinkedList<Cat> cats = new LinkedList<>();

    public void enqueue(Animal a) {
        a.setOrder(order++);
        if (a instanceof Dog) {
            dogs.addLast((Dog) a);
        } else if (a instanceof Cat) {
            cats.addLast((Cat) a);
        }
    }

    public Animal dequeueAny() {
        if (dogs.isEmpty()) {
            return dequeueCats();
        } else if (cats.isEmpty()) {
            return dequeueDogs();
        }
        Dog dog = dogs.peek();
        Cat cat = cats.peek();
        return dog.isOlderThan(cat) ? dogs.poll() : cats.poll();
    }

    public Animal peek() {
        if (dogs.isEmpty()) {
            return cats.peek();
        } else if (cats.isEmpty()) {
            return dogs.peek();
        }
        Dog dog = dogs.peek();
        Cat cat = cats.peek();
        return dog.isOlderThan(cat) ? dog : cat;
    }

    public int size() {
        return dogs.size() + cats.size();
    }

    private Dog dequeueDogs() {
        return dogs.poll();
    }

    public Dog peekDogs() {
        return dogs.peek();
    }

    private Cat dequeueCats() {
        return cats.poll();
    }

    public Cat peekCats() {
        return cats.peek();
    }

    private abstract class Animal {
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

    private class Cat extends Animal {
        public Cat(String n) {
            super(n);
        }

        public String name() {
            return "Cat: " + name;
        }
    }

    private class Dog extends Animal {
        public Dog(String n) {
            super(n);
        }

        public String name() {
            return "Dog: " + name;
        }
    }
}
