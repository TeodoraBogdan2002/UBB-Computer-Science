package Model.MyADTs;

import Implemented_Exceptions.InterpreterException;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface MyIDictionary<K,V> {
    boolean isDefined(K key);
    void put(K key, V value);
    V lookUp(K key) throws InterpreterException;
    void update(K key, V value) throws InterpreterException;
    Collection<V> values();
    void remove(K key) throws InterpreterException;
    Set<K> keySet();
    Map<K, V> getContent();
    MyIDictionary<K, V> deepCopy() throws InterpreterException;

}
