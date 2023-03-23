package Model.Statement;

import Implemented_Exceptions.InterpreterException;
import Model.MyADTs.MyIDictionary;
import Model.PrgState.PrgState;
import Model.Types.Type;

import java.io.IOException;

public interface IStmt {
    PrgState execute(PrgState programstate) throws InterpreterException, IOException; //also throws myExc
    IStmt deepCopy();

    MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException;
}
