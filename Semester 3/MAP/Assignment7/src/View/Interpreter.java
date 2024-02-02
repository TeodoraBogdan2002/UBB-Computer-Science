package View;

import Controller.Controller;
import Implemented_Exceptions.InterpreterException;
import Model.Expressions.*;
import Model.MyADTs.*;
import Model.PrgState.PrgState;
import Model.Statement.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.RefType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;
import Repository.Repository;
import Repository.RepositoryInterface;

import java.io.BufferedReader;
import java.io.IOException;

public class Interpreter {

    public static void main(String[] args) throws InterpreterException, IOException {
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        IStmt ex1 = new CompoundStmt(new VarDeclStmt("v", new IntType()),
                new CompoundStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
        try {
            ex1.typeCheck(new MyDictionary<>());
            MyIStack<IStmt> executionStack = new MyStack<>();
            MyIDictionary<String, Value> symbolTable = new MyDictionary<>();
            MyIList<Value> output = new MyList<>();
            MyIDictionary<String, BufferedReader> fileTable = new MyDictionary<String, BufferedReader>();
            MyIHeap newHeap = new MyHeap();

            PrgState state = new PrgState(executionStack, symbolTable, output, fileTable, newHeap, ex1);
            RepositoryInterface repo1 = new Repository((PrgState) state, "log1.txt");
            Controller ctrl1 = new Controller(repo1);
            menu.addCommand(new RunCommand("1", ex1.toString(), ctrl1));

        } catch (IOException | InterpreterException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex2 = new CompoundStmt(new VarDeclStmt("a", new IntType()),
                new CompoundStmt(new VarDeclStmt("b", new IntType()),
                        new CompoundStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)), new
                                ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompoundStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new
                                        IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        try {
            ex2.typeCheck(new MyDictionary<>());
            MyIStack<IStmt> executionStack2 = new MyStack<>();
            MyIDictionary<String, Value> symbolTable2 = new MyDictionary<>();
            MyIList<Value> output2 = new MyList<>();
            MyIDictionary<String, BufferedReader> fileTable2 = new MyDictionary<String, BufferedReader>();
            MyIHeap newHeap2 = new MyHeap();


            PrgState state2 = new PrgState(executionStack2, symbolTable2, output2, fileTable2, newHeap2, ex2);

            RepositoryInterface repo2 = new Repository(state2, "log2.txt");
            Controller ctrl2 = new Controller(repo2);
            menu.addCommand(new RunCommand("2", ex2.toString(), ctrl2));

        } catch (IOException | InterpreterException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex3 = new CompoundStmt(new VarDeclStmt("a", new BoolType()),
                new CompoundStmt(new VarDeclStmt("v", new IntType()),
                        new CompoundStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompoundStmt(new IfStmt(new VarExp("a"),
                                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                                        new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));
        try {
            ex3.typeCheck(new MyDictionary<>());
            MyIStack<IStmt> executionStack3 = new MyStack<>();
            MyIDictionary<String, Value> symbolTable3 = new MyDictionary<>();
            MyIList<Value> output3 = new MyList<>();
            MyIDictionary<String, BufferedReader> fileTable3 = new MyDictionary<String, BufferedReader>();

            PrgState state3 = new PrgState(executionStack3, symbolTable3, output3, fileTable3, new MyHeap(), ex3);
            RepositoryInterface repo3 = new Repository(state3, "log3.txt");
            Controller ctrl3 = new Controller(repo3);
            menu.addCommand(new RunCommand("3", ex3.toString(), ctrl3));


        } catch (IOException | InterpreterException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        IStmt ex4 = new CompoundStmt(new VarDeclStmt("varf", new StringType()),
                new CompoundStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompoundStmt(new OpenFileStatement(new VarExp("varf")),
                                new CompoundStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompoundStmt(new ReadFileStatement(new VarExp("varf"), "varc"),
                                                new CompoundStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompoundStmt(new ReadFileStatement(new VarExp("varf"), "varc"),
                                                                new CompoundStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CloseFileStatement(new VarExp("varf"))))))))));
        try {
            ex4.typeCheck(new MyDictionary<>());
            PrgState prg4 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex4);
            RepositoryInterface repo4 = new Repository(prg4, "log4.txt");
            Controller ctrl4 = new Controller(repo4);
            menu.addCommand(new RunCommand("4", ex4.toString(), ctrl4));


        } catch (IOException | InterpreterException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex5 = new CompoundStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompoundStmt(new NewStatement("v", new ValueExp(new IntValue(20))),
                        new CompoundStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompoundStmt(new NewStatement("a", new VarExp("v")),
                                        new CompoundStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));

        try {
            ex5.typeCheck(new MyDictionary<>());
            PrgState prg5 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex5);
            RepositoryInterface repo5 = new Repository(prg5, "log5.txt");
            Controller controller5 = new Controller(repo5);
            menu.addCommand(new RunCommand("5", ex5.toString(), controller5));

        } catch (IOException | InterpreterException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex6 = new CompoundStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompoundStmt(new NewStatement("v", new ValueExp(new IntValue(20))),
                        new CompoundStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompoundStmt(new NewStatement("a", new VarExp("v")),
                                        new CompoundStmt(new PrintStmt(new ReadHeapExpression(new VarExp("v"))),
                                                new PrintStmt(new ArithExp('+', new ReadHeapExpression(new ReadHeapExpression(new VarExp("a"))), new ValueExp(new IntValue(5)))))))));

        try {
            ex6.typeCheck(new MyDictionary<>());
            PrgState prg6 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex6);
            RepositoryInterface repo6 = new Repository(prg6, "log6.txt");
            Controller controller6 = new Controller(repo6);
            menu.addCommand(new RunCommand("6", ex6.toString(), controller6));

        } catch (IOException | InterpreterException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex7 = new CompoundStmt(new VarDeclStmt("v", new IntType()),
                new CompoundStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompoundStmt(new WhileStatement(new RelationalExpression(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                                new CompoundStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));

        try {
            ex7.typeCheck(new MyDictionary<>());
            PrgState prg7 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex7);
            RepositoryInterface repo7 = new Repository(prg7, "log7.txt");
            Controller controller7 = new Controller(repo7);
            menu.addCommand(new RunCommand("7", ex7.toString(), controller7));

        } catch (IOException | InterpreterException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex8 = new CompoundStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompoundStmt(new NewStatement("v", new ValueExp(new IntValue(20))),
                        new CompoundStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompoundStmt(new NewStatement("a", new VarExp("v")),
                                        new CompoundStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));


        try {
            ex8.typeCheck(new MyDictionary<>());
            PrgState prg8 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex8);
            RepositoryInterface repo8 = new Repository(prg8, "log8.txt");
            Controller controller8 = new Controller(repo8);
            menu.addCommand(new RunCommand("8", ex8.toString(), controller8));

        } catch (IOException | InterpreterException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex9 = new CompoundStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompoundStmt(new NewStatement("v", new ValueExp(new IntValue(20))),
                        new CompoundStmt(new PrintStmt(new ReadHeapExpression(new VarExp("v"))),
                                new CompoundStmt(new HeapWriteStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp('+', new ReadHeapExpression(new VarExp("v")), new ValueExp(new IntValue(5))))))));

        try {
            ex9.typeCheck(new MyDictionary<>());
            PrgState prg9 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex9);
            RepositoryInterface repo9 = new Repository(prg9, "log9.txt");
            Controller controller9 = new Controller(repo9);
            menu.addCommand(new RunCommand("9", ex9.toString(), controller9));

        } catch (IOException | InterpreterException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }


