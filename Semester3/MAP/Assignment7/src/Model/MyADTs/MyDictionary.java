package Model.MyADTs;

import Implemented_Exceptions.InterpreterException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<K,V> implements MyIDictionary<K,V>{
    HashMap<K, V> dictionary;

    public MyDictionary() {
        this.dictionary = new HashMap<>();
    }

    @Override
    public boolean isDefined(K key) {
        return this.dictionary.containsKey(key);
    }

    @Override
    public V lookUp(K key) throws InterpreterException {
        if (!isDefined(key))
            throw new InterpreterException( key + " is not defined.");
        return this.dictionary.get(key);
    }

    @Override
    public void update(K key, V value) throws InterpreterException {
        if (!isDefined(key))
            throw new InterpreterException(key + " is not defined.");
        this.dictionary.put(key, value);
    }

    @Override
    public Collection<V> values() {
        return this.dictionary.values();
    }

    @Override
    public void remove(K key) throws InterpreterException {
        if (!isDefined(key))
            throw new InterpreterException(key + " is not defined.");
        this.dictionary.remove(key);
    }

    @Override
    public Set<K> keySet() {
        return dictionary.keySet();
    }

    @Override
    public Map<K, V> getContent() {
        return dictionary;
    }

    @Override
    public MyIDictionary<K, V> deepCopy() throws InterpreterException {
        MyIDictionary<K, V> toReturn = new MyDictionary<>();
        for (K key: keySet())
            toReturn.put(key, lookUp(key));
        return toReturn;
    }

    @Override
    public String toString() {
        return this.dictionary.toString();
    }

    @Override
    public void put(K key, V value) {
        this.dictionary.put(key, value);
    }

}
