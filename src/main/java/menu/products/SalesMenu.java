package menu.products;

import menu.menuAbstract.Menu;

public class SalesMenu extends Menu {
    public SalesMenu(Menu parentMenu) {
        super("Sales Menu", parentMenu);

        menusIn.put("^filtering$", new FilteringMenu(this));
        menuForShow.add("Filtering");

        menusIn.put("^sorting$", new SortingMenu(this));
        menuForShow.add("Sort");

        Menu showProducts = new Menu("Show Products", this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
        menusIn.put("^show products$", showProducts);
        menuForShow.add("Show Product");


        menusIn.put("^show product ([^\\s]+)$", new ProductMenu(this));
        menuForShow.add("Show Product By Id");
    }
}
