package Model.Statement;

import Implemented_Exceptions.ADTException;
import Implemented_Exceptions.ExpressionException;
import Implemented_Exceptions.StatementExecutionException;
import Model.Expressions.Expression;
import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIHeap;
import Model.PrgState.PrgState;
import Model.Values.RefValue;
import Model.Values.Value;

import java.io.IOException;

public class HeapWriteStmt implements IStmt{
    private final String varName;
    private final Expression expression;

    public HeapWriteStmt(String varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementExecutionException, ExpressionException, ADTException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();
        if (!symTable.isDefined(varName))
            throw new StatementExecutionException(String.format("%s not present in the symTable", varName));
        Value value = symTable.lookUp(varName);
        if (!(value instanceof RefValue))
            throw new StatementExecutionException(String.format("%s not of RefType", value));
        RefValue refValue = (RefValue) value;
        Value evaluated = expression.evaluate(symTable, heap);
        if (!evaluated.getType().equals(refValue.getInnerReferencedType()))
            throw new StatementExecutionException(String.format("%s not of %s", evaluated, refValue.getInnerReferencedType()));
        heap.update(refValue.getHeapAddress(), evaluated);
        state.setHeap(heap);
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new HeapWriteStmt(varName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("WriteHeap(%s, %s)", varName, expression);
    }
}
