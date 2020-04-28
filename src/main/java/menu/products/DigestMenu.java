package menu.products;

import account.Supplier;
import menu.menuAbstract.Menu;
import product.Product;

import java.util.regex.Matcher;

public class DigestMenu extends Menu {
    private Product product;
    private Supplier seller;
    public DigestMenu(Menu parentMenu) {
        super("Digest Menu", parentMenu);

        Menu addToCart = new Menu("Add To Cart", this) {
            @Override
            public void show() {

            }

            @Override
            public void execute() {
                if(!controller.hasSomeOneLoggedIn()){
                    menusIn.get("Login/Register").show();
                    menusIn.get("Login/Register").execute();
                } else {
                    //controller.controlAddToCart();
                }
            }
        };
        menusIn.put("^add to cart$", addToCart);
        menuForShow.add("add to cart");

        Menu selectSeller = new Menu("Select Seller", this) {
            @Override
            public void show() {
                //get all sellers
            }

            @Override
            public void execute() {
                String regex = "^select seller ([^\\s]+)$";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()){
                    //check if this seller sells the product and change the seller of the product!
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^select seller ([^\\s]+)$", selectSeller);
        menuForShow.add("select seller [seller_username]");
    }

    @Override
    public void show() {
        System.out.println("Product info : ");
        //print infos!
    }
}
