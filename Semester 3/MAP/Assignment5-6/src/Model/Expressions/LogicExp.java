package Model.Expressions;

import Implemented_Exceptions.ADTException;
import Implemented_Exceptions.ExpressionException;
import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIHeap;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class LogicExp implements Expression{
    Expression exp1,exp2;
    private String op;

    public LogicExp(Expression exp1, Expression exp2, String op) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> table, MyIHeap heap) throws ExpressionException, ADTException
    {
        Value v1,v2;
        v1 = exp1.evaluate(table,heap);
        if (v1.getType().equals(new BoolType())){
            v2 = exp2.evaluate(table,heap);
            if (v2.getType().equals(new BoolType())){
                BoolValue i1 = (BoolValue)v1;
                BoolValue i2 = (BoolValue)v2;
                boolean n1,n2;
                n1 = i1.getValue();
                n2 = i2.getValue();
                if (op=="and"){
                    return new BoolValue(n1 && n2);
                }
                if(op=="or"){
                    return new BoolValue(n1 || n2);
                }
                else throw new ExpressionException("Invalid operand");
            }
            else
                throw new ExpressionException("Second operand is not a bool.");
        }
        else{
            throw new ExpressionException("First second operand is not a bool.");
        }
//        return null;
    }

    @Override
    public Expression deepCopy(){
        return new LogicExp(exp1.deepCopy(),exp2.deepCopy(),op);
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException {
        Type type1, type2;
        type1 = this.exp1.typeCheck(typeEnv);
        type2 = this.exp2.typeCheck(typeEnv);

        if(type1.equals(new BoolType()))
            if(type2.equals(new BoolType()))
                return new BoolType();
            else
                throw new ExpressionException("second operand is not a boolean");
        else
            throw new ExpressionException("first operand is not a boolean");
    }
}
