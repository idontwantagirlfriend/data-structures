package com.idontwantagirlfriend.Queue;

import com.idontwantagirlfriend.Array.Array;

import java.util.Iterator;
import java.util.Objects;

/**
 * A priority queue based on {@code Array} class from this
 * package. The element with the largest priority comes
 * out first.
 * @param <T>
 */
public class PriorityQueue<T> implements Iterable<T>{
    private Array<Task> items;

    public PriorityQueue() {
        this.items = new Array<>();
    }

    /**
     * Add the given element to current priority queue.<br/>
     * O(n) time complexity.
     * @param priority an arbitrary value describing
     *                element priority
     * @param element
     */
    public void enqueue(int priority, T element) {
        var task = new Task(priority, element);
        for (var i = 0; i < items.length(); i++) {
            if (items.get(i).priority() > task.priority()) {
                items.insertBefore(i, task);
                return;
            }
        }
        items.insert(task);
    }

    /**
     * Remove an element from the tail of current
     * priority queue.
     * The elements with larger priority are dequeued before
     * those with smaller priority.<br/>
     * O(1) time complexity.
     * @return the removed element
     */
    public T dequeue() {
        if (isEmpty()) throw new IllegalStateException(
                "You were trying to dequeue but current priority queue is empty.");
        return items.removeAt(items.length() - 1).get();
    }

    /**
     * Peek the last element in the current priority queue.<br/>
     * O(1) time complexity.
     * @return the last element
     */
    public T peek() {
        if (isEmpty()) return null;
        return items.get(items.length() - 1).get();
    }

    /**
     * O(1) time complexity.
     * @return if the current priority queue is empty
     */
    public Boolean isEmpty() {
        return items.length() == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new PriorityQueueIterator(items.iterator());
    }

    private final class Task {
        private final int priority;
        private final T task;

        private Task(int priority, T task) {
            this.priority = priority;
            this.task = task;
        }

        public int priority() {
            return priority;
        }

        public T get() {
            return task;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Task) obj;
            return this.priority == that.priority &&
                    Objects.equals(this.task, that.task);
        }

        @Override
        public int hashCode() {
            return Objects.hash(priority, task);
        }

        @Override
        public String toString() {
            return "Task[" +
                    "priority=" + priority + ", " +
                    "task=" + task + ']';
        }
    }

    private class PriorityQueueIterator implements Iterator<T> {
        private Iterator<Task> taskIterator;

        public PriorityQueueIterator(Iterator<Task> taskIterator) {
            this.taskIterator = taskIterator;
        }

        @Override
        public boolean hasNext() {
            return taskIterator.hasNext();
        }

        @Override
        public T next() {
            return taskIterator.next().get();
        }
    }
}
