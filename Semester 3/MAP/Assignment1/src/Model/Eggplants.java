package Model;

public class Eggplants implements Vegetables{
    private float weight;
    public Eggplants(float weight){
        this.weight = weight;
    }
    @Override
    public float getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(float newWeight) {
        this.weight = newWeight;
    }

    public String to_Str() {
        return "The eggplant weighs: " + this.weight + " kg.";
    }
}
