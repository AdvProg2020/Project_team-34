package menu.profile.customerProfileMenu;

import exceptionalMassage.ExceptionalMassage;
import menu.menuAbstract.Menu;
import menu.products.ProductMenu;
import product.Product;

import java.util.regex.Matcher;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class ViewCartMenu extends Menu {
    public ViewCartMenu(Menu parentMenu) {
        super("View Cart Menu", parentMenu);

        Menu ShowProduct = new Menu("Show Products In", this) {
            @Override
            public void show() {
                System.out.println("Products In Cart");
            }

            @Override
            public void execute() {
                System.out.println(controller.getCart().getProductsIn());
            }
        };
        menusIn.put("^show products$", ShowProduct);
        menuForShow.add("Show Products");

        Menu View = new Menu("View Product", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                String regex = "^view (\\w+)$";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()){
                    Menu nextMenu = new ProductMenu(this);
                    nextMenu.show();
                    nextMenu.execute();
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^view (\\w+)$", View);
        menuForShow.add("View Product");

        Menu Increase = new Menu("Increase", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                String regex = "^increase (\\w+)$";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()){
                    Product product = Product.getProductById(matcher.group(1));
                    try {
                        controller.getAccountController().increaseProductQuantity(matcher.group(1),product.getNameOfCompany());
                    } catch (ExceptionalMassage ex){
                        System.out.println(ex.getMessage());
                    }
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^increase (\\w+)$", Increase);
        menuForShow.add("Increase");

        Menu Decrease = new Menu("Decrease", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                String regex = "^decrease (\\w+)$";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()){
                    Product product = Product.getProductById(matcher.group(1));
                    try {
                        controller.getAccountController().decreaseProductQuantity(matcher.group(1),product.getNameOfCompany());
                    } catch (ExceptionalMassage ex){
                        System.out.println(ex.getMessage());
                    }
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^decrease (\\w+)$", Decrease);
        menuForShow.add("Decrease");

        Menu ShowTotalPrice = new Menu("Show Total Price", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                System.out.println(controller.getCart().getBill());
            }
        };
        menusIn.put("^show total price$", ShowTotalPrice);
        menuForShow.add("Show Total Price");

        menusIn.put("^purchase$", new PurchaseMenu(this));
        menuForShow.add("Purchase");
    }

    @Override
    public void show() {
        System.out.println(controller.getAccountController().controlViewCart());
        super.show();
    }
}
