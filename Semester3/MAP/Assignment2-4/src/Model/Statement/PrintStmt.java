package Model.Statement;

import Implemented_Exceptions.ADTException;
import Implemented_Exceptions.ExpressionException;
import Model.Expressions.Expression;
import Model.PrgState.PrgState;
import Model.MyADTs.*;
import Model.Values.Value;

public class PrintStmt implements IStmt{
    Expression expression;

    public PrintStmt(Expression expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpressionException, ADTException {
        MyIList<Value> out = state.getOutput();
        out.add(expression.evaluate(state.getSymTable(),state.getHeap()));
        state.setOutput(out);
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("Print(%s)", expression.toString());
    }
}
