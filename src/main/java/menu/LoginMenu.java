package menu;

import java.util.regex.Matcher;

public class LoginMenu extends Menu {
    public LoginMenu(Menu parentMenu) {
        super("Login/Register Menu", parentMenu);
        Menu login = new Menu("Login", this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
        menusIn.put("^login \\w+$", login);
        menuForShow.add("Login");
        Menu register = new Menu("Create Account", this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
        menusIn.put("^create account (customer|supplier|supervisor) \\w+$", register);
        menuForShow.add("Create Account");
    }
}
