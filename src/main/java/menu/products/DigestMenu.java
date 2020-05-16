package menu.products;

import account.Account;
import account.Supplier;
import exceptionalMassage.ExceptionalMassage;
import menu.menuAbstract.Menu;
import product.Product;

import java.util.regex.Matcher;

public class DigestMenu extends Menu {
    private Product product;
    private Supplier seller;
    public DigestMenu(Menu parentMenu,Product product) {
        super("Digest Menu", parentMenu);
        this.product = product;

        Menu addToCart = new Menu("Add To Cart", this) {
            @Override
            public void show() {

            }

            @Override
            public void execute() {
                if(!controller.getAccountController().hasSomeOneLoggedIn()){
                    menusIn.get("Login/Register").show();
                    menusIn.get("Login/Register").execute();
                } else {
                    //need modification
                    try {
                        controller.getAccountController().controlAddToCart(product.getProductId(), seller.getName());
                    } catch (ExceptionalMassage ex){
                        System.out.println(ex.getMessage());
                    }
                }
            }
        };
        menusIn.put("^add to cart$", addToCart);
        menuForShow.add("add to cart");

        Menu selectSeller = new Menu("Select Seller", this) {
            @Override
            public void show() {
                System.out.println("Sellers :");
                for (Supplier supplier : controller.getProductController().controlGetAllSuppliersForAProduct(product)) {
                    System.out.println(supplier.getUserName());
                }
            }

            @Override
            public void execute() {
                String regex = "^select seller ([^\\s]+)$";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()){
                    try {
                        if (controller.getProductController().doesThisSupplierSellThisProduct(product)) {
                            seller = (Supplier) (Account.getAccountByUsername(matcher.group(1)));
                            System.out.println("Seller selected!");
                        } else {
                            System.out.println("This seller is not available for this product!");
                        }
                    }
                    catch (ExceptionalMassage ex){
                        System.out.println(ex.getMessage());
                    }
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
        try {
            System.out.println(controller.getProductController().controlGetDigestInfosOfProduct(product));
        } catch (ExceptionalMassage ex){
            System.out.println(ex.getMessage());
        }
        super.show();
    }
}
