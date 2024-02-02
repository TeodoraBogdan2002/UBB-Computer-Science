package Model.MyADTs;
import Implemented_Exceptions.ADTException;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T>{
    private Stack<T> stack;

    public MyStack(){
        this.stack=new Stack<T>();
    }
    @Override
    public T pop() throws ADTException {
        //exceptions
        if (stack.isEmpty())
            throw new ADTException("Stack is empty!");
        return this.stack.pop();
    }

    @Override
    public void push(T v) {
        this.stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.empty();
    }

    @Override
    public List<T> getValues() {
        return stack.subList(0,stack.size());
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
