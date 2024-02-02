package Model.MyADTs;
import Implemented_Exceptions.ADTException;

import java.util.List;

public interface MyIList<T> {
    void add(T elem);
    T pop() throws ADTException;
    void remove(T elem);
    boolean isEmpty();
    List<T> getList();

    T get(int index);

    int getSize();

}
