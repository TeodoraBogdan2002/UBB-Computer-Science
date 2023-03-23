//package View;
//
//import Controller.Controller;
//import Implemented_Exceptions.ADTException;
//import Implemented_Exceptions.ImplException;
//import Implemented_Exceptions.StatementExecutionException;
//import Model.MyADTs.*;
//import Model.Expressions.*;
//import Model.PrgState.PrgState;
//import Model.Statement.*;
//import Model.Types.*;
//import Model.Values.BoolValue;
//import Model.Values.IntValue;
//import Model.Values.Value;
//import Repository.RepositoryInterface;
//import Repository.Repository;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.util.Objects;
//import java.util.Scanner;
//
//public class View {
//
//    private void runStatement(IStmt statement) throws ImplException, ADTException, StatementExecutionException, IOException {
//        MyIStack<IStmt> executionStack = new MyStack<>();
//        MyIDictionary<String, Value> symbolTable = new MyDictionary<>();
//        MyIList<Value> output = new MyList<>();
//
//        PrgState state = new PrgState(executionStack, symbolTable, output, statement);
//
//        RepositoryInterface repository = new Repository(state);
//        Controller controller = new Controller(repository);
//
//        System.out.println("Do you want to display the steps?[y/n]");
//        Scanner readOption = new Scanner(System.in);
//        String option = readOption.next();
//        controller.setDisplayFlag(Objects.equals(option, "y"));
//
//        controller.allSteps();
//        System.out.println("Result is" + state.getOutput().toString());
//    }
//
//    private void runProgram1() throws ImplException, ADTException, StatementExecutionException, IOException {
//        IStmt ex1 = new CompoundStmt(new VarDeclStmt("v", new IntType()),
//                new CompoundStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
//                        new PrintStmt(new VarExp("v"))));
//        runStatement(ex1);
//    }
//
//    private void runProgram2() throws ImplException, ADTException, StatementExecutionException, IOException {
//        IStmt ex2 = new CompoundStmt(new VarDeclStmt("a", new IntType()),
//                new CompoundStmt(new VarDeclStmt("b", new IntType()),
//                        new CompoundStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)), new
//                                ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
//                                new CompoundStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new
//                                        IntValue(1)))), new PrintStmt(new VarExp("b"))))));
//        runStatement(ex2);
//    }
//
//    private void runProgram3() throws ImplException, ADTException, StatementExecutionException, IOException {
//        IStmt ex3 = new CompoundStmt(new VarDeclStmt("a", new BoolType()),
//                new CompoundStmt(new VarDeclStmt("v", new IntType()),
//                        new CompoundStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
//                                new CompoundStmt(new IfStmt(new VarExp("a"),
//                                        new AssignStmt("v", new ValueExp(new IntValue(2))),
//                                        new AssignStmt("v", new ValueExp(new IntValue(3)))),
//                                        new PrintStmt(new VarExp("v"))))));
//        runStatement(ex3);
//    }
//
//    public void start() throws ImplException, ADTException, IOException, StatementExecutionException {
//        boolean done = false;
//        while (!done) {
//            showMenu();
//            Scanner readOption = new Scanner(System.in);
//            int option = readOption.nextInt();
//            if (option == 0) {
//                done = true;
//            } else if (option == 1) {
//                runProgram1();
//            } else if (option == 2) {
//                runProgram2();
//            } else if (option == 3) {
//                runProgram3();
//            } else {
//                System.out.println("Invalid input!");
//            }
//        }
//    }
//
//    private void showMenu() {
//        System.out.println("MENU: ");
//        System.out.println("0. Exit.");
//        System.out.println("1. Run the first program: \n\tint v;\n\tv=2;\n\tPrint(v)");
//        System.out.println("2. Run the second program: \n\tint a;\n\tint b;\n\ta=2+3*5;\n\tb=a+1;\n\tPrint(b)");
//        System.out.println("3. Run the third program: \n\tbool a;\n\tint v;\n\ta=true;\n\t(If a Then v=2 Else v=3);\n\tPrint(v)");
//        System.out.println("Choose an option: ");
//    }
//
//}