package Repository;



import Implemented_Exceptions.InterpreterException;
import Model.PrgState.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements RepositoryInterface{
    private List<PrgState> programStates;
    private final String logFilePath;

    public Repository(PrgState programState, String logFilePath) throws IOException {
        this.logFilePath = logFilePath;
        this.programStates = new ArrayList<>();
        this.addProgram(programState);
        this.emptyLogFile();
    }

    @Override
    public List<PrgState> getProgramList() {
        return this.programStates;
    }

    @Override
    public void setProgramStates(List<PrgState> programStates) {
        this.programStates = programStates;
    }

    @Override
    public void addProgram(PrgState program) {
        this.programStates.add(program);
    }

    @Override
    public void logProgramStateExecution(PrgState programState) throws InterpreterException, IOException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println(programState.programStateToString());
        logFile.close();
    }

    @Override
    public void emptyLogFile() throws IOException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
        logFile.close();
    }
}