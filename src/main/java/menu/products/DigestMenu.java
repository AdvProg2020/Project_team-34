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
    public DigestMenu(Menu parentMenu) {
        super("Digest Menu", parentMenu);


        Menu addToCart = new Menu("Add To Cart", this) {
            @Override
            public void show() {

            }

            @Override
            public void execute() {

                    try {
                        controller.getAccountController().controlAddToCart(product.getProductId(), seller.getNameOfCompany());
                    } catch (ExceptionalMassage ex){
                        System.out.println(ex.getMessage());
                    }
                    parentMenu.show();
                    parentMenu.execute();
                }

        };
        menusIn.put("^add to cart$", addToCart);
        menuForShow.add("add to cart");

        Menu selectSeller = new Menu("Select Seller", this) {
            @Override
            public void show() {

            }

            @Override
            public void execute() {
                String regex = "^select seller ([^\\s]+)$";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()){
                    try {
                        seller = (Supplier) (Account.getAccountByUsernameWithinAvailable(matcher.group(1)));
                        if(seller == null){
                            System.out.println("No such supplier!");
                        }
                        if (controller.getProductController().doesThisSupplierSellThisProduct(seller, product)) {
                            System.out.println("Seller selected!");
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

    public void setProduct(Product product) {
        this.product = product;
    }


    @Override
    public void show() {
        System.out.println("Product info : ");
        try {
            System.out.println(controller.getProductController().controlGetDigestInfosOfProduct(product));
        } catch (ExceptionalMassage ex){
            System.out.println(ex.getMessage());
        }
        seller = product.getListOfSuppliers().get(0);
        super.show();
    }
}
