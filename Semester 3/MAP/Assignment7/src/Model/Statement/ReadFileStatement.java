package Model.Statement;

import Implemented_Exceptions.InterpreterException;
import Model.Expressions.Expression;
import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIHeap;
import Model.PrgState.PrgState;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStmt {
    private Expression expression;
    private String variableName;

    public ReadFileStatement(Expression expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }


    @Override
    public String toString() {
        return "read(" + expression.toString() + "," + variableName + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFileStatement(expression.deepCopy(), variableName);    }

    @Override
    public PrgState execute(PrgState programstate) throws InterpreterException, IOException
    {
        MyIDictionary<String, Value> symbolTable = programstate.getSymTable();
        MyIDictionary<String, BufferedReader> fileTable = programstate.getFileTable();
        MyIHeap heap = programstate.getHeap();

        // check if the variable which will store the read line is defined in the symbol table
        if(symbolTable.isDefined(variableName))
        {
            // check if the variable type is int
            if(symbolTable.lookUp(variableName).getType().equals(new IntType()))
            {
                // evaluate the expression
                Value value = this.expression.evaluate(symbolTable, heap);
                // check whether the value of the expression is a string
                if(value.getType().equals(new StringType()))
                {
                    // cast from ValueInterface to StringValue
                    StringValue fileName = (StringValue) value;
                    // get the reader associated to the fileName
                    BufferedReader reader = null;

                    if(fileTable.isDefined(String.valueOf(fileName)))
                        reader = fileTable.lookUp(String.valueOf(fileName));
                    else
                        throw new InterpreterException("file does not exist in the file table");

                    // read a line
                    String readLine = reader.readLine();
                    IntValue readLineAsIntValue = null;
                    if(readLine == null)
                        readLineAsIntValue = new IntValue(0);
                    else
                    {
                        int readInt = Integer.parseInt((readLine));
                        readLineAsIntValue = new IntValue(readInt);
                    }

                    // update the symbol table such that variableName is mapped to the readLineAsIntValue
                    symbolTable.update(variableName, readLineAsIntValue);

                }
                else
                    throw new InterpreterException("the value is not a string");
            }
            else
                throw new InterpreterException("the variable type is different form int");
        }
        else
            throw new InterpreterException("the variable is not defined in the symbol table");

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        Type typeVariable = typeEnv.lookUp(this.variableName);
        Type typeExpression = this.expression.typeCheck(typeEnv);
        if(typeVariable.equals(new IntType()))
            if(typeExpression.equals(new StringType()))
                return typeEnv;
            else
                throw new InterpreterException("The expression of OPENRFILE has not the string type");
        else
            throw new InterpreterException("The variable is not an integer");
    }
}