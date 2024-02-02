package Model.Statement;


import Implemented_Exceptions.ADTException;
import Implemented_Exceptions.ExpressionException;
import Implemented_Exceptions.StatementExecutionException;
import Model.Expressions.Expression;
import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIHeap;
import Model.PrgState.PrgState;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class NewStatement implements IStmt{
    private final String varName;
    private final Expression expression;

    public NewStatement(String varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementExecutionException, ExpressionException, ADTException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();
        if (!symTable.isDefined(varName))
            throw new StatementExecutionException(String.format("%s not in symTable", varName));
        Value varValue = symTable.lookUp(varName);
        if (!(varValue.getType() instanceof RefType))
            throw new StatementExecutionException(String.format("%s in not of RefType", varName));
        Value evaluated = expression.evaluate(symTable, heap);
        Type locationType = ((RefValue)varValue).getInnerReferencedType();
        if (!locationType.equals(evaluated.getType()))
            throw new StatementExecutionException(String.format("%s not of %s", varName, evaluated.getType()));
        int newPosition = heap.add(evaluated);
        symTable.put(varName, new RefValue(newPosition, locationType));
        state.setSymTable(symTable);
        state.setHeap(heap);
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new NewStatement(varName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("New(%s, %s)", varName, expression);
    }
}
