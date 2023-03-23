package Model.Statement;

import Implemented_Exceptions.ExpressionException;
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
    public PrgState execute(PrgState state) throws ExpressionException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if(symTable.isDefined(name)){
            throw new ExpressionException("Variable"+name+"already exists in the symTable");
        }
        symTable.put(name,type.defaultValue());
        state.setSymTable(symTable);
        return state;
    }
    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(name, type);
    }

    @Override
    public String toString() {
        return String.format("%s %s", type.toString(), name);
    }
}
