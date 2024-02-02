package Model.Statement;

import Implemented_Exceptions.ADTException;
import Implemented_Exceptions.ExpressionException;
import Implemented_Exceptions.StatementExecutionException;
//import Model.Exceptions.MyException;
import Model.Expressions.Expression;
import Model.MyADTs.MyIDictionary;
import Model.PrgState.PrgState;
import Model.Types.StringType;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseFileStatement implements IStmt {
    private Expression expression;

    @Override
    public String toString() {
        return "close(" + expression.toString() + ")";
    }

    public CloseFileStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementExecutionException, ExpressionException, ADTException {
        Value value = expression.evaluate(state.getSymTable(), state.getHeap());
        if (!value.getType().equals(new StringType()))
            throw new StatementExecutionException(String.format("%s does not evaluate to StringValue", expression));
        StringValue fileName = (StringValue) value;
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        if (!fileTable.isDefined(fileName.getValue()))
            throw new StatementExecutionException(String.format("%s is not present in the FileTable", value));
        BufferedReader br = fileTable.lookUp(fileName.getValue());
        try {
            br.close();
        } catch (IOException e) {
            throw new StatementExecutionException(String.format("Unexpected error in closing %s", value));
        }
        fileTable.remove(fileName.getValue());
        state.setFileTable(fileTable);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CloseFileStatement(expression.deepCopy());
    }
}