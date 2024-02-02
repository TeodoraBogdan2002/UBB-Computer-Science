package Model.Statement;

import Implemented_Exceptions.InterpreterException;
import Model.Expressions.Expression;
import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIHeap;
import Model.MyADTs.MyIStack;
import Model.PrgState.PrgState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class IfStmt implements IStmt {
    private Expression expression;
    private IStmt ifStatement, elseStatement;

    public IfStmt(Expression exp, IStmt ifStatement1, IStmt elseStatement1) {
        this.expression = exp;
        this.ifStatement = ifStatement1;
        this.elseStatement = elseStatement1;
    }

    public PrgState execute(PrgState programstate) throws InterpreterException {
        MyIDictionary<String, Value> symbolTable = programstate.getSymTable();
        MyIStack<IStmt> stack = programstate.getExeStack();
        MyIHeap heap = programstate.getHeap();
        BoolValue condition = (BoolValue)this.expression.evaluate(symbolTable,heap);

        if(condition.getValue())
            stack.push(this.ifStatement);
        else
            stack.push(this.elseStatement);
        return null;

    }

    @Override
    public String toString() {
        return "(IF(" + expression.toString() + ") THEN(" + ifStatement.toString()
                + ") ELSE(" + elseStatement.toString() + "))";
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(expression.deepCopy(), ifStatement.deepCopy(), elseStatement.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        Type typeExpression = this.expression.typeCheck(typeEnv);
        if (typeExpression.equals(new BoolType())) {
            this.ifStatement.typeCheck((MyIDictionary<String, Type>) typeEnv.deepCopy());
            this.elseStatement.typeCheck((MyIDictionary<String, Type>) typeEnv.deepCopy());

            return typeEnv;
        } else throw new InterpreterException("The condition of IF statement has not the type bool");
    }
}
