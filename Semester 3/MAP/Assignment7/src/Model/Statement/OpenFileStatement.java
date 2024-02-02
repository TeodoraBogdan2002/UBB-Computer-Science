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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
    public PrgState execute(PrgState programstate) throws InterpreterException, FileNotFoundException {
        MyIDictionary<String, Value> symbolTable = programstate.getSymTable();
        MyIDictionary<String, BufferedReader> fileTable = programstate.getFileTable();
        MyIHeap heap = programstate.getHeap();

        // evaluate the expression
        Value value = this.expression.evaluate(symbolTable, heap);
        // check if it is a StringValue
        if(value.getType().equals(new StringType()))
        {
            StringValue fileName = (StringValue) value;
            // check if the file has been defined in the file table before
            if(!fileTable.isDefined(String.valueOf(fileName)))
            {
                BufferedReader reader = null;

                try{
                    reader = new BufferedReader(new FileReader(fileName.getValue()));
                }
                catch (FileNotFoundException exception) { throw new InterpreterException("file does not exist"); } finally {
                    if(reader != null)
                    {
                        // create a new entrance in fileTable which maps the stringValue and the reader(BufferedReader class)
                        fileTable.put(String.valueOf(fileName), reader);
                    }
                }
            }
            else
                throw new InterpreterException("string value already exists in the file table");
        }
        else
            throw new InterpreterException("the type is not string");

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new OpenFileStatement(expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws InterpreterException {
        Type typeExpression = this.expression.typeCheck(typeEnv);
        if(typeExpression.equals(new StringType()))
            return typeEnv;
        else
            throw new InterpreterException("The expression of OPENRFILE has not the string type");
    }
}