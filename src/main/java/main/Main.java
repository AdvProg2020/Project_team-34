package main;

import database.DataBase;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Project team 34");
        System.out.println("Server is starting ...");
        Scanner scanner = new Scanner(System.in);
        DataBase.createNewTablesToStart();
        DataBase.importAllData();

        System.out.println("Server is up.");
        while (true) {
            System.out.print("Server :: command > ");
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("force shutdown")) {
                scanner.close();
                System.exit(0);
            }
        }
    }
}
