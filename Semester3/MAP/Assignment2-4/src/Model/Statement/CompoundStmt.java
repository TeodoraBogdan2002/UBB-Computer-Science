package Model.Statement;

import Model.MyADTs.MyIStack;
import Model.PrgState.PrgState;

public class CompoundStmt implements IStmt {
    private IStmt first, second;

    public CompoundStmt(IStmt first, IStmt second){
        this.first=first;
        this.second=second;
    }
    public IStmt getFirst(){
        return first;
    }

    public IStmt getSecond(){
        return second;
    }

    @Override
    public PrgState execute(PrgState state) {
        MyIStack<IStmt> stack = state.getExeStack();
        stack.push(second);
        stack.push(first);
//        state.setExeStack(stack);
//        return state;
        return null;
    }

    @Override
    public String toString(){
        return "("+this.first.toString() + ";" + this.second.toString() +")";
    }

    @Override
    public IStmt deepCopy() {
        return new CompoundStmt(first.deepCopy(), second.deepCopy());
    }

}
