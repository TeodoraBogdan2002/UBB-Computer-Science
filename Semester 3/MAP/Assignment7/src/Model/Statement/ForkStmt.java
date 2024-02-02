package Model.Statement;

import Implemented_Exceptions.InterpreterException;
import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIStack;
import Model.MyADTs.MyStack;
import Model.PrgState.PrgState;
import Model.Types.Type;
import Model.Values.Value;


public class ForkStmt implements IStmt{
    private final IStmt statement;

    public ForkStmt(IStmt statement) {
        this.statement = statement;
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(statement.deepCopy());
    }

    @Override
    public String toString() {
        return "fork( " + this.statement.toString() + ")";
    }


    @Override
    public PrgState execute(PrgState programstate) throws InterpreterException {
        MyIDictionary<String, Value> symbolTable = programstate.getSymTable();

        MyIStack<IStmt> newStack = new MyStack<IStmt>();
        MyIDictionary<String, Value> newSymbolTable = symbolTable.deepCopy();

        return new PrgState(newStack, newSymbolTable, programstate.getOut(), programstate.getFileTable(), programstate.getHeap(), statement);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        return this.statement.typeCheck(typeEnv);
    }

}
