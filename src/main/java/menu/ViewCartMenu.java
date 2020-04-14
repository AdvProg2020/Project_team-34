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
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
        menusIn.put("^view (\\w+)$", this);
        menuForShow.add("View Product");
    }
}
