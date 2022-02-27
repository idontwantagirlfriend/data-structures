package com.idontwantagirlfriend.HashTable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestHashTable {
    private HashTable<String, Integer> table;

    @BeforeEach
    public void refreshHashTable() {
        table = new HashTable<>();
    }

    @Test
    public void putOne() {
        table.put("a", 1);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "", "=%%-+#", "1048576"})
    public void putOneGetOne(String key) {
        table.put(key, 1);
        assertEquals(1, table.get(key));
    }

    @Test
    public void putTwoDifferentGetTwo() {
        table.put("a", 1);
        table.put("b", 2);
        assertEquals(1, table.get("a"));
        assertEquals(2, table.get("b"));
    }

    @Test
    public void putTwoIdenticalKeysAndGet_ShouldReturnTheLastStoredValue() {
        table.put("a", 1);
        table.put("a", 128);
        assertEquals(128, table.get("a"));
    }

    @Test
    public void getANonExistentKey_ShouldReturnNull() {
        assertNull(table.get("mystery"));
    }

    @Test
    public void putOneAndRemove_ShouldReturnTheRemovedValueAndDeleteTheEntry() {
        table.put("a", 127);
        assertEquals(127, table.remove("a"));
        assertNull(table.get("a"));
    }

    @Test
    public void RemoveANonExistentOne_ShouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> table.remove("mystery"));
    }
}
