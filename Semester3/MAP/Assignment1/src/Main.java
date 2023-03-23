import Controller.Controller;
import Repository.Repository;
import View.View;

public class Main {
    public static void main(String[] args) throws Exception {
        Repository repo=new Repository();
        Controller controller = new Controller(repo);
        View view = new View(controller);
        controller.add("tomato", 0.750F);
        controller.add("tomato", 0.180F);

        controller.add("eggplant", 0.950F);
        controller.add("eggplant", 1.150F);
        controller.add("eggplant", 1.2F);

        controller.add("pepper", 0.120F);
        controller.add("pepper", 0.08F);



        view.start();
    }
}