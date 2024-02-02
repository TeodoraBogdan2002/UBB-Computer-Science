package Repository;

import Implemented_Exceptions.InterpreterException;
import Model.PrgState.PrgState;

import java.io.IOException;
import java.util.List;

public interface RepositoryInterface {
    List<PrgState> getProgramList();
    void setProgramStates(List<PrgState> programStates);

    void emptyLogFile() throws IOException;

    void addProgram(PrgState program);

    public void logProgramStateExecution(PrgState prgState) throws InterpreterException, IOException;
}
