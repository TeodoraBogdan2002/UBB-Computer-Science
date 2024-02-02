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
    Map<K, V> getContent();

    Set<Map.Entry<K, V>> entrySet();
    Set<K> keySet();

}
