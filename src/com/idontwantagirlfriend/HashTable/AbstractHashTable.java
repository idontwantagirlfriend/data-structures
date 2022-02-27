package com.idontwantagirlfriend.HashTable;

public abstract class AbstractHashTable<K, V> {
    public abstract void put(K key, V value);

    public abstract V get(K key);

    public abstract V remove(K key);
}
