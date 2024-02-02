package Model.Statement;
import Implemented_Exceptions.ADTException;
import Implemented_Exceptions.ExpressionException;
import Implemented_Exceptions.StatementExecutionException;
import Model.Expressions.Expression;
import Model.PrgState.PrgState;
import Model.Values.BoolValue;
import Model.Values.Value;

import java.io.IOException;

public class IfStmt implements IStmt{
    private Expression expression;
    private IStmt ifStatement, elseStatement;

    public IfStmt(Expression exp, IStmt ifStatement1, IStmt elseStatement1 ){
        this.expression=exp;
        this.ifStatement=ifStatement1;
        this.elseStatement=elseStatement1;
    }

    public PrgState execute(PrgState state) throws ExpressionException, ADTException, StatementExecutionException, IOException {
        Value result = this.expression.evaluate(state.getSymTable(),state.getHeap());
        if(((BoolValue) result).getValue())
            this.ifStatement.execute(state);
        else
            this.elseStatement.execute(state);
        return state;
    }

    @Override
    public String toString(){
        return "(IF("+ expression.toString()+") THEN(" +ifStatement.toString()
            +")ELSE("+elseStatement.toString()+"))";
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(expression.deepCopy(), ifStatement.deepCopy(), elseStatement.deepCopy());
    }
}
