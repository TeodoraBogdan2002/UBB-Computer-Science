package Model.Statement;

import Implemented_Exceptions.InterpreterException;
import Model.Expressions.Expression;
import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIHeap;
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
    public String toString() {
        return "Print( "+ expression.toString()+" )";
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(expression.deepCopy());
    }

    @Override
    public PrgState execute(PrgState programstate) throws InterpreterException {
        MyIList<Value> out = programstate.getOut();
        MyIDictionary<String, Value> table = programstate.getSymTable();
        MyIHeap heap = programstate.getHeap();

        out.add(this.expression.evaluate(table, heap));

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        this.expression.typeCheck(typeEnv);
        return typeEnv;
    }


}
