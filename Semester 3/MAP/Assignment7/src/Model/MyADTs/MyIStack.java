package Model.MyADTs;

import Implemented_Exceptions.InterpreterException;

import java.util.List;

public interface MyIStack<T> {
    T pop() throws InterpreterException;
    void push(T v);
    boolean isEmptyS();
    List<T> getValues();
}
