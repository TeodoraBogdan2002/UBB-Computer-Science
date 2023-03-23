package Model.Statement;

import Implemented_Exceptions.ADTException;
import Implemented_Exceptions.ExpressionException;
import Implemented_Exceptions.StatementExecutionException;
import Model.Expressions.Expression;
import Model.MyADTs.MyIDictionary;
import Model.PrgState.PrgState;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenFileStatement implements IStmt {
    private Expression expression;

    public OpenFileStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "open(" + expression.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws ExpressionException, FileNotFoundException, ADTException, StatementExecutionException {
        Value evaluationValue;
        evaluationValue = this.expression.evaluate(state.getSymTable(),state.getHeap());
        if (evaluationValue.getType().equals(new StringType())) {
            StringValue downcastedValue = (StringValue) evaluationValue;
            String expressionValue = downcastedValue.getValue();
            if (!state.getFileTable().isDefined(expressionValue)) {
                BufferedReader fileDescriptor = new BufferedReader(new FileReader(expressionValue));
                state.getFileTable().put(expressionValue, fileDescriptor);
            } else
                throw new StatementExecutionException("Filename already exists!");
        } else
            throw new StatementExecutionException("Expression doesn't evaluate to a string.");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new OpenFileStatement(expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException {
        Type typeExpression = this.expression.typeCheck(typeEnv);
        if(typeExpression.equals(new StringType()))
            return typeEnv;
        else
            throw new ExpressionException("The expression of OPENRFILE has not the string type");
    }
}