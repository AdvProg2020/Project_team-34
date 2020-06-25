package main;

import database.DataBase;
import menu.mainMenu.MainMenu;

public class ConsoleMenu {

    public static void main(String[] args) {
        DataBase.createNewTablesToStart();
        DataBase.importAllData();
        run();
    }

    private static void run() {
        MainMenu mainMenu = new MainMenu();
        mainMenu.show();
        mainMenu.execute();
    }
}
