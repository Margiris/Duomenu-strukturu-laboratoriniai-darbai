/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab4Burakauskas;

import laborai.studijosktu.HashType;
import laborai.studijosktu.MapADTp;

import java.util.*;

/**
 * @author Margiris
 */
@SuppressWarnings({"WeakerAccess", "unused", "unchecked", "Duplicates"})
public class MapBUR<K, V> implements MapADTp<K, V> {

    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    static final HashType DEFAULT_HASH_TYPE = HashType.DIVISION;
    // Maišos lentelė

    protected Node<K, V>[] table;
    // Lentelėje esančių raktas-reikšmė porų kiekis
    protected int size = 0;
    // Apkrovimo faktorius
    private float loadFactor;
    // Maišos metodas
    private HashType ht;
    //--------------------------------------------------------------------------
    //  Maišos lentelės įvertinimo parametrai
    //--------------------------------------------------------------------------
    // Maksimalus suformuotos maišos lentelės grandinėlės ilgis
    private int maxChainSize = 0;
    // Permaišymų kiekis
    private int rehashesCounter = 0;
    // Paskutinės patalpintos poros grandinėlės indeksas maišos lentelėje
    private int lastUpdatedChain = 0;
    // Lentelės grandinėlių skaičius     
    private int chainsCounter = 0;
    // Einamas poros indeksas maišos lentelėje
    protected int index = 0;

    public MapBUR() {
        this(DEFAULT_HASH_TYPE);
    }

    public MapBUR(HashType ht) {
        this(DEFAULT_INITIAL_CAPACITY, ht);
    }

    public MapBUR(int initialCapacity, HashType ht) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR, ht);
    }

    public MapBUR(float loadFactor, HashType ht) {
        this(DEFAULT_INITIAL_CAPACITY, loadFactor, ht);
    }

    public MapBUR(int initialCapacity, float loadFactor, HashType ht) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }

        if ((loadFactor <= 0.0) || (loadFactor > 1.0)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }

        this.table = new Node[initialCapacity];
        this.loadFactor = loadFactor;
        this.ht = ht;
    }

    @Override
    public int getMaxChainSize() {
        return 1;
    }

    @Override
    public int getRehashesCounter() {
        return rehashesCounter;
    }

    @Override
    public int getTableCapacity() {
        return table.length;
    }

    @Override
    public int getLastUpdatedChain() {
        return index;
    }

    @Override
    public int getChainsCounter() {
        return size;
    }

    @Override
    public boolean containsValue(Object value) {
        V val = (V) value;
        for (Node<K, V> aTable : table) {
            if (aTable == null)
                continue;
            if (val.equals(aTable.value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (Node<K, V> aTable : table) {
            if (aTable == null)
                continue;
            set.add(aTable.key);
        }
        return set;
    }

    @Override
    public float averageChainLength() {
        float sum = 0;
        for (Node<K, V> aTable : table) {
            if (aTable == null)
                continue;
            sum += 1;
        }
        return sum / size;
    }

    @Override
    public V putIfAbsent(K key, V value) {
        V prevVal = this.get(key);
        if (prevVal == null) {
            put(key, value);
        }
        return prevVal;
    }

    @Override
    public List<V> values() {
        List<V> list = new ArrayList<>();
        for (Node<K, V> aTable : table) {
            if (aTable == null)
                continue;
            list.add(aTable.value);
        }
        return list;
    }

    @Override
    public int numberOfEmpties() {
        int count = 0;
        for (Node<K, V> aTable : table)
            if (aTable == null)
                count++;
        return count;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
        index = 0;
        lastUpdatedChain = 0;
        maxChainSize = 0;
        rehashesCounter = 0;
        chainsCounter = 0;
    }

    @Override
    public String[][] toArray() {
        String[][] result = new String[table.length][];
        int count = 0;
        for (Node<K, V> n : table) {
            String[] list = new String[getMaxChainSize()];
            list[0] = n.toString();
            result[count] = list;
            count++;
        }
        return result;
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or value is null in put(Key key, Value value)");
        }
        index = hash(key, ht);

        Node<K, V> node = table[index];
        if (node == null) {
            table[index] = new Node<>(key, value);
            size++;

            if (size > table.length * loadFactor) {
                rehash(table[index]);
            }
        } else if (key == table[index].key) {
            table[index].value = value;
        } else {
            int idx = index;
            int i = 0;
            int loopel = -1;
            int counter = 0;
            while (table[idx] != null) {
                int h2 = (Math.abs(key.hashCode()) + counter);
                idx = Math.abs((index + i * h2) % table.length);
                if (loopel == -1)
                    loopel = idx;
                else if (loopel == idx)
                    counter++;
                if (table[idx] == null) {
                    table[idx] = new Node<>(key, value);
                    size++;
                    if (size > table.length * loadFactor) {
                        rehash(table[idx]);
                    }
                    break;
                }
                i++;
            }
        }

        return value;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null in get(Key key)");
        }

        for (Node<K, V> n : table) {
            if (n != null)
                if (n.key.toString().equals(key.toString()))
                    return n.value;
        }
        return null;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null in remove(Key key)");
        }

        index = hash(key, ht);
        Node<K, V> previous;
        if ((table[index].key).equals(key)) {
            size--;
            previous = table[index];
            table[index] = null;
            return previous.value;
        }
        return null;
    }

    @Override
    public boolean contains(K key) {
        return get(key) != null;
    }

    private void rehash(Node<K, V> node) {
        MapBUR mapBUR
                = new MapBUR(table.length * 2, loadFactor, ht);
        for (int i = 0; i < table.length; i++) {
            while (table[i] != null) {
                if (table[i].equals(node)) {
                    lastUpdatedChain = i;
                }
                mapBUR.put(table[i].key, table[i].value);
                table[i] = null;
            }
        }
        table = mapBUR.table;
        maxChainSize = mapBUR.maxChainSize;
        chainsCounter = mapBUR.chainsCounter;
        rehashesCounter++;
    }

    private int hash(K key, HashType hashType) {
        int h = key.hashCode();
        switch (hashType) {
            case DIVISION:
                return Math.abs(h) % table.length;
            case MULTIPLICATION:
                double k = (Math.sqrt(5) - 1) / 2;
                return (int) (((k * Math.abs(h)) % 1) * table.length);
            case JCF7:
                h ^= (h >>> 20) ^ (h >>> 12);
                h = h ^ (h >>> 7) ^ (h >>> 4);
                return h & (table.length - 1);
            case JCF8:
                h = h ^ (h >>> 16);
                return h & (table.length - 1);
            default:
                return Math.abs(h) % table.length;
        }
    }

    @SuppressWarnings("TypeParameterHidesVisibleType")
    protected class Node<K, V> {

        // Raktas        
        protected K key;
        // Reikšmė
        protected V value;
        // Rodyklė į sekantį grandinėlės mazgą

        protected Node() {
        }

        protected Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

}
