
package Model.Statement;

import Implemented_Exceptions.ADTException;
import Implemented_Exceptions.ExpressionException;
import Implemented_Exceptions.StatementExecutionException;
import Model.Expressions.Expression;
import Model.MyADTs.MyIDictionary;
import Model.PrgState.PrgState;
import Model.Types.Type;
import Model.Values.Value;

public class AssignStmt implements IStmt {
    private String id;
    private Expression exp;

    public AssignStmt(String id, Expression exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpressionException, ADTException, StatementExecutionException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();

        if (symTbl.isDefined(id)) {
            Value val = exp.evaluate(symTbl,state.getHeap());
            Type typId = (symTbl.lookUp(id)).getType();
            if (val.getType().equals(typId)){
                symTbl.update(id, val);
            }
            else throw new StatementExecutionException("declared type of variable" + id + " and type of the assigned expression do not match ");

        }
        else throw new StatementExecutionException("the used variable" + id + " was not declared before");
        state.setSymTable(symTbl);
        return null;
    }
    @Override
    public IStmt deepCopy() {
        return new AssignStmt(id, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException {
        Type typeVariable = typeEnv.lookUp(this.id);
        Type typeExpression = this.exp.typeCheck(typeEnv);
        if(typeVariable.equals(typeExpression))
            return typeEnv;
        else
            throw new ExpressionException("ASSIGNMENT statement: right hand side and left hand side have different types");
    }


    @Override
    public String toString() {
        return this.id + "=" + this.exp.toString();
    }

}
