package menu;

import java.util.regex.Matcher;

public class LoginMenu extends Menu {
    public LoginMenu(Menu parentMenu) {
        super("Login/Register Menu", parentMenu);
        Runnable login = new Runnable() {
            @Override
            public void run() {

            }
        };
        commandsIn.put("login \\w+", login);
        commandForShow.add("Login");
        Runnable register = new Runnable() {
            @Override
            public void run() {

            }
        };
        commandsIn.put("create account (customer|supplier|supervisor) \\w+", register);
        commandForShow.add("Create Account");
    }
}
