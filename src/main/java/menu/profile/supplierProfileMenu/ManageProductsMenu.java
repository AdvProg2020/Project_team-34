package menu.profile.supplierProfileMenu;

import menu.menuAbstract.Menu;

public class ManageProductsMenu extends Menu {
    public ManageProductsMenu(Menu parentMenu) {
        super("Manage Products Menu", parentMenu);

        Menu View = new Menu("View", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^view (\\w+)$", View);
        menuForShow.add("View");

        Menu ViewBuyers = new Menu("View Buyers", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^view buyers (\\w+)$", ViewBuyers);
        menuForShow.add("View Buyers");

        Menu Edit = new Menu("Edit", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^edit (\\w+)$", Edit);
        menuForShow.add("Edit");
    }
}
