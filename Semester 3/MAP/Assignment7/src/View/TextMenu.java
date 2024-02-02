package View;


import Implemented_Exceptions.InterpreterException;
import Model.MyADTs.MyDictionary;
import Model.MyADTs.MyIDictionary;

import java.util.Scanner;

public class TextMenu {
    private MyIDictionary<String, Command> commands;

    public TextMenu() {
        this.commands = new MyDictionary<>();
    }

    public void addCommand(Command command) {
        this.commands.put(command.getKey(), command);
    }

    private void printMenu() {
        for (Command command: commands.values()) {
            String line = String.format("%4s: %s", command.getKey(), command.getDescription());
            System.out.println(line);
        }
    }

    public void show() throws InterpreterException
    {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            System.out.print("Input the option: ");
            String key = scanner.nextLine();
            Command com = commands.lookUp(key);
            if (com == null) {
                System.out.println("Invalid Option");
                continue;
            }

            com.execute();
        }
    }
}