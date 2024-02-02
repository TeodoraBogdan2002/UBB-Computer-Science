package Model.MyADTs;

import Implemented_Exceptions.ADTException;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T>{
    private List<T> list;

    public MyList(){
        this.list=new ArrayList<T>();
    }

    @Override
    public void add(T elem) {
        this.list.add(elem);
    }

    @Override
    public T pop() throws ADTException {
        if (list.isEmpty())
            throw new ADTException("The list is empty!");
        return this.list.remove(0);
    }

    @Override
    public synchronized void remove(T elem) {
        //exception
        this.list.remove(elem);
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public List<T> getList() {
        return list;
    }


    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public int getSize() {
        return this.list.size();
    }

    @Override
    public String toString(){
        String result = "{";

        for(T el:this.list){
            result+=el.toString()+" ";
        }
        result+="}\n";
        return result.toString();
    }
}
