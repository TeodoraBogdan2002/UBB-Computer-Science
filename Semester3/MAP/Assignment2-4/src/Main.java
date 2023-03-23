import Controller.Controller;
import Model.Expressions.*;
import Model.Expressions.ValueExp;
import Model.Expressions.VarExp;
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
import Repository.*;
import View.ExitCommand;
import View.RunCommand;
import View.TextMenu;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        IStmt ex1 = new CompoundStmt(new VarDeclStmt("v", new IntType()),
                new CompoundStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
        MyIStack<IStmt> executionStack = new MyStack<>();
        MyIDictionary<String, Value> symbolTable = new MyDictionary<>();
        MyIList<Value> output = new MyList<>();
        MyIDictionary<String, BufferedReader> fileTable = new MyDictionary<String,BufferedReader>();
        MyIHeap newHeap = new MyHeap();

        PrgState state = new PrgState(executionStack, symbolTable, output, ex1, newHeap, fileTable);
        RepositoryInterface repo1 = new Repository((PrgState) state, "log1.txt");
        Controller ctrl1 = new Controller(repo1);

        IStmt ex2 = new CompoundStmt(new VarDeclStmt("a", new IntType()),
                new CompoundStmt(new VarDeclStmt("b", new IntType()),
                        new CompoundStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)), new
                                ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompoundStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new
                                        IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        MyIStack<IStmt> executionStack2 = new MyStack<>();
        MyIDictionary<String, Value> symbolTable2 = new MyDictionary<>();
        MyIList<Value> output2 = new MyList<>();
        MyIDictionary<String, BufferedReader> fileTable2 = new MyDictionary<String,BufferedReader>();
        MyIHeap newHeap2 = new MyHeap();


        PrgState state2 = new PrgState(executionStack2, symbolTable2, output2, ex2, newHeap2,fileTable2);

        RepositoryInterface repo2 = new Repository(state2, "log2.txt");
        Controller ctrl2 = new Controller(repo2);

        IStmt ex3 = new CompoundStmt(new VarDeclStmt("a", new BoolType()),
                new CompoundStmt(new VarDeclStmt("v", new IntType()),
                        new CompoundStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompoundStmt(new IfStmt(new VarExp("a"),
                                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                                        new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));
        MyIStack<IStmt> executionStack3 = new MyStack<>();
        MyIDictionary<String, Value> symbolTable3 = new MyDictionary<>();
        MyIList<Value> output3 = new MyList<>();
        MyIDictionary<String, BufferedReader> fileTable3 = new MyDictionary<String,BufferedReader>();

        PrgState state3 = new PrgState(executionStack3, symbolTable3, output3, ex3, new MyHeap(),fileTable3);
        RepositoryInterface repo3 = new Repository(state3, "log3.txt");
        Controller ctrl3 = new Controller(repo3);

        IStmt ex4 = new CompoundStmt(new VarDeclStmt("varf", new StringType()),
                new CompoundStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompoundStmt(new OpenFileStatement(new VarExp("varf")),
                                new CompoundStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompoundStmt(new ReadFileStatement(new VarExp("varf"), "varc"),
                                                new CompoundStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompoundStmt(new ReadFileStatement(new VarExp("varf"), "varc"),
                                                                new CompoundStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CloseFileStatement(new VarExp("varf"))))))))));

        PrgState prg4 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex4, new MyHeap(), new MyDictionary<>());
        RepositoryInterface repo4 = new Repository(prg4, "log4.txt");
        Controller ctrl4 = new Controller(repo4);

        IStmt ex5 = new CompoundStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompoundStmt(new NewStatement("v", new ValueExp(new IntValue(20))),
                        new CompoundStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompoundStmt(new NewStatement("a", new VarExp("v")),
                                        new CompoundStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));

        PrgState prg5 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex5, new MyHeap(), new MyDictionary<>());
        RepositoryInterface repo5 = new Repository(prg5, "log5.txt");
        Controller controller5 = new Controller(repo5);

        IStmt ex6 = new CompoundStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompoundStmt(new NewStatement("v", new ValueExp(new IntValue(20))),
                        new CompoundStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompoundStmt(new NewStatement("a", new VarExp("v")),
                                        new CompoundStmt(new PrintStmt(new ReadHeapExpression(new VarExp("v"))),
                                                new PrintStmt(new ArithExp('+',new ReadHeapExpression(new ReadHeapExpression(new VarExp("a"))), new ValueExp(new IntValue(5)))))))));
        PrgState prg6 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex6, new MyHeap(), new MyDictionary<>());
        RepositoryInterface repo6 = new Repository(prg6, "log6.txt");
        Controller controller6 = new Controller(repo6);

        IStmt ex7 = new CompoundStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompoundStmt(new NewStatement("v", new ValueExp(new IntValue(20))),
                        new CompoundStmt( new PrintStmt(new ReadHeapExpression(new VarExp("v"))),
                                new CompoundStmt(new HeapWriteStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp('+', new ReadHeapExpression(new VarExp("v")), new ValueExp(new IntValue(5))))))));
        PrgState prg7 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex7, new MyHeap(), new MyDictionary<>());
        RepositoryInterface repo7 = new Repository(prg7, "log7.txt");
        Controller controller7 = new Controller(repo7);

        IStmt ex8 = new CompoundStmt(new VarDeclStmt("v", new IntType()),
                new CompoundStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompoundStmt(new WhileStatement(new RelationalExpression(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                                new CompoundStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v",new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));

        PrgState prg8 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex8, new MyHeap(), new MyDictionary<>() );
        RepositoryInterface repo8 = new Repository(prg8, "log8.txt");
        Controller controller8 = new Controller(repo8);

        IStmt ex9 = new CompoundStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompoundStmt(new NewStatement("v", new ValueExp(new IntValue(20))),
                        new CompoundStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompoundStmt(new NewStatement("a", new VarExp("v")),
                                        new CompoundStmt(new NewStatement("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExpression(new ReadHeapExpression(new VarExp("a")))))))));

        PrgState prg9 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex9, new MyHeap(), new MyDictionary<>() );
        RepositoryInterface repo9 = new Repository(prg9, "log9.txt");
        Controller controller9 = new Controller(repo9);

//        IStmt ex11 = new CompoundStmt(new VarDeclStmt("v", new IntType()),
//                new CompoundStmt(new VarDeclStmt("a", new RefType(new IntType())),
//                        new CompoundStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
//                                new CompoundStmt(new NewStatement("a", new ValueExp(new IntValue(22))),
//                                        new CompoundStmt(new ForkStmt(new CompoundStmt(new HeapWriteStmt("a", new ValueExp(new IntValue(30))),
//                                                new CompoundStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
//                                                        new CompoundStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExpression(new VarExp("a"))))))),
//                                                new CompoundStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExpression(new VarExp("a")))))))));
//
//        PrgState prg11 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex11, new MyHeap(), new MyDictionary<>());
//        RepositoryInterface repo11 = new Repository(prg11, "log11.txt");
//        Controller controller11 = new Controller(repo11);



        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunCommand("1", ex1.toString(), ctrl1));
        menu.addCommand(new RunCommand("2", ex2.toString(), ctrl2));
        menu.addCommand(new RunCommand("3", ex3.toString(), ctrl3));
        menu.addCommand(new RunCommand("4", ex4.toString(), ctrl4));
        menu.addCommand(new RunCommand("5", ex5.toString(), controller5));
        menu.addCommand(new RunCommand("6", ex6.toString(), controller6));
        menu.addCommand(new RunCommand("7", ex7.toString(), controller7));
        menu.addCommand(new RunCommand("8", ex8.toString(), controller8));
        menu.addCommand(new RunCommand("9", ex9.toString(), controller9));
//        menu.addCommand(new RunCommand("11", ex11.toString(), controller11));



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