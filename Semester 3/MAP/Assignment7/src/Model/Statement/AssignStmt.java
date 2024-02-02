
package Model.Statement;

import Implemented_Exceptions.InterpreterException;
import Model.Expressions.Expression;
import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIHeap;
import Model.MyADTs.MyIStack;
import Model.PrgState.PrgState;
import Model.Types.Type;
import Model.Values.Value;

public class AssignStmt implements IStmt {
    private String id;
    private Expression expression;

    public AssignStmt(String id, Expression exp) {
        this.id = id;
        this.expression = exp;
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(id, expression.deepCopy());
    }

    @Override
    public String toString() {
        return this.id + "=" + this.expression.toString();
    }

    @Override
    public PrgState execute(PrgState programstate) throws InterpreterException {
        MyIStack<IStmt> stack = programstate.getExeStack();
        MyIDictionary<String, Value> symbolTable = programstate.getSymTable();
        MyIHeap heap = programstate.getHeap();

        if (symbolTable.isDefined(id))
        {
            Value value = expression.evaluate(symbolTable, heap);
            Type typeId = (symbolTable.lookUp(id)).getType();
            if(value.getType().equals(typeId))
                symbolTable.update(id, value);
            else
                throw new InterpreterException("declared type of variable " + id + " and type of the assigned expression do not match");
        }
        else throw new InterpreterException("the used variable " + id + " was not declared before");

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        Type typeVariable = typeEnv.lookUp(this.id);
        Type typeExpression = this.expression.typeCheck(typeEnv);
        if(typeVariable.equals(typeExpression))
            return typeEnv;
        else
            throw new InterpreterException("ASSIGNMENT statement: right hand side and left hand side have different types");
    }

}
