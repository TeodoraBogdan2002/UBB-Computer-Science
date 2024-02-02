package Controller;
import Exceptions.MyException;
import Model.Eggplants;
import Model.Peppers;
import Model.Tomatoes;
import Repository.*;
import Model.Vegetables;

import java.util.Objects;
/*
5. Intr-o sera se cultiva rosii, ardei si vinete.
Sa se afiseze toate legumele cu greutatea mai mare
de 0.2 kg.
 */

public class Controller {
    public Repository repo=new Repository();

    public Controller(Repository repo) {
        this.repo=repo;
    }

    public void add(String type, float weight) throws MyException {

        if(Objects.equals(type, "tomato")){
            Tomatoes tomato = new Tomatoes(weight);
            this.repo.add(tomato);
        }
        if(Objects.equals(type, "pepper")){
            Peppers peppers = new Peppers(weight);
            this.repo.add(peppers);
        }
        if(Objects.equals(type, "eggplant")){
            Eggplants eggplants = new Eggplants(weight);
            this.repo.add(eggplants);
        }
    }

    public void delete(int index) {
        this.repo.delete(index);
    }

    public Vegetables[] getVbyWeight(){
        Vegetables[] copy = new Vegetables[this.repo.getSize()];
        int size=0;
        for(Vegetables vegetable : this.repo.getVegetables()){
            if(vegetable!=null && vegetable.getWeight()>0.2){
                copy[size++]=vegetable;
            }
        }
        Vegetables[] copyy = new Vegetables[size];
        System.arraycopy(copy,0,copyy,0,size);
        return copyy;
    }
}
