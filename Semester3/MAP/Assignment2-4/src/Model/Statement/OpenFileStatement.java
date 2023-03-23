package Model.Statement;

import Implemented_Exceptions.ADTException;
import Implemented_Exceptions.ExpressionException;
import Implemented_Exceptions.StatementExecutionException;
import Model.Expressions.Expression;
import Model.PrgState.PrgState;
import Model.Types.StringType;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.*;

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
}