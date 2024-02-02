package Repository;

import Implemented_Exceptions.ADTException;
import Implemented_Exceptions.StatementExecutionException;
import Model.PrgState.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements RepositoryInterface{
    private List<PrgState> programStates;
    private int currentPosition;
    private String path;
    private boolean first;


    public Repository(PrgState programState, String filepath){
        this.programStates = new ArrayList<>();
        this.currentPosition = 0;
        this.addProgram(programState);
        this.first=true;
        this.path=filepath;
        //emptyLogFile();
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
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
    public void logProgramStateExecution() throws StatementExecutionException, IOException, ADTException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(path, true)));
        logFile.println(this.programStates.get(0).toString());
        logFile.close();
    }

    @Override
    public void emptyLogFile() throws IOException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(path, false)));
        logFile.close();
    }


    @Override
    public PrgState getCurrentState() {
        return this.programStates.get(this.currentPosition);
    }

    @Override
    public PrgState getCurrentProgram() {
        return programStates.get(programStates.size()-1);
    }
}