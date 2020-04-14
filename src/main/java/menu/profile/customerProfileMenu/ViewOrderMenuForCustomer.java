package menu.profile.customerProfileMenu;

import menu.menuAbstract.Menu;

public class ViewOrderMenuForCustomer extends Menu {
    public ViewOrderMenuForCustomer(Menu parentMenu) {
        super("View Order Menu", parentMenu);

        Menu ShowOrderForCustomer = new Menu("Show Order", this) {
            @Override
            public void show() {

            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("show order (\\w+)", ShowOrderForCustomer);
        menuForShow.add("Show Order");

        Menu Rate = new Menu("Rate", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("show order (\\w+)", Rate);
        menuForShow.add("Rate");
    }
}
