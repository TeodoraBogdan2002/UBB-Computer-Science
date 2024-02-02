package Model.Statement;

import Implemented_Exceptions.ADTException;
import Implemented_Exceptions.ExpressionException;
import Implemented_Exceptions.StatementExecutionException;
import Model.Expressions.Expression;
import Model.PrgState.PrgState;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStmt {
    private Expression expression;
    private String variableName;

    @Override
    public String toString() {
        return "read(" + expression.toString() + "," + variableName + ")";
    }

    public ReadFileStatement(Expression expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpressionException, StatementExecutionException, ADTException, IOException {
        if (state.getSymTable().isDefined(variableName)) {
            if (state.getSymTable().lookUp(variableName).getType().equals(new IntType())) {
                Value evaluationValue;
                evaluationValue = this.expression.evaluate(state.getSymTable(),state.getHeap());
                if (evaluationValue.getType().equals(new StringType())) {
                    StringValue downcastedValue = (StringValue) evaluationValue;
                    String expressionValue = downcastedValue.getValue();
                    if (state.getFileTable().isDefined(expressionValue)) {
                        BufferedReader fileDescriptor = state.getFileTable().lookUp(expressionValue);
                        String line = fileDescriptor.readLine();
                        IntValue readValue;
                        if (line == null)
                            readValue = new IntValue(0);
                        else
                            readValue = new IntValue(Integer.parseInt(line));
                        state.getSymTable().update(variableName, readValue);
                    } else
                        throw new StatementExecutionException("No entry associated in the file table.");
                } else
                    throw new StatementExecutionException("Expression didn't evaluate to a string.");
            } else
                throw new StatementExecutionException("Associated value type is not int");
        } else
            throw new StatementExecutionException("Variable name is not defined in the symbol table");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFileStatement(expression.deepCopy(), variableName);    }
}