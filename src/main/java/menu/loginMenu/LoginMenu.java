package menu.loginMenu;

import menu.menuAbstract.Menu;

import java.util.Scanner;
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
            }

            @Override
            public void execute() {
                String regex = "^login (\\w+)$";
                Matcher usernameMatcher = getMatcher(command, regex);
                if(usernameMatcher.find()) {
                    System.out.println("Enter your password:");
                    String passWord = scanner.nextLine();
                    //check the password and username with controller
                    parentMenu.show();
                    parentMenu.execute();
                }
            }
        };
        menusIn.put("^login \\w+$", login);
        menuForShow.add("Login");

        Menu register = new Menu("Create Account", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^create account (customer|supplier|supervisor) \\w+$", register);
        menuForShow.add("Create Account");
    }

}
