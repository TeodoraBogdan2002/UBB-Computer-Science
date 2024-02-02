package Controller;

import Implemented_Exceptions.ADTException;
import Implemented_Exceptions.ExpressionException;
import Implemented_Exceptions.StatementExecutionException;
import Model.MyADTs.MyIStack;
import Model.PrgState.PrgState;
import Model.Statement.IStmt;
import Model.Values.RefValue;
import Model.Values.Value;
import Repository.RepositoryInterface;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {
    RepositoryInterface repository;
    boolean displayFlag = false;

    public void setDisplayFlag(boolean value) {
        this.displayFlag = value;
    }

    public Controller(RepositoryInterface repository) {
        this.repository = repository;
    }

    public PrgState oneStep(PrgState state) throws ExpressionException, ADTException, StatementExecutionException, IOException {
        MyIStack<IStmt> stack = state.getExeStack();
        if (stack.isEmpty())
            throw new StatementExecutionException("Program state stack is empty.");
        IStmt currentStatement = stack.pop();
        state.setExeStack(stack);
        return currentStatement.execute(state);
    }

    Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value> heap){
        return heap.entrySet().stream().filter(e->symTableAddr.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> ( symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getHeapAddress();})
                .collect(Collectors.toList());
    }

    public List<Integer> getAddrFromHeap(Collection<Value> heapValues) {
        return heapValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getHeapAddress();})
                .collect(Collectors.toList());
    }

    public void allSteps() throws ExpressionException, ADTException, StatementExecutionException, IOException {
        PrgState program = this.repository.getCurrentState();

        this.repository.logProgramStateExecution();
        display();
        while(!program.getExeStack().isEmpty()) {
            oneStep(program);
            this.repository.logProgramStateExecution();
//            program.getHeap().setContent((HashMap<Integer, Value>)unsafeGarbageCollector( getAddrFromSymTable(program.getSymTable().getContent().values()), program.getHeap().getContent()));

            program.getHeap().setContent((HashMap<Integer, Value>) safeGarbageCollector(getAddrFromSymTable(program.getSymTable().getContent().values()), getAddrFromHeap(program.getHeap().getContent().values()), program.getHeap().getContent()));
            this.repository.logProgramStateExecution();
            display();
        }
    }

    private void display() {
        if (displayFlag) {
            System.out.println(this.repository.getCurrentState().toString());
        }
    }
}