package Model.Expressions;

import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIHeap;
import Model.Values.Value;

public class ValueExp implements Expression{
    Value value;

    public ValueExp(Value value) {
        this.value = value;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> symTable, MyIHeap heap) {
        return this.value;
    }

    @Override
    public Expression deepCopy() {
        return new ValueExp(value);
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
