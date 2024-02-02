package Model.Statement;

import Implemented_Exceptions.ADTException;
import Implemented_Exceptions.ExpressionException;
import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIStack;
import Model.PrgState.PrgState;
import Model.Types.Type;

public class CompoundStmt implements IStmt {
    private final IStmt firstStatement;
    private final IStmt secondStatement;

    public CompoundStmt(IStmt firstStatement, IStmt secondStatement) {
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
    }

    @Override
    public PrgState execute(PrgState state){
        MyIStack<IStmt> stack = state.getExeStack();
        stack.push(secondStatement);
        stack.push(firstStatement);
        state.setExeStack(stack);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CompoundStmt(firstStatement.deepCopy(), secondStatement.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException {
        return secondStatement.typeCheck(firstStatement.typeCheck(typeEnv));
    }

    @Override
    public String toString() {
        return String.format("(%s|%s)", firstStatement.toString(), secondStatement.toString());
    }

}
