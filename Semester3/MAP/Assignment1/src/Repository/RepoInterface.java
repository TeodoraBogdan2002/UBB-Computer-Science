package Repository;

import Model.Vegetables;


public interface RepoInterface {
    void add(Vegetables vegetable);
    void delete(int index);
    Vegetables[] getVegetables();
    int getSize();
}
