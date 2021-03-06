package menu.mainMenu;

import menu.menuAbstract.Menu;
import menu.products.AllProductsMenu;
import menu.products.SalesMenu;

public class MainMenu extends Menu {
    public MainMenu() {
        super("kooft.Main Menu", null);

        menusIn.put("^products$", new AllProductsMenu(this));
        menuForShow.add("Products");

        menusIn.put("^offs$", new SalesMenu(this));
        menuForShow.add("Offs");

    }
}