        IStmt ex10 = new CompoundStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompoundStmt(new NewStatement("v", new ValueExp(new IntValue(20))),
                        new CompoundStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompoundStmt(new NewStatement("a", new VarExp("v")),
                                        new CompoundStmt(new NewStatement("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExpression(new ReadHeapExpression(new VarExp("a")))))))));


        try {
            ex10.typeCheck(new MyDictionary<>());
            PrgState prg10 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex10);
            RepositoryInterface repo10 = new Repository(prg10, "log10.txt");
            Controller controller10 = new Controller(repo10);
            menu.addCommand(new RunCommand("10", ex10.toString(), controller10));


        } catch (IOException | InterpreterException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        IStmt ex11 = new CompoundStmt(new VarDeclStmt("v", new IntType()),
                new CompoundStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompoundStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompoundStmt(new NewStatement("a", new ValueExp(new IntValue(22))),
                                        new CompoundStmt(new ForkStmt(new CompoundStmt(new HeapWriteStmt("a", new ValueExp(new IntValue(30))),
                                                new CompoundStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                        new CompoundStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExpression(new VarExp("a"))))))),
                                                new CompoundStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExpression(new VarExp("a")))))))));


        try {
            ex11.typeCheck(new MyDictionary<>());
            PrgState prg11 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex11);
            RepositoryInterface repo11 = new Repository(prg11, "log11.txt");
            Controller controller11 = new Controller(repo11);
            menu.addCommand(new RunCommand("11", ex11.toString(), controller11));


        } catch (IOException | InterpreterException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        IStmt ex12 = new CompoundStmt(new VarDeclStmt("v", new IntType()),
                new CompoundStmt(new AssignStmt("v", new ValueExp(new IntValue(0))),
                        new CompoundStmt(new RepeatUntilStmt(new CompoundStmt(new ForkStmt(new CompoundStmt(new PrintStmt(new VarExp("v")),
                                new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ValueExp(new IntValue(1))))),
                                new RelationalExpression("==", new VarExp("v"), new ValueExp(new IntValue(3)))), new CompoundStmt(new VarDeclStmt("x", new IntType()),
                                new CompoundStmt(new VarDeclStmt("y", new IntType()),
                                        new CompoundStmt(new AssignStmt("x", new ValueExp(new IntValue(1))), new CompoundStmt(new NopStmt(), new CompoundStmt(new AssignStmt("y", new ValueExp(new IntValue(3))),
                                                new CompoundStmt(new NopStmt(), new PrintStmt(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10)))))))))))));


        ex12.typeCheck(new MyDictionary<>());
        PrgState prg12 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), ex12);
        RepositoryInterface repo12 = new Repository(prg12, "log12.txt");
        Controller controller12 = new Controller(repo12);
        menu.addCommand(new RunCommand("12", ex12.toString(), controller12));


        menu.show();
    }
}
/*
IStmt ex7 = new CompoundStmt(
                new VarDeclStmt("v",new RefType(new IntType())),
                new CompoundStmt(
                        new NewStatement("v",new ValueExp(new IntValue(20))),
                        new CompoundStmt(
                                new PrintStmt(new ReadHeapExpression(new VarExp("v"))), new CompoundStmt(
                                new VarDeclStmt("a",new RefType(new RefType(new  IntType()))), new CompoundStmt(
                                new NewStatement("a",new VarExp("v")),new CompoundStmt(
                                new HeapWriteStmt("v",new ValueExp(new IntValue(30))),
                                new PrintStmt(new ArithExp('+' ,new ReadHeapExpression(new ReadHeapExpression( new VarExp("a"))),new ValueExp(new IntValue(5))))))))));
 */
