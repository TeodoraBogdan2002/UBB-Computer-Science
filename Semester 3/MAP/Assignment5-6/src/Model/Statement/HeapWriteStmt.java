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
        if (symTable.isDefined(varName)) {
            Value value = symTable.lookUp(varName);
            if (value.getType() instanceof RefType) {
                RefValue refValue = (RefValue) value;
                if (heap.containsKey(refValue.getHeapAddress())) {
                    Value evaluated = expression.evaluate(symTable, heap);
                    if (evaluated.getType().equals(refValue.getInnerReferencedType())) {
                        heap.update(refValue.getHeapAddress(), evaluated);
                        state.setHeap(heap);
                    } else
                        throw new StatementExecutionException(String.format("%s not of %s", evaluated, refValue.getInnerReferencedType()));
                } else
                    throw new StatementExecutionException(String.format("The RefValue %s is not defined in the heap", value));
            } else
                throw new StatementExecutionException(String.format("%s not of RefType", value));
        } else
            throw new StatementExecutionException(String.format("%s not present in the symTable", varName));
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new HeapWriteStmt(varName, expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException {
        Type typeVariable = typeEnv.lookUp(this.varName);
        Type typeExpression = this.expression.typeCheck(typeEnv);
        //if(typeVariable instanceof ReferenceType)
        if(typeVariable.equals(new RefType(typeExpression)))
            return typeEnv;
        else
            throw new ExpressionException("WRITEHEAP statement: right hand side and left hand side have different types");    }

    @Override
    public String toString() {
        return String.format("WriteHeap(%s, %s)", varName, expression);
    }
}
