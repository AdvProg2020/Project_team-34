package menu.profile;

import menu.Menu;
import menu.ViewCartMenu;
import menu.ViewOrderMenuForCustomer;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class CustomerProfileMenu extends ProfileMenu {
    public CustomerProfileMenu(Menu parentMenu) {
        super("Customer Profile Menu", parentMenu);

        menusIn.put("^view cart$", new ViewCartMenu(this));
        menuForShow.add("View Cart");

        menusIn.put("^view orders$", new ViewOrderMenuForCustomer(this));
        menuForShow.add("View Orders");

        Menu ViewBalance = new Menu("View Balance", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^view balance$", ViewBalance);
        menuForShow.add("View Balance");

        Menu ViewDiscountCodes = new Menu("View Discount Codes", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("view discount codes", ViewDiscountCodes);
        menuForShow.add("View Discount Codes");
    }
}
