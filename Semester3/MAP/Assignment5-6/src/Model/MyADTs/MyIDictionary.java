package Model.MyADTs;

import Implemented_Exceptions.ADTException;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface MyIDictionary<K,V> {
    boolean isDefined(K key);
    void put(K key, V value);
    V lookUp(K key) throws ADTException;
    void update(K key, V value) throws ADTException;
    Collection<V> values();
    void remove(K key) throws ADTException;
    Set<K> keySet();
    Map<K, V> getContent();
    MyIDictionary<K, V> deepCopy() throws ADTException;

}
