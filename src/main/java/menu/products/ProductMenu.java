package menu.products;

import menu.menuAbstract.Menu;
import product.Product;

public class ProductMenu extends Menu {
    private Product currentProduct;
    public ProductMenu(Menu parentMenu) {
        super("Product Menu", parentMenu);
        menusIn.put("^digest$", new DigestMenu(this));
        menuForShow.add("Digest Menu");

        Menu attributes = new Menu("Attributes", this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
        menusIn.put("^attributes$", attributes);
        menuForShow.add("Attributes");

        Menu compare = new Menu("Compare" , this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
        menusIn.put("^compare ([^\\s]+)", compare);
        menuForShow.add("Compare [productID]");

        menusIn.put("^Comments$", new CommentMenu(this));
        menuForShow.add("Comments");



    }
}
