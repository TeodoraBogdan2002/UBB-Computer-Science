package Model.Statement;

import Implemented_Exceptions.InterpreterException;
import Model.Expressions.Expression;
import Model.Expressions.NOTExpr;
import Model.MyADTs.MyIDictionary;
import Model.PrgState.PrgState;
import Model.Types.BoolType;
import Model.Types.Type;

import java.io.IOException;

public class RepeatUntilStmt implements IStmt{
    private IStmt stmt;
    private Expression exp;

    public RepeatUntilStmt(IStmt stmt, Expression exp) {
        this.stmt = stmt;
        this.exp = exp;
    }


    @Override
    public String toString(){
        return "Repeat{ \n"+stmt.toString()+"\n}until"+exp.toString();
    }

    @Override
    public IStmt deepCopy() {
        return new RepeatUntilStmt(stmt.deepCopy(),exp.deepCopy());
    }

    @Override
    public PrgState execute(PrgState programstate) throws InterpreterException, IOException {
        NOTExpr notExpr=new NOTExpr(exp);
        WhileStatement whileStmt=new WhileStatement(notExpr,stmt);
        IStmt repeatUntilStmt= new CompoundStmt(stmt,whileStmt);
        programstate.getExeStack().push(repeatUntilStmt);
        return null;
    }



    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        Type type=exp.typeCheck(typeEnv);
        if(type.equals(new BoolType())){
            stmt.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else{
            throw new InterpreterException("The expression from the statement most be bool");
        }
    }
}
