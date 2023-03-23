package Model.Expressions;
import Implemented_Exceptions.ADTException;
import Implemented_Exceptions.ExpressionException;
import Model.MyADTs.MyIDictionary;
import Model.MyADTs.MyIHeap;
import Model.Types.Type;
import Model.Values.Value;

public interface Expression {
    Value evaluate(MyIDictionary<String,Value> table, MyIHeap heap) throws ExpressionException, ADTException;
    Expression deepCopy();

    Type typeCheck (MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException;


}
