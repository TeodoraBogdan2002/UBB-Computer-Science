package Model.Expressions;

import Implemented_Exceptions.ADTException;
import Implemented_Exceptions.ExpressionException;
import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIHeap;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class ReadHeapExpression implements Expression{
    private final Expression expression;

    public ReadHeapExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> table, MyIHeap heap) throws ExpressionException, ADTException {
        Value value = expression.evaluate(table, heap);
        if (value instanceof RefValue) {
            RefValue refValue = (RefValue) value;
            if (heap.containsKey(refValue.getHeapAddress()))
                return heap.get(refValue.getHeapAddress());
            else
                throw new ExpressionException("The address is not defined on the heap!");
        } else
            throw new ExpressionException(String.format("%s not of RefType", value));
    }

    @Override
    public Expression deepCopy() {
        return new ReadHeapExpression(expression.deepCopy());
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException {
        Type type = this.expression.typeCheck(typeEnv);
        if(type instanceof RefType)
        {
            RefType referenceType = (RefType) type;
            return referenceType.getInner();
        }
        else
            throw new ExpressionException("the readHeap argument is not a reference type");
    }

    @Override
    public String toString(){
        return String.format("ReadHeap(%s)", expression);
    }
}
