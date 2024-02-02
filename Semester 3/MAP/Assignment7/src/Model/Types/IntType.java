package Model.Types;
import Model.Values.IntValue;
import Model.Values.Value;

public class IntType implements Type{
    @Override
    public boolean equals(Type second){
        return second instanceof IntType;
    }

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }

    @Override
    public Type deepCopy() {
        return new IntType();
    }

    @Override
    public String toString(){
        return "int ";
    }
}
