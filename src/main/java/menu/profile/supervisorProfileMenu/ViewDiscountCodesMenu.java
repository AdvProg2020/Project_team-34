package menu.profile.supervisorProfileMenu;

import menu.menuAbstract.Menu;

public class ViewDiscountCodesMenu extends Menu {
    public ViewDiscountCodesMenu(Menu parentMenu) {
        super("View Discount Codes Menu", parentMenu);

        Menu ViewDiscountCode = new Menu("View Discount Code", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^view discount code \\w+$", ViewDiscountCode);
        menuForShow.add("View Discount Code");
    }
}
