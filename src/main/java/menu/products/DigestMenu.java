package menu.products;

import menu.menuAbstract.Menu;

public class DigestMenu extends Menu {
    public DigestMenu(Menu parentMenu) {
        super("Digest Menu", parentMenu);

        Menu addToCart = new Menu("Add To Cart", this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
        menusIn.put("^add to cart$", addToCart);
        menuForShow.add("add to cart");

        Menu selectSeller = new Menu("Select Seller", this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
        menusIn.put("^select seller ([^\\s]+)$", selectSeller);
        menuForShow.add("select seller [seller_username]");
    }
}
