package Model.Statement;

import Implemented_Exceptions.InterpreterException;
import Model.MyADTs.MyIDictionary;
import Model.PrgState.PrgState;
import Model.Types.Type;
import Model.Values.Value;

public class VarDeclStmt implements IStmt{
    String name;
    Type type;

    public VarDeclStmt(String name, Type type){
        this.name=name;
        this.type=type;
    }


    @Override
    public String toString() {
        return String.format("%s %s", type.toString(), name);
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(name, type);
    }

    @Override
    public PrgState execute(PrgState programstate) throws InterpreterException
    {
        MyIDictionary<String, Value> symTable = programstate.getSymTable();
        if(symTable.isDefined(name)){
            throw new InterpreterException("Variable"+name+"already exists in the symTable");
        }
        symTable.put(name,type.defaultValue());
        programstate.setSymTable(symTable);
        return null;
    }


    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        typeEnv.put(this.name, this.type);
        return typeEnv;
    }

}
