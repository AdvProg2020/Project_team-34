package menu.products;

import menu.menuAbstract.Menu;

public class SortingMenu extends Menu {
    public SortingMenu(Menu parentMenu) {
        super("Sorting Menu", parentMenu);
        Menu showAvailableSorts = new Menu("Show All Sorts", this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
        menusIn.put("^show available sorts$", showAvailableSorts);
        menuForShow.add("Show Available Sorts");

        Menu sort = new Menu("Sort", this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
        menusIn.put("^sort (.+)$", sort);
        menuForShow.add("Sort [an available sort]");

        Menu currentSort = new Menu("Current Sort", this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
        menusIn.put("^current sort$", currentSort);
        menuForShow.add("Current Sort");

        Menu disableSort = new Menu("Disable Sort", this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
        menusIn.put("^disable sort$" , disableSort);
        menuForShow.add("Disable Sort");



    }
}
