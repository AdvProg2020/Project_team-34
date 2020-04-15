package menu;

import menu.loginMenu.LoginMenu;
import menu.menuAbstract.Menu;

public class MainMenu extends Menu {
    public MainMenu() {
        super("Main Menu", null);

        menusIn.put("login/register", new LoginMenu(this));
        menuForShow.add("Login Register");
    }
}
