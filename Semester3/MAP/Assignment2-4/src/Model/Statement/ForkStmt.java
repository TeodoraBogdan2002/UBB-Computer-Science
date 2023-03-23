package Model.Statement;

import Model.MyADTs.MyDictionary;
import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIStack;
import Model.MyADTs.MyStack;
import Model.PrgState.PrgState;
import Model.Values.Value;

import java.util.Map;

public class ForkStmt implements IStmt{
    private final IStmt statement;

    public ForkStmt(IStmt statement) {
        this.statement = statement;
    }
    @Override
    public PrgState execute(PrgState state) //throws StatementExecutionException, ExpressionException, ADTException
    {
        MyIStack<IStmt> newStack = new MyStack<>();
        newStack.push(statement);
        MyIDictionary<String, Value> newSymTable = new MyDictionary<>();
        for (Map.Entry<String, Value> entry: state.getSymTable().getContent().entrySet()) {
            newSymTable.put(entry.getKey(), entry.getValue().deepCopy());
        }

        return new PrgState(newStack, newSymTable, state.getOutput(), state.getFileTable(), state.getHeap());
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(statement.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("Fork(%s)", statement.toString());
    }

}
