package Model.Statement;

import Implemented_Exceptions.InterpreterException;
import Model.Expressions.Expression;
import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIHeap;
import Model.PrgState.PrgState;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseFileStatement implements IStmt {
    private Expression expression;

    public CloseFileStatement(Expression expression) {
        this.expression = expression;
    }


    @Override
    public String toString() {
        return "close(" + expression.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new CloseFileStatement(expression.deepCopy());
    }


    @Override
    public PrgState execute(PrgState programstate) throws InterpreterException
    {
        MyIDictionary<String, Value> symbolTable = programstate.getSymTable();
        MyIDictionary<String, BufferedReader> fileTable = programstate.getFileTable();
        MyIHeap heap = programstate.getHeap();

        // evaluate the expression
        Value value = this.expression.evaluate(symbolTable, heap);
        // check if it is a String
        if(value.getType().equals(new StringType()))
        {
            StringValue fileName = (StringValue) value;
            // check if the file is defined in the file table
            if(fileTable.isDefined(String.valueOf(fileName)))
            {
                BufferedReader reader = fileTable.lookUp(String.valueOf(fileName));
                // close the file
                try{
                    reader.close();
                }
                catch (IOException exception) { throw new InterpreterException("Error at closing the file"); }

                // delete the entry in the file table
                fileTable.remove(String.valueOf(fileName));
            }
            else
                throw new InterpreterException("string value does not exist in the file table");
        }
        else
            throw new InterpreterException("the type is not string");

        return null;
    }


    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        Type typeExpression = this.expression.typeCheck(typeEnv);
        if(typeExpression.equals(new StringType()))
            return typeEnv;
        else
            throw new InterpreterException("The expression of CLOSEFILESTATEMENT has not the string type");

    }
}