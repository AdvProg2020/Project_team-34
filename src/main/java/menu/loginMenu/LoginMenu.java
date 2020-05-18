package menu.loginMenu;

import exceptionalMassage.ExceptionalMassage;
import menu.menuAbstract.Menu;

import java.util.regex.Matcher;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class LoginMenu extends Menu {
    public LoginMenu(Menu parentMenu) {
        super("Login/Register Menu", parentMenu);
        Menu login = new Menu("Login", this) {
            @Override
            public void show() {
                System.out.println("Login:");
            }

            @Override
            public void execute() {
                Matcher commandMatcher = getMatcher(parentMenu.command, "^login (\\w+)$");
                commandMatcher.find();
                String username = commandMatcher.group(1);
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                try {
                    controller.getAccountController().controlLogin(username, password);
                } catch (ExceptionalMassage e) {
                    System.out.println(e.getMessage());
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^login \\w+$", login);
        menuForShow.add("Login");

        Menu register = new Menu("Create Account", this) {
            @Override
            public void show() {
                System.out.println("Create Account:");
            }

            @Override
            public void execute() {
                Matcher commandMatcher = getMatcher(parentMenu.command, "^create account (customer|supplier|supervisor) (\\w+)$");
                commandMatcher.find();
                String username = commandMatcher.group(2);
                String type = commandMatcher.group(1);
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                System.out.print("Enter family name: ");
                String familyName = scanner.nextLine();
                System.out.print("Enter email: ");
                String email = scanner.nextLine();
                System.out.print("Enter phone number: ");
                String phoneNumber = scanner.nextLine();
                String companyName = null;
                if (type.equals("supplier")) {
                    System.out.print("Enter company name: ");
                    companyName = scanner.nextLine();
                }
                int credit = 0;
                if (type.equals("customer")) {
                    System.out.print("Enter credit: ");
                    credit = scanner.nextInt();
                    String junk = scanner.nextLine();
                }
                try {
                    controller.getAccountController().controlCreateAccount(username, type, name, familyName, email, phoneNumber, password, credit, companyName);
                } catch (ExceptionalMassage e) {
                    System.out.println(e.getMessage());
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^create account (customer|supplier|supervisor) \\w+$", register);
        menuForShow.add("Create Account");
    }

}
