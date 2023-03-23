package Model.Expressions;

import Implemented_Exceptions.InterpreterException;
import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIHeap;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;
import com.sun.jdi.BooleanValue;

public class NOTExpr implements Expression{
    private final Expression expr;

    public NOTExpr(Expression expr) {
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "!" + expr.toString();
    }

    @Override
    public Expression deepCopy() {
        return new NOTExpr(expr.deepCopy());
    }

    @Override
    public Value evaluate(MyIDictionary<String, Value> table, MyIHeap heap) throws InterpreterException {
        BoolValue boolval=(BoolValue) expr.evaluate(table,heap);
        if(!boolval.getValue())
            return new BoolValue(true);
        else return new BoolValue(false);
    }


    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        return expr.typeCheck(typeEnv);
    }

}
