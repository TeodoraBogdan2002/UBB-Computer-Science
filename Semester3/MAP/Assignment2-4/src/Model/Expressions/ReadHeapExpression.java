package Model.Expressions;

import Implemented_Exceptions.ADTException;
import Implemented_Exceptions.ExpressionException;
import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIHeap;
import Model.Values.RefValue;
import Model.Values.Value;

public class ReadHeapExpression implements Expression{
    private final Expression expression;

    public ReadHeapExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> table, MyIHeap heap) throws ExpressionException, ADTException {
        Value value =expression.evaluate(table, heap);
        if(!(value instanceof RefValue))
            throw new ExpressionException(String.format("%s not of RefType", value));
        RefValue refValue =(RefValue) value;
        return heap.get(refValue.getHeapAddress());
    }

    @Override
    public Expression deepCopy() {
        return new ReadHeapExpression(expression.deepCopy());
    }

    @Override
    public String toString(){
        return String.format("ReadHeap(%s)", expression);
    }
}
