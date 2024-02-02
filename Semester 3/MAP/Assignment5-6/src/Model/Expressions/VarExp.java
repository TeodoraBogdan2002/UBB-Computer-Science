package Model.Expressions;

import Implemented_Exceptions.ADTException;
import Implemented_Exceptions.ExpressionException;
import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIHeap;
import Model.Types.Type;
import Model.Values.Value;

public class VarExp implements Expression{
    String key;

    public VarExp(String key) {
        this.key = key;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> symTable, MyIHeap heap) throws ADTException {
        return symTable.lookUp(key);
    }

    @Override
    public Expression deepCopy() {
        return new VarExp(key);
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException {
        return typeEnv.lookUp(this.key);
    }

    @Override
    public String toString() {
        return key;
    }
}
