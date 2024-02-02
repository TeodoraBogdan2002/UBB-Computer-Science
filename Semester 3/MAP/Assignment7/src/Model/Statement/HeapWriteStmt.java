package Model.Statement;

import Implemented_Exceptions.InterpreterException;
import Model.Expressions.Expression;
import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIHeap;
import Model.PrgState.PrgState;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class HeapWriteStmt implements IStmt {
    private final String varName;
    private final Expression expression;

    public HeapWriteStmt(String varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return String.format("WriteHeap(%s, %s)", varName, expression);
    }

    @Override
    public IStmt deepCopy() {
        return new HeapWriteStmt(varName, expression.deepCopy());
    }


    @Override
    public PrgState execute(PrgState programstate) throws InterpreterException {
        MyIDictionary<String, Value> symbolTable = programstate.getSymTable();
        MyIHeap heap = programstate.getHeap();

        // check if the variableName is defined in the symbol table
        if (symbolTable.isDefined(varName)) {
            // check if its type is a reference type
            Type typeVariable = (symbolTable.lookUp(varName)).getType();
            if (typeVariable instanceof RefType) {
                // check if the address from the reference value associated to variableName in symbol table
                // is a key in heap table
                RefValue valueVariableName = (RefValue) symbolTable.lookUp(varName);
                int address = valueVariableName.getHeapAddress();
                if (heap.containsKey(address)) {
                    // evaluate the expression
                    Value valueExpression = this.expression.evaluate(symbolTable, heap);
                    // check if the type of the valueExpression and the location type of
                    // the reference variable match
                    RefType referenceTypeValueVariableName = (RefType) typeVariable;
                    Type innerTypeValueVariableName = referenceTypeValueVariableName.getInner();
                    if (valueExpression.getType().equals(innerTypeValueVariableName)) {
                        // update in the heap the address from variableName with the result of the expression evaluation
                        heap.update(address, valueExpression);
                    } else
                        throw new InterpreterException("The type of the variable and the type of the expression do not match");
                } else throw new InterpreterException("the address does not exist in the heap");
            } else throw new InterpreterException("the variable type is not a reference type");
        } else throw new InterpreterException("the variable is not defined in the symbol table");

        return null;
    }


    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        Type typeVariable = typeEnv.lookUp(this.varName);
        Type typeExpression = this.expression.typeCheck(typeEnv);

        if (typeVariable.equals(new RefType(typeExpression)))
            return typeEnv;
        else
            throw new InterpreterException("WRITEHEAP statement: right hand side and left hand side have different types");
    }

}
