package Model.Statement;

import Implemented_Exceptions.ADTException;
import Implemented_Exceptions.ExpressionException;
import Implemented_Exceptions.StatementExecutionException;
import Model.MyADTs.MyIDictionary;
import Model.PrgState.PrgState;
import Model.Types.Type;

import java.io.IOException;

public interface IStmt {
    PrgState execute(PrgState state) throws ExpressionException, ADTException, StatementExecutionException, IOException; //also throws myExc
    IStmt deepCopy();

    MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException;
}
