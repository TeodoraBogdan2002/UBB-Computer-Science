package Model.MyADTs;

import Implemented_Exceptions.ADTException;

import java.util.List;

public interface MyIStack<T> {
    T pop() throws ADTException;
    void push(T v);
    boolean isEmpty();
    List<T> getValues();
}
