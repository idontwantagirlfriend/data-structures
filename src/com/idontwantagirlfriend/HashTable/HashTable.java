package com.idontwantagirlfriend.HashTable;

import com.idontwantagirlfriend.Array.Array;
import com.idontwantagirlfriend.Array.LinearGrowingArray;
import com.idontwantagirlfriend.LinkedList.LinkedList;

import java.util.function.Function;

/**
 * An implementation of hash table with {@code Array} and {@code LinkedList}
 * classes. Does not support resizing. Internally using
 * chaining to avoid collision. <br/>
 * Does not support primitive type as key,
 * as generics don't support them, as this class
 * uses object equality to match keys.
 * @param <K>
 * @param <V>
 */
public class HashTable<K, V> extends AbstractHashTable<K, V>{
    private Array<LinkedList<Entry<K, V>>> items;
    private Function<K, Integer> hashMethod;
    private int capacity;

    /**
     * @param capacity the initial and final capacity, increasing it
     *                 would augment the internal cache so that
     *                 collision would be less likely to happen, but
     *                 obviously taking more space.
     */
    public HashTable(int capacity) {
        this.capacity = capacity;
        items = new LinearGrowingArray<>(capacity);
        for (var i = 0; i < capacity; i++) {
            items.insert(new LinkedList<>());
        }
    }

    /**
     * The internal capacity is default to 10.
     */
    public HashTable() {
        this(10);
    }

    private int hash(K key) {
        return hashMethod == null
            ? key.hashCode() % capacity
            : hashMethod.apply(key) % capacity;
    }

    /**
     * Store a value with a key. Values use keys
     * to uniquely identify themselves, and those with
     * identical keys will overwrite.
     * @param key the unique identifier of {@value}
     * @param value to be stored
     */
    @Override
    public void put(K key, V value) {
        var entry = new Entry<>(key, value);
        put(entry);
    }

    /**
     * Store a key-value pair.
     * @param entry an instance of HashTable.Entry
     */
    public void put(Entry<K, V> entry) {
        var linkedList = findLocation(entry.getKey());
        var i = 0;
        for (var existingEntry: linkedList) {
            if (entry.getKey() == existingEntry.getKey())
                linkedList.removeAt(i);
            i++;
        }
        linkedList.addLast(entry);
    }

    /**
     * Get a value using a key. On keys not found, return null.
     * @param key the unique identifier of value
     * @return the value preserved, or null
     */
    @Override
    public V get(K key) {
        var linkedList = findLocation(key);
        for (var entry: linkedList)
            if (key == entry.getKey())
                return entry.getValue();
        return null;
    }

    /**
     * Remove a key-value pair from the hash table. If no entry
     * can be found, will throw NullPointerException.
     * @param key the unique identifier of entry
     * @return value of the removed entry
     * @throws NullPointerException when the given key isn't
     * present in the hashtable
     */
    @Override
    public V remove(K key) {
        var linkedList = findLocation(key);

        var i = 0;
        for (var entry: linkedList) {
            if (key == entry.getKey())
                return linkedList.removeAt(i).getValue();
            i++;
        }

        throw new NullPointerException(
                "The following key doesn't exist in the current hash table: "
                + key.toString());
    }

    public void setHashMethod(Function<K, Integer> newHashMethod) {
        this.hashMethod = newHashMethod;
    }

    private LinkedList<Entry<K, V>> findLocation(K key) {
        var address = hash(key);

        return items.get(address);
    }

    public static class Entry<K, V>{
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public V get(K key) {
            return null;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}