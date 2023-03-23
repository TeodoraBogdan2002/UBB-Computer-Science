package Model;

public class Tomatoes implements Vegetables {
    private float weight;

    public Tomatoes(float weight){
        this.weight = weight;
    }
    @Override
    public float getWeight(){
        return this.weight;
    }

    @Override
    public void setWeight(float newWeight){
        this.weight=newWeight;

    }

    @Override
    public String toString()
    {
        return "The Tomato weighs: " + this.weight +" kg.";
    }
}
