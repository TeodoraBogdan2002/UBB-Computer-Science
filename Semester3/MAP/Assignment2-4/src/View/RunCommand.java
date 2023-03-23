package View;

import Controller.Controller;
import Implemented_Exceptions.ADTException;
import Implemented_Exceptions.ExpressionException;
import Implemented_Exceptions.StatementExecutionException;

import java.io.IOException;

public class RunCommand extends Command{
    private Controller controller;

    public RunCommand(String key, String description, Controller controller){
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try{
            controller.allSteps();
        } catch (ExpressionException | StatementExecutionException | IOException | ADTException e) {
            System.out.println(e.getMessage());
        }
    }
}