package Model.Expressions;

import Implemented_Exceptions.ExpressionException;
import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIHeap;
import Model.Types.Type;
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
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException {
        return this.value.getType();
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
