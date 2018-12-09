import java.util.*;

/**
 * Your implementation of HashMap.
 * 
 * @author Ashwini Iyer
 * @version 1.0
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {

    // Do not make any new instance variables.
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        size = 0;
        table = new MapEntry[initialCapacity];
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("key or value is null");
        }
        V keyValue = null; //if not already in map
        MapEntry<K, V> currentEntry = new MapEntry(key, value, null);
        int index = Math.abs(key.hashCode()) % table.length;
        double loadFactor = (size + 1) / (double) table.length;
        if (loadFactor > MAX_LOAD_FACTOR) {
            int newLength = (2 * table.length) + 1;
            resizeBackingTable(newLength);
        }
        if (table[index] == null) {
            table[index] = currentEntry;
            size++;
        } else {
            MapEntry<K, V> temp = table[index];
            boolean proceed = true;
            while (temp != null && proceed) {
                if (temp.getKey().equals(key)) {
                    keyValue = temp.getValue();
                    temp.setValue(value);
                    proceed = false;
                }
                temp = temp.getNext();
            }
            if (proceed) {
                currentEntry.setNext(table[index]);
                table[index] = currentEntry;
                size++;
            }
        }
        return keyValue;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        boolean containsKey = false;
        V keyValue = null;
        if (size != 0) {
            int index = Math.abs(key.hashCode()) % table.length;
            MapEntry<K, V> temp = null;
            MapEntry<K, V> prevTemp = table[index];
            if (table[index].getKey().equals(key)) {
                keyValue = table[index].getValue();
                containsKey = true;
                if (table[index].getNext() != null) {
                    temp = table[index].getNext();
                    table[index] = temp;
                } else {
                    table[index] = null;
                }
                size--;
            } else {
                if (table[index].getNext() != null) {
                    temp = table[index].getNext();
                    boolean proceed = true;
                    while (temp != null && proceed) {
                        if (temp.getKey().equals(key)) {
                            keyValue = temp.getValue();
                            prevTemp.setNext(temp.getNext());
                            size--;
                            containsKey = true;
                            proceed = false;
                        }
                        prevTemp = temp;
                        temp = temp.getNext();
                    }
                }
            }
        }
        if (!containsKey) {
            throw new NoSuchElementException("key was not in map");
        }
        return keyValue;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        V keyValue = null;
        boolean containsKey = false;
        int index = Math.abs(key.hashCode()) % table.length;
        MapEntry<K, V> temp = table[index];
        boolean proceed = true;
        while (temp != null && proceed) {
            if (temp.getKey().equals(key)) {
                keyValue = temp.getValue();
                containsKey = true;
                proceed = false;
            }
            temp = temp.getNext();
        }
        if (!containsKey) {
            throw new NoSuchElementException("key was not in map");
        }
        return keyValue;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        boolean containsKey = false;
        int index = Math.abs(key.hashCode()) % table.length;
        MapEntry<K, V> temp = table[index];
        boolean proceed = true;
        while (temp != null && proceed) {
            if (temp.getKey().equals(key)) {
                containsKey = true;
                proceed = false;
            }
            temp = temp.getNext();
        }
        return containsKey;
    }

    @Override
    public void clear() {
        //set backing array to empty backing array
        size = 0;
        table = new MapEntry[INITIAL_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> keys = new HashSet<K>();
        for (int i = 0; i < table.length; i++) {
            MapEntry<K, V> currentEntry = table[i];
            if (currentEntry != null) {
                if (currentEntry.getKey() != null) {
                    keys.add(currentEntry.getKey());
                }
                if (currentEntry.getNext() != null) {
                    MapEntry<K, V> temp = currentEntry.getNext();
                    while (temp != null) {
                        keys.add(temp.getKey());
                        temp = temp.getNext();
                    }
                }
            }
        }
        return keys;
    }

    @Override
    public List<V> values() {
        ArrayList<V> values = new ArrayList<V>();
        for (int i = 0; i < table.length; i++) {
            MapEntry<K, V> currentEntry = table[i];
            if (currentEntry != null) {
                if (currentEntry.getValue() != null) {
                    values.add(currentEntry.getValue());
                }
                if (currentEntry.getNext() != null) {
                    MapEntry<K, V> temp = currentEntry.getNext();
                    while (temp != null) {
                        values.add(temp.getValue());
                        temp = temp.getNext();
                    }
                }
            }
        }
        return values;
    }

    @Override
    public void resizeBackingTable(int length) {
        if (length <= 0 || length < size) {
            throw new IllegalArgumentException("length is neg/0 or < size");
        }
        List<V> val = values();
        MapEntry<K, V>[] tempTable = new MapEntry[length];
        for (int i = 0; i < table.length; i++) {
            MapEntry<K, V> currentEntry = table[i];
            if (currentEntry != null) {
                K tKey = table[i].getKey();
                V tV = table[i].getValue();
                MapEntry<K, V> tempCurrentEntry = new MapEntry(tKey, tV, null);
                if (currentEntry.getValue() != null) {
                    K keyHashcode = currentEntry.getKey();
                    int index = Math.abs(keyHashcode.hashCode()) % length;
                    if (tempTable[index] == null) {
                        tempTable[index] = tempCurrentEntry;
                    } else {
                        tempCurrentEntry.setNext(tempTable[index]);
                        tempTable[index] = tempCurrentEntry;
                    }

                }
                if (currentEntry.getNext() != null) {
                    MapEntry<K, V> currentEntryNext = currentEntry.getNext();
                    while (currentEntryNext != null) {
                        K cKey = currentEntryNext.getKey();
                        V cV = currentEntryNext.getValue();
                        MapEntry<K, V> tempCEN = new MapEntry(cKey, cV, null);
                        K cIK = currentEntryNext.getKey();
                        int childIndex = Math.abs(cIK.hashCode()) % length;
                        if (tempTable[childIndex] == null) {
                            tempTable[childIndex] = tempCEN;
                        } else {
                            tempCEN.setNext(tempTable[childIndex]);
                            tempTable[childIndex] = tempCEN;
                        }
                        currentEntryNext = currentEntryNext.getNext();
                    }
                }
            }
        }
        table = tempTable;
    }
    
    @Override
    public MapEntry<K, V>[] getTable() {
        // DO NOT EDIT THIS METHOD!
        return table;
    }

}
