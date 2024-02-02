package Model.Statement;

import Implemented_Exceptions.InterpreterException;
import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIStack;
import Model.PrgState.PrgState;
import Model.Types.Type;

public class CompoundStmt implements IStmt {
    private final IStmt firstStmt;
    private final IStmt secondStmt;

    public CompoundStmt(IStmt firstStatement, IStmt secondStmt) {
        this.firstStmt = firstStatement;
        this.secondStmt = secondStmt;
    }

    @Override
    public String toString() {
        return "(" + firstStmt.toString() + ";" + secondStmt.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        IStmt copyFirst = this.firstStmt.deepCopy();
        IStmt copySecond = this.secondStmt.deepCopy();
        return new CompoundStmt(copyFirst, copySecond);
    }

    @Override
    public PrgState execute(PrgState programstate) {
        MyIStack<IStmt> mystack = programstate.getExeStack();
        mystack.push(secondStmt);
        mystack.push(firstStmt);
        programstate.setExeStack(mystack);
        return null;
    }


    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        return secondStmt.typeCheck(firstStmt.typeCheck(typeEnv));
    }


}
