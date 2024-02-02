package View;

import Controller.Controller;
import Implemented_Exceptions.InterpreterException;

public class RunCommand extends Command{
    private Controller controller;

    public RunCommand(String key, String description, Controller controller){
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try{
            controller.allStep();
        } catch (InterpreterException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}