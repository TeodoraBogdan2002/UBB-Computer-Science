package Model.Expressions;

import Implemented_Exceptions.ADTException;
import Implemented_Exceptions.ExpressionException;
import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIHeap;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

public class ArithExp implements Expression {
    private Expression exp1, exp2;
    private char op;

    public ArithExp(char op,Expression exp1, Expression exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    @Override
    public String toString() {
        return this.exp1.toString()  + " " + this.op  + " " + this.exp2.toString();
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> tbl, MyIHeap heap) throws ExpressionException, ADTException
    {
        Value v1, v2;
        v1 = exp1.evaluate(tbl,heap);
        if (v1.getType().equals(new IntType())) {
            v2 = this.exp2.evaluate(tbl,heap);
            if (v2.getType().equals(new IntType())) {
                IntValue toInteger1 = (IntValue) v1;
                IntValue toInteger2 = (IntValue) v2;
                int number1, number2;
                number1 = toInteger1.getValue();
                number2 = toInteger2.getValue();
                if (this.op == '+')
                {
                    return new IntValue(number1 + number2);
                }
                if (op == '-')
                {
                    return new IntValue(number1 - number2);
                }
                if (op == '*')
                {
                    return new IntValue(number1 * number2);
                }
                if (op == '/')
                {
                    if (number2 == 0){
                        throw new ExpressionException("division by zero");
                    }
                    else {
                        return new IntValue(number1 / number2);
                    }
                }
            }
            else throw new ExpressionException("second operand is not an integer");
        }
        else throw new ExpressionException("first operand is not an integer");
        return null;
    }

    @Override
    public Expression deepCopy() {
        return new ArithExp(op, exp1.deepCopy(), exp2.deepCopy());
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException {
        Type type1, type2;
        type1 = this.exp1.typeCheck(typeEnv);
        type2 = this.exp2.typeCheck(typeEnv);

        if(type1.equals(new IntType()))
            if(type2.equals(new IntType()))
                return new IntType();
            else
                throw new ExpressionException("second operand is not an integer");
        else
            throw new ExpressionException("first operand is not an integer");
    }
}
