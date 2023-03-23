package View;

import Controller.Controller;
import Model.*;
import Exceptions.MyException;

import java.util.Objects;
import java.util.Scanner;

public class View {
    private final Controller controller;

    public View(Controller controller){
        this.controller=controller;
    }

    private void printMenuOptions(){
        System.out.println("1 Add a vegetable.");
        System.out.println("2 Delete a vegetable.");
        System.out.println("3 Print all vegetables.");
        System.out.println("4 Print the vegetables that weigh >0.2 kg.");
        System.out.println("0 Exit.");
    }


    private void printVegetables() {
        Vegetables[] vegetables = this.controller.repo.getVegetables();
        if (this.controller.repo.getSize() == 0) {
            System.out.println("No vegetables!");
        } else {
            int index;
            for (index = 0; index < this.controller.repo.getSize(); index++) {
                System.out.println(vegetables[index].toString());
            }
        }
    }

    private void addV() throws MyException {
        System.out.println("Enter type of vegetable: ");
        Scanner readT=new Scanner(System.in);
        String type = readT.nextLine();
        if (Objects.equals(type, "eggplant") || Objects.equals(type, "pepper") || Objects.equals(type, "tomato")) {
            System.out.println("Enter weight: ");
            Scanner readWeight = new Scanner(System.in);
            float weight = readWeight.nextFloat();
            this.controller.add(type,weight);
        }
        else {
            throw new MyException("Invalid type!");
        }
    }

    private void deleteV() throws MyException{
        if (this.controller.repo.getSize() != 0) {
            System.out.println("Enter index: ");
            Scanner readIndex = new Scanner(System.in);
            int index = readIndex.nextInt();
            if (index - 1 >= 0 && index - 1 < this.controller.repo.getSize()) {
                this.controller.delete(index - 1);
            } else {
                throw new MyException("Invalid index!");
            }
        } else {
            throw new MyException("There is nothing left to remove!");
        }
    }

    private void FilterVegetables()
    {
        if (this.controller.repo.getSize() != 0) {
            Vegetables[] filteredV = this.controller.getVbyWeight();
            if (filteredV.length == 0)
            {
                System.out.println("There are no vegetables that have the weight greater than  0.2 kg.");
            }
            else
            {
                int index;
                for (index = 0; index < filteredV.length; index++)
                {
                    System.out.println(filteredV[index].toString());
                }
            }
        }
    }

    public void start() throws MyException {
        boolean k=true;
        while(k){
            try{
                printMenuOptions();
                Scanner readOpt=new Scanner(System.in);
                int opt=readOpt.nextInt();
                if(opt==1){
                    this.addV();
                }
                else if(opt==2){
                    this.deleteV();
                }
                else if(opt==3){
                    this.printVegetables();
                }
                else if(opt==4){
                    this.FilterVegetables();
                }
                else if(opt==0){
                    k=false;
                }
                else{
                    System.out.println("Invalid input!");
                }
            } catch (MyException myException){
                System.out.println(myException.getMessage());
            }
        }
    }
}
