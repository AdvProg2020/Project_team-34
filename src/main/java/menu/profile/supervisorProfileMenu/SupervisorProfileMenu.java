package menu.profile.supervisorProfileMenu;

import menu.menuAbstract.Menu;
import menu.profile.ProfileMenu;

public class SupervisorProfileMenu extends ProfileMenu {
    public SupervisorProfileMenu( Menu parentMenu) {
        super("Supervisor Profile Menu", parentMenu);

        menusIn.put("^manage users$", new ManageUsersMenu(this));
        menuForShow.add("Manage Users");

        menusIn.put("^manage all products$", new ManageProductsMenu(this));
        menuForShow.add("Manage Products");

        Menu CreateDiscountCode = new Menu("Create Discount Code", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^create discount code$", CreateDiscountCode);
        menuForShow.add("Create Discount Code");

        menusIn.put("^view discount codes$", new ViewDiscountCodesMenu(this));
        menuForShow.add("View Discount Codes");


    }
}
