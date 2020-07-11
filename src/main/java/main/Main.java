package main;

import Server.Server;
import database.DataBase;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Project team 34");
        System.out.println("Server is starting ...");
        Scanner scanner = new Scanner(System.in);
        DataBase.createNewTablesToStart();
        DataBase.importAllData();
        try {
            Server server = new Server();
            server.start();
            System.out.println("Server is up");
        } catch (IOException e) {
            System.err.println("Error During starting server");
            scanner.close();
            System.exit(1);
        }
        while (true) {
            System.out.print("server :: command > ");
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("shutdown")) {
                System.exit(0);
            }
        }
    }
}
