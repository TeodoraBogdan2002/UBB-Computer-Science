package Model.Expressions;

import Implemented_Exceptions.InterpreterException;
import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIStack;
import Model.PrgState.PrgState;
import Model.Statement.CompoundStmt;
import Model.Statement.IStmt;
import Model.Statement.WhileStatement;
import Model.Types.Type;

import java.io.IOException;

public class DoWhileStmt implements IStmt {
    private final IStmt stmt;
    private final Expression exp;

    public DoWhileStmt(IStmt stmt, Expression exp) {
        this.stmt = stmt;
        this.exp = exp;
    }

    @Override
    public String toString(){
        return "do {"+stmt+"} while ("+exp+")";
    }

    @Override
    public PrgState execute(PrgState programstate) throws InterpreterException, IOException {
        WhileStatement whilestmt=new WhileStatement(exp,stmt);
        IStmt doWhileStmt=new CompoundStmt(stmt,whilestmt);
        MyIStack<IStmt> exeStack=programstate.getExeStack();

        exeStack.push(doWhileStmt);
        programstate.setExeStack(exeStack);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new DoWhileStmt(stmt.deepCopy(),exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        return null;
    }
}
