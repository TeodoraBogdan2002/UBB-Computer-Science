package Model;

public class Peppers implements Vegetables {
    public float weight;

    public Peppers(float weight) {
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
        return "The pepper weighs: " + this.weight + " kg.";
    }
}
