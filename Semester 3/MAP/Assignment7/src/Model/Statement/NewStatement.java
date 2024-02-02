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

public class NewStatement implements IStmt{
    private final String varName;
    private final Expression expression;

    public NewStatement(String varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState programstate) throws InterpreterException {

        MyIDictionary<String, Value> symbolTable = programstate.getSymTable();
        MyIHeap heap = programstate.getHeap();

        // check if the variable has been defined
        if(symbolTable.isDefined(varName))
        {
            // check if its type is reference
            Type typeVariable = (symbolTable.lookUp(varName)).getType();
            //if(typeVariable.equals(new ReferenceType(new IntType())))
            if(typeVariable instanceof RefType)
            {
                // evaluate the expression and check if the type of the value and the location type
                // of the reference variable match
                Value value = expression.evaluate(symbolTable, heap);
                RefType referenceVariable = (RefType) typeVariable;
                if(value.getType().equals(referenceVariable.getInner()))
                {
                    // create a new entry in the Heap Table
                    //heap.add(value);
                    // update in the symbol table the reference value associated to variableName
                    RefValue newReferenceValue = new RefValue(heap.add(value), value.getType());
                    symbolTable.update(varName, newReferenceValue);
                }
                else throw new InterpreterException("The type of the variable and the type of the expression do not match");
            }
            else
                throw new InterpreterException("The type of the variable is not reference type");
        }
        else
            throw new InterpreterException("The variable name is not defined in the symbol table");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewStatement(varName, expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        Type typeVariable = typeEnv.lookUp(this.varName);
        Type typeExpression = this.expression.typeCheck(typeEnv);
        if(typeVariable.equals(new RefType(typeExpression)))
            return typeEnv;
        else
            throw new InterpreterException("NEW statement: right hand side and left hand side have different types");
    }

    @Override
    public String toString() {
        return String.format("New(%s, %s)", varName, expression);
    }
}
