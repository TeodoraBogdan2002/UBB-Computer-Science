package Repository;

import Implemented_Exceptions.ADTException;
import Implemented_Exceptions.StatementExecutionException;
import Model.PrgState.PrgState;

import java.io.IOException;
import java.util.List;

public interface RepositoryInterface {
    List<PrgState> getProgramList();
    void setProgramStates(List<PrgState> programStates);

    void emptyLogFile() throws IOException;

    PrgState getCurrentState();
    void addProgram(PrgState program);

    public void logProgramStateExecution() throws StatementExecutionException, IOException, ADTException;


    PrgState getCurrentProgram();
}
