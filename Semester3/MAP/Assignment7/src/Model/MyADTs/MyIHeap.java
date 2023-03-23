package Model.MyADTs;

import Implemented_Exceptions.InterpreterException;
import Model.Values.Value;

import java.util.HashMap;
import java.util.Set;

public interface MyIHeap {
    int getFreeValue();
    HashMap<Integer, Value> getContent();
    void setContent(HashMap<Integer,Value> newMap);
    int add(Value value);
    void update(Integer position, Value value)throws InterpreterException;
    Value get(Integer position) throws InterpreterException;
    boolean containsKey(Integer position);
    void remove(Integer key) throws InterpreterException;
    Set<Integer> keySet();

}
