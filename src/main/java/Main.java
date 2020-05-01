import account.Customer;
import account.Supplier;
import exceptionalMassage.ExceptionalMassage;
import menu.mainMenu.MainMenu;

public class Main {
    public static void main(String[] args) {
        System.out.println("Project_team-34");
        run();
    }

    private static void run() {
        MainMenu mainMenu = new MainMenu();
        mainMenu.show();
        mainMenu.execute();
    }
}
