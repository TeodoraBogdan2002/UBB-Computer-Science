package gui;


import Controller.Controller;
import Implemented_Exceptions.InterpreterException;
import Model.Expressions.*;
import Model.MyADTs.*;
import Model.PrgState.PrgState;
import Model.Statement.*;
import Model.Types.*;
import Model.Types.BoolType;
import Model.Values.*;
import Repository.Repository;
import Repository.RepositoryInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProgramChooserContrl {
    private ProgramExecutorContrl programExecutorController;

    public void setProgramExecutorController(ProgramExecutorContrl programExecutorController) {
        this.programExecutorController = programExecutorController;
    }
    @FXML
    private ListView<IStmt> programsListView;

    @FXML
    private Button displayButton;

    @FXML
    public void initialize() {
        programsListView.setItems(getAllStatements());
        programsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void displayProgram(ActionEvent actionEvent) {
        IStmt selectedStatement = programsListView.getSelectionModel().getSelectedItem();
        if (selectedStatement == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error encountered!");
            alert.setContentText("No statement selected!");
            alert.showAndWait();
        } else {
            int id = programsListView.getSelectionModel().getSelectedIndex();
            try {
                selectedStatement.typeCheck(new MyDictionary<>());
                PrgState programState = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), selectedStatement);
                RepositoryInterface repository = new Repository(programState, "log" + (id + 1) + ".txt");
                Controller controller = new Controller(repository);
                programExecutorController.setController(controller);
            } catch (InterpreterException | IOException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error encountered!");
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    private ObservableList<IStmt> getAllStatements() {
        List<IStmt> allStatements = new ArrayList<>();

        IStmt ex1 = new CompoundStmt(new VarDeclStmt("v", new IntType()),
                new CompoundStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));

        allStatements.add(ex1);

        IStmt ex2 = new CompoundStmt(new VarDeclStmt("a", new IntType()),
                new CompoundStmt(new VarDeclStmt("b", new IntType()),
                        new CompoundStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)), new
                                ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompoundStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new
                                        IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        allStatements.add(ex2);

        IStmt ex3 = new CompoundStmt(new VarDeclStmt("a", new BoolType()),
                new CompoundStmt(new VarDeclStmt("v", new IntType()),
                        new CompoundStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompoundStmt(new IfStmt(new VarExp("a"),
                                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                                        new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));
        allStatements.add(ex3);

        IStmt ex4 = new CompoundStmt(new VarDeclStmt("varf", new StringType()),
                new CompoundStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompoundStmt(new OpenFileStatement(new VarExp("varf")),
                                new CompoundStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompoundStmt(new ReadFileStatement(new VarExp("varf"), "varc"),
                                                new CompoundStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompoundStmt(new ReadFileStatement(new VarExp("varf"), "varc"),
                                                                new CompoundStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CloseFileStatement(new VarExp("varf"))))))))));
        allStatements.add(ex4);

        IStmt ex5 = new CompoundStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompoundStmt(new NewStatement("v", new ValueExp(new IntValue(20))),
                        new CompoundStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompoundStmt(new NewStatement("a", new VarExp("v")),
                                        new CompoundStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));
        allStatements.add(ex5);

        IStmt ex6 = new CompoundStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompoundStmt(new NewStatement("v", new ValueExp(new IntValue(20))),
                        new CompoundStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompoundStmt(new NewStatement("a", new VarExp("v")),
                                        new CompoundStmt(new PrintStmt(new ReadHeapExpression(new VarExp("v"))),
                                                new PrintStmt(new ArithExp('+', new ReadHeapExpression(new ReadHeapExpression(new VarExp("a"))), new ValueExp(new IntValue(5)))))))));

        allStatements.add(ex6);

        IStmt ex7 = new CompoundStmt(new VarDeclStmt("v", new IntType()),
                new CompoundStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompoundStmt(new WhileStatement(new RelationalExpression(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                                new CompoundStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));
        allStatements.add(ex7);

        IStmt ex8 = new CompoundStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompoundStmt(new NewStatement("v", new ValueExp(new IntValue(20))),
                        new CompoundStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompoundStmt(new NewStatement("a", new VarExp("v")),
                                        new CompoundStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));
        allStatements.add(ex8);

        IStmt ex9 = new CompoundStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompoundStmt(new NewStatement("v", new ValueExp(new IntValue(20))),
                        new CompoundStmt( new PrintStmt(new ReadHeapExpression(new VarExp("v"))),
                                new CompoundStmt(new HeapWriteStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp('+', new ReadHeapExpression(new VarExp("v")), new ValueExp(new IntValue(5))))))));
        allStatements.add(ex9);

        IStmt ex10 = new CompoundStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompoundStmt(new NewStatement("v", new ValueExp(new IntValue(20))),
                        new CompoundStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompoundStmt(new NewStatement("a", new VarExp("v")),
                                        new CompoundStmt(new NewStatement("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExpression(new ReadHeapExpression(new VarExp("a")))))))));
        allStatements.add(ex10);

        IStmt ex11 = new CompoundStmt(new VarDeclStmt("v", new IntType()),
                new CompoundStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompoundStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompoundStmt(new NewStatement("a", new ValueExp(new IntValue(22))),
                                        new CompoundStmt(new ForkStmt(new CompoundStmt(new HeapWriteStmt("a", new ValueExp(new IntValue(30))),
                                                new CompoundStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                        new CompoundStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExpression(new VarExp("a"))))))),
                                                new CompoundStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExpression(new VarExp("a")))))))));
        allStatements.add(ex11);

        IStmt ex12 = new CompoundStmt(new VarDeclStmt("v", new IntType()),
                new CompoundStmt(new AssignStmt("v", new ValueExp(new IntValue(0))),
                        new CompoundStmt(new RepeatUntilStmt(new CompoundStmt(new ForkStmt(new CompoundStmt(new PrintStmt(new VarExp("v")),
                                        new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                        new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ValueExp(new IntValue(1))))),
                                new RelationalExpression("==", new VarExp("v"), new ValueExp(new IntValue(3)))), new CompoundStmt(new VarDeclStmt("x", new IntType()),
                                         new CompoundStmt(new VarDeclStmt("y", new IntType()),
                                                new CompoundStmt(new AssignStmt("x", new ValueExp(new IntValue(1))),new CompoundStmt(new NopStmt(),new CompoundStmt(new AssignStmt("y", new ValueExp(new IntValue(3))),
                                                        new CompoundStmt(new NopStmt(),new PrintStmt(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10)))))))))))));

        allStatements.add(ex12);

        return FXCollections.observableArrayList(allStatements);
    }
}
