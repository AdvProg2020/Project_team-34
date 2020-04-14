package menu;

public class ViewCartMenu extends Menu{
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
        menusIn.put("^show products$", this);
        menuForShow.add("Show Product");
        Menu View = new Menu("View Product", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^view (\\w+)$", this);
        menuForShow.add("View Product");
        Menu Increase = new Menu("Increase", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("increase \\w+", this);
        menuForShow.add("Increase");
        Menu Decrease = new Menu("Decrease", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("decrease \\w+", this);
        menuForShow.add("Decrease");
        Menu showTotalPrice = new Menu("Show Total Price", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("show total price", this);
        menuForShow.add("Show Total Price");
    }
}
