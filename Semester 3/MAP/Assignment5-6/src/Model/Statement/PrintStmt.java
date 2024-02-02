package Model.Statement;

import Implemented_Exceptions.ADTException;
import Implemented_Exceptions.ExpressionException;
import Model.Expressions.Expression;
import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIList;
import Model.PrgState.PrgState;
import Model.Types.Type;
import Model.Values.Value;

public class PrintStmt implements IStmt{
    Expression expression;

    public PrintStmt(Expression expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpressionException, ADTException {
        MyIList<Value> out = state.getOut();
        out.add(expression.evaluate(state.getSymTable(),state.getHeap()));
        state.setOut(out);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException {
        this.expression.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return String.format("Print(%s)", expression.toString());
    }
}
