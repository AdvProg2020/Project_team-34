package main;

import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import server.Server;
import database.DataBase;
import server.communications.Response;

import javax.naming.ldap.Control;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        DataBase.createNewTablesToStart();
//        DataBase.importAllData();
//        Controller controller = new Controller();
//        Response response = null;
//        try {
//            response = controller.getAccountController().controlLogin("i", "k4j");
//        } catch (ExceptionalMassage exceptionalMassage) {
//            exceptionalMassage.printStackTrace();
//        }
//        String string = response.convertResponseToJsonString();
//        System.out.println(string);
//        Response.convertJsonStringToResponse(string);
//        System.out.println(response.getStatus());
//        System.out.println(response.getContent());
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
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("shutdown")) {
                System.exit(0);
            } else {
                System.err.println("Invalid command.");
            }
        }
    }
}
