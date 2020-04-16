import database.ProductDateBase;
import menu.mainMenu.MainMenu;
import menu.menuAbstract.Menu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Project_team-34");
        ProductDateBase.createNewTable();
        run();
    }

    private static void run() {
        MainMenu mainMenu = new MainMenu();
        mainMenu.show();
        mainMenu.execute();
    }
}
