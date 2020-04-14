package menu;

public class CustomerProfileMenu extends ProfileMenu {
    public CustomerProfileMenu(Menu parentMenu) {
        super("Customer Profile Menu", parentMenu);

        menusIn.put("^view cart$", new ViewCartMenu(this));
        menuForShow.add("View Cart");


    }
}
