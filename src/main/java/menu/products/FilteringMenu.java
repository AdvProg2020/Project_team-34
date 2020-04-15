package menu.products;

import menu.menuAbstract.Menu;

public class FilteringMenu extends Menu {
    public FilteringMenu(Menu parentMenu) {
        super("Filtering Menu", parentMenu);
        Menu showAvailableFilters = new Menu("Show Available Filters", this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
        menusIn.put("^show available filters$", showAvailableFilters);
        menuForShow.add("Show Available Filters");

        Menu filter = new Menu("Filter", this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
        menusIn.put("^filter (.+)$", filter);
        menuForShow.add("Filter with available filters");

        Menu currentFilters = new Menu("Current Filters", this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
        menusIn.put("^current filters$", currentFilters);
        menuForShow.add("Current Filters");

        Menu disableFilter = new Menu("Disable Filter", this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
        menusIn.put("^disable filter (.+)$", disableFilter);
        menuForShow.add("Disable Filter [a selected filter]");
    }
}
