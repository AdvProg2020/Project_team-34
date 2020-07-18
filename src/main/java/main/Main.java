package main;

import database.DataBase;
import database.WageDataBase;
import server.Server;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        WageDataBase.createNewTable();
//        WageDataBase.delete();
//        WageDataBase.update(56, 89);
//        WageDataBase.update(4, 5);
//        WageDataBase.delete();
//        System.out.println(WageDataBase.getMinimum());
        WageDataBase.createNewTable();
        System.out.println("Project team 34");
        System.out.println("Server is starting ...");
        Scanner scanner = new Scanner(System.in);
        DataBase.createNewTablesToStart();
        DataBase.importAllData();
        try {
            Server server = new Server();
            server.start();
        } catch (IOException e) {
            System.err.println("Error During starting server");
            scanner.close();
            System.exit(1);
        }
        while (true) {
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("shutdown")) {
                System.exit(0);
            } else {
                System.err.println("Invalid command.");
            }
        }
    }
}
