package Model.Statement;

import Implemented_Exceptions.ADTException;
import Implemented_Exceptions.ExpressionException;
import Implemented_Exceptions.StatementExecutionException;
import Model.Expressions.Expression;
import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIStack;
import Model.PrgState.PrgState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

import java.io.IOException;

public class IfStmt implements IStmt {
    private Expression expression;
    private IStmt ifStatement, elseStatement;

    public IfStmt(Expression exp, IStmt ifStatement1, IStmt elseStatement1) {
        this.expression = exp;
        this.ifStatement = ifStatement1;
        this.elseStatement = elseStatement1;
    }

    public PrgState execute(PrgState state) throws ExpressionException, ADTException, StatementExecutionException, IOException {
//        Value result = this.expression.evaluate(state.getSymTable(), state.getHeap());
//        if (((BoolValue) result).getValue())
//            this.ifStatement.execute(state);
//        else
//            this.elseStatement.execute(state);
//        return state;
        Value result = this.expression.evaluate(state.getSymTable(), state.getHeap());
        if (result instanceof BoolValue boolResult) {
            IStmt statement;
            if (boolResult.getValue()) {
                statement = ifStatement;
            } else {
                statement = elseStatement;
            }

            MyIStack<IStmt> stack = state.getExeStack();
            stack.push(statement);
            state.setExeStack(stack);
            return null;
        } else {
            throw new StatementExecutionException("Please provide a boolean expression in an if statement.");
        }

    }

    @Override
    public String toString() {
        return "(IF(" + expression.toString() + ") THEN(" + ifStatement.toString()
                + ")ELSE(" + elseStatement.toString() + "))";
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(expression.deepCopy(), ifStatement.deepCopy(), elseStatement.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException {
        Type typeExpression = this.expression.typeCheck(typeEnv);
        if(typeExpression.equals(new BoolType()))
        {
            this.ifStatement.typeCheck((MyIDictionary<String, Type>) typeEnv.deepCopy());
            this.elseStatement.typeCheck((MyIDictionary<String, Type>) typeEnv.deepCopy());

            return typeEnv;
        }
        else throw new ExpressionException("The condition of IF statement has not the type bool");
    }
}
