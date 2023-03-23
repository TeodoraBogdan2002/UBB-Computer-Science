package Repository;

import Model.Vegetables;

public class Repository implements RepoInterface{
    private Vegetables[] vegetables;
    private int nrOfV;

    public Repository()
    {
        this.vegetables = new Vegetables[10];
        this.nrOfV = 0;
    }

    public void add(Vegetables vegies){
        if (this.nrOfV == this.vegetables.length) {
            int i = this.nrOfV * 2;
            Vegetables[] newVegetables = new Vegetables[i];
            System.arraycopy(this.vegetables, 0, newVegetables, 0, this.vegetables.length);
            this.vegetables = newVegetables;
        }
        this.vegetables[nrOfV]=vegies;
        nrOfV++;
    }

    public int getSize()
    {
        return this.nrOfV;
    }

    public Vegetables[] getVegetables()
    {
        return this.vegetables;
    }

    public void delete(int index) {
        Vegetables[] copy = new Vegetables[this.nrOfV - 1];
        for(int ii=0,j=0;ii<this.nrOfV; ii++){
            if (ii!=index)
            {
                copy[j]=this.vegetables[ii];
                j++;
            }
        }
        this.vegetables=copy;
        this.nrOfV--;
    }
}
