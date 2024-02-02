package Model.MyADTs;

import Implemented_Exceptions.ADTException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T>{
    private Stack<T> stack;

    public MyStack(){
        this.stack=new Stack<T>();
    }
    @Override
    public T pop() throws ADTException
    {

        return this.stack.pop();
    }

    @Override
    public void push(T v) {
        this.stack.push(v);
    }

    @Override
    public boolean isEmptyS() {
        return this.stack.isEmpty();
    }

    @Override
    public List<T> getValues() {
        List<T> list = Arrays.asList((T[]) stack.toArray());
        Collections.reverse(list);
        return list;
    }

    @Override
    public String toString() {
        StringBuilder requiredString = new StringBuilder();
        for (T item: stack) {
            requiredString.append(item).append("|");
        }
        return requiredString.toString();
    }
}
