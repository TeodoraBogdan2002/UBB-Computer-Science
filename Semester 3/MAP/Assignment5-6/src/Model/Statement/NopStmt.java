package Model.Statement;

import Implemented_Exceptions.ExpressionException;
import Model.MyADTs.MyIDictionary;
import Model.PrgState.PrgState;
import Model.Types.Type;

public class NopStmt implements IStmt{
    @Override
    public PrgState execute(PrgState state) {
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException {
        return typeEnv;

    }

    @Override
    public String toString() {
        return "NopStatement";
    }
}
