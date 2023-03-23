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

public class WhileStatement implements IStmt{
    private final Expression expression;
    private final IStmt statement;

    public WhileStatement(Expression expression, IStmt statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementExecutionException, ExpressionException, ADTException {
        Value value = expression.evaluate(state.getSymTable(), state.getHeap());
        MyIStack<IStmt> stack = state.getExeStack();
        if (!value.getType().equals(new BoolType()))
            throw new StatementExecutionException(String.format("%s is not of BoolType", value));
        BoolValue boolValue = (BoolValue) value;
        if (boolValue.getValue()) {
            stack.push(this);
            stack.push(statement);
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStatement(expression.deepCopy(), statement.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException {
        Type typeExpression = this.expression.typeCheck(typeEnv);
        if(typeExpression.equals(new BoolType()))
            return typeEnv;
        else
            throw new ExpressionException("The condition of WHILE statement has not the bool type");
    }

    @Override
    public String toString() {
        return String.format("while(%s){%s}", expression, statement);
    }
}
