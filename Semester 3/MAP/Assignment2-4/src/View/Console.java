package View;

import Controller.Controller;
import Implemented_Exceptions.ADTException;
import Implemented_Exceptions.ExpressionException;
import Implemented_Exceptions.StatementExecutionException;
import Model.MyADTs.MyList;
import Model.Statement.IStmt;

import java.io.IOException;
import java.util.Scanner;

public class Console {
    private Controller ctrl;
    private MyList<IStmt> statements;

    public Console(Controller ctrl, MyList<IStmt> statements){
        this.ctrl = ctrl;
        this.statements = statements;
    }

    private void printMenu(){
        System.out.println("\n=========Toy Language Interpretor=========");
        for (int i = 0; i < statements.getSize(); i++)
        {
            System.out.println(String.format("%d: %s", i, statements.get(i).toString()));
        }
        System.out.println("-1: Exit.");
    }

    private int getInteger(Scanner scanner) throws ExpressionException {
        try {
            return Integer.parseInt((scanner.nextLine()));
        }
        catch (NumberFormatException e){
            throw new ExpressionException("Invalid integer");
        }
    }

    private void infiniteLoop(){
        Scanner scanner = new Scanner(System.in);

        while(true){
            printMenu();
            System.out.print("Choose one oprion: ");
            int option = -1;
            try{
                option = getInteger(scanner);
                if(option == -1) break;
                if(option < statements.getSize()){
                    try{
                        //ctrl.setProgram(new ProgramState(statements.get(option)));
                        ctrl.allSteps();
                    } catch (StatementExecutionException | ExpressionException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (ExpressionException | ADTException e){
                System.out.print(e.getMessage());
            }


        }

        scanner.close();
    }

    public void runApp(){
        infiniteLoop();
    }
}