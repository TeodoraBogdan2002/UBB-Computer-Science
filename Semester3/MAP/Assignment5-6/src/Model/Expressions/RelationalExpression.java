package Model.Expressions;

import Implemented_Exceptions.ADTException;
import Implemented_Exceptions.ExpressionException;
import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIHeap;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;

import java.util.Objects;

public class RelationalExpression implements Expression{
    Expression expression1;
    Expression expression2;
    String operator;

    public RelationalExpression(String operator, Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operator = operator;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> symTable, MyIHeap heap) throws ExpressionException, ADTException
    {
        Value value1, value2;
        value1 = this.expression1.evaluate(symTable, heap);
        if (value1.getType().equals(new IntType())) {
            value2 = this.expression2.evaluate(symTable, heap);
            if (value2.getType().equals(new IntType())) {
                IntValue val1 = (IntValue) value1;
                IntValue val2 = (IntValue) value2;
                int v1, v2;
                v1 = val1.getValue();
                v2 = val2.getValue();
                if (Objects.equals(this.operator, "<"))
                    return new BoolValue(v1 < v2);
                else if (Objects.equals(this.operator, "<="))
                    return new BoolValue(v1 <= v2);
                else if (Objects.equals(this.operator, "=="))
                    return new BoolValue(v1 == v2);
                else if (Objects.equals(this.operator, "!="))
                    return new BoolValue(v1 != v2);
                else if (Objects.equals(this.operator, ">"))
                    return new BoolValue(v1 > v2);
                else if (Objects.equals(this.operator, ">="))
                    return new BoolValue(v1 >= v2);
            } else
                throw new ExpressionException("Second operand is not an integer.");
        } else
            throw new ExpressionException("First operand in not an integer.");
        return null;
    }

    @Override
    public Expression deepCopy() {
        return new RelationalExpression(operator, expression1.deepCopy(), expression2.deepCopy());
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException {
        Type type1, type2;
        type1 = this.expression1.typeCheck(typeEnv);
        type2 = this.expression2.typeCheck(typeEnv);

        if(type1.equals(new IntType()))
            if(type2.equals(new IntType()))
                return new BoolType();
            else
                throw new ExpressionException("second operand is not an integer");
        else
            throw new ExpressionException("first operand is not an integer");
    }

    @Override
    public String toString() {
        return this.expression1.toString() + " " + this.operator + " " + this.expression2.toString();
    }
}