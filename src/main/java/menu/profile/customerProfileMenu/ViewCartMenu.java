package menu.profile.customerProfileMenu;

import menu.menuAbstract.Menu;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class ViewCartMenu extends Menu {
    public ViewCartMenu(Menu parentMenu) {
        super("View Cart Menu", parentMenu);

        Menu ShowProduct = new Menu("Show Product", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^show products$", ShowProduct);
        menuForShow.add("Show Product");

        Menu View = new Menu("View Product", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^view (\\w+)$", View);
        menuForShow.add("View Product");

        Menu Increase = new Menu("Increase", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^increase \\w+$", Increase);
        menuForShow.add("Increase");

        Menu Decrease = new Menu("Decrease", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^decrease \\w+$", Decrease);
        menuForShow.add("Decrease");

        Menu ShowTotalPrice = new Menu("Show Total Price", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^show total price$", ShowTotalPrice);
        menuForShow.add("Show Total Price");

        menusIn.put("^purchase$", new PurchaseMenu(this));
        menuForShow.add("Purchase");
    }
}
