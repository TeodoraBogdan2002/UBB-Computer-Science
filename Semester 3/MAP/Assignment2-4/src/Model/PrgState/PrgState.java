package Model.PrgState;

import Implemented_Exceptions.ADTException;
import Implemented_Exceptions.ExpressionException;
import Implemented_Exceptions.StatementExecutionException;
import Model.MyADTs.*;
//myExc
import Model.Statement.IStmt;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class PrgState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    IStmt originalProgram;

    MyIHeap heap;
    private int id;
    private static int globalID = 0;

    private MyIDictionary<String, BufferedReader> fileTable;

    private static int lastId = 0;


    public synchronized int getGlobalID()
    {
        globalID+=1;
        return globalID;
    }

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value>
            ot, IStmt prg,MyIHeap heap,MyIDictionary<String,BufferedReader> fileTable){
        this.exeStack=stk;
        symTable=symtbl;
        out = ot;
        originalProgram= prg.deepCopy();//recreate the entire original prg
        this.exeStack.push(this.originalProgram);
        this.id=getGlobalID();
        this.heap=heap;
        this.fileTable=fileTable;
    }

    public PrgState(MyIStack<IStmt> stack, MyIDictionary<String,Value> symTable, MyIList<Value> out, MyIDictionary<String, BufferedReader> fileTable, MyIHeap heap) {
        this.exeStack = stack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = setId();
    }

    private IStmt deepCopy(IStmt prg) {
        return prg;
    }


    public Integer getId(){
        return this.id;
    }

    public synchronized int setId() {
        lastId++;
        return lastId;
    }

    public MyIStack<IStmt> getExeStack(){
        return this.exeStack;
    }

    public void setExeStack(MyIStack<IStmt> executionStack){
        this.exeStack=executionStack;
    }

    public MyIDictionary<String,Value> getSymTable(){
        return this.symTable;
    }

    public void setSymTable(MyIDictionary<String,Value> symbolTable){
        this.symTable=symbolTable;
    }

    public MyIList<Value> getOutput(){
        return this.out;
    }

    public void setOutput(MyIList<Value> output){
        this.out=output;
    }

    public MyIHeap getHeap(){
        return heap;
    }

    public void setHeap(MyIHeap newHeap){
        this.heap=newHeap;
    }

    public MyIDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(MyIDictionary<String, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }


//    public PrgState(IStmt originalProgram){
//        this.exeStack=new MyStack<IStmt>();
//        this.symTable=new MyDictionary<String,Value>();
//        this.out=new MyList<Value>();
//        this.id=1;
//        this.exeStack.push(originalProgram);
//        this.fileTable=new MyDictionary<String,BufferedReader>();
//    }

    public PrgState executeOneStep() throws ExpressionException, ADTException, StatementExecutionException, IOException {
        if(exeStack.isEmpty())
            throw new ExpressionException("Stack is empty!");
        IStmt top = exeStack.pop();
        return top.execute(this);
    }

    @Override
    public String toString(){
        return "ProgramState with id: "+id+"\n"+
                "Execution Stack:\n" + exeStack.toString()+"\n"+
                "Symbol Table:\n"+symTable.toString()+"\n"+
                "Output:\n"+out.toString()+"\n"+
                "Heap: \n"+heap.toString()+"\n"+
                "File table\n" + fileTable.toString() +"\n";
    }

    public String exeStackToString() {
        StringBuilder exeStackStringBuilder = new StringBuilder();
        List<IStmt> stack = exeStack.getValues();
        for (IStmt statement: stack) {
            exeStackStringBuilder.append(statement.toString()).append("\n");
        }
        return exeStackStringBuilder.toString();
    }

    public String symTableToString() throws ADTException {
        StringBuilder symTableStringBuilder = new StringBuilder();
        for (String key: symTable.keySet()) {
            symTableStringBuilder.append(String.format("%s -> %s\n", key, symTable.lookUp(key).toString()));
        }
        return symTableStringBuilder.toString();
    }

    public String outToString() {
        StringBuilder outStringBuilder = new StringBuilder();
        for (Value elem: out.getList()) {
            outStringBuilder.append(String.format("%s\n", elem.toString()));
        }
        return outStringBuilder.toString();
    }

    public String fileTableToString() {
        StringBuilder fileTableStringBuilder = new StringBuilder();
        for (String key: fileTable.keySet()) {
            fileTableStringBuilder.append(String.format("%s\n", key));
        }
        return fileTableStringBuilder.toString();
    }

    public String heapToString() throws ADTException {
        StringBuilder heapStringBuilder = new StringBuilder();
        for (int key: heap.keySet()) {
            heapStringBuilder.append(String.format("%d -> %s\n", key, heap.get(key)));
        }
        return heapStringBuilder.toString();
    }

    public String programStateToString() throws ADTException {
        return "Execution stack: \n" + exeStackToString() + "Symbol table: \n" + symTableToString() + "Output list: \n" + outToString() + "File table:\n" + fileTableToString() + "Heap memory:\n" + heapToString();
    }

}
