package menu.profile.customerProfileMenu;

import account.Supplier;
import cart.Cart;
//import com.sun.tools.javac.tree.JCTree;
import discount.Sale;
import exceptionalMassage.ExceptionalMassage;
import menu.menuAbstract.Menu;
import product.Product;

import java.util.ArrayList;
import java.util.HashMap;

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
                Cart cart = null;
                try {
                    cart = controller.controlViewCart();
                    if (cart.isEmpty()) {
                        System.out.println("Cart is Empty!");
                    } else {
                        ArrayList<Product> productsIn = cart.getProductsIn();
                        HashMap<Product, Integer> productsQuantity = cart.getProductsQuantity();
                        HashMap<Product, Supplier> productsSupplier = cart.getProductsSupplier();
                        HashMap<Product, Sale> productsSales = cart.getProductsSales();
                        for (Product product : productsIn) {
                            StringBuilder productLog = new StringBuilder();
                            productLog.append(product.getName()).append(" ");
                            productLog.append("Supplied by: ").append(productsSupplier.get(product).getName()).append(" ");
                            productLog.append("Count: ").append(productsQuantity.get(product)).append(" ");
                            productLog.append("Price: ").append(product.getPrice(productsSupplier.get(product)));
                            if (productsSales.get(product) != null) {
                                productLog.append(">>").append(product.getPrice(productsSupplier.get(product)) - productsSales.get(product).discountAmountFor(product.getPrice(productsSupplier.get(product))));
                            }
                            productLog.append(" ");
                            System.out.println(productLog);
                            int totalValueOfDiscount = cart.getAmountOfSale() + cart.getAmountOfCodedDiscount();
                            System.out.println("Total Discount = " + totalValueOfDiscount + " = " + cart.getAmountOfSale() + " + " + cart.getAmountOfCodedDiscount());
                            System.out.println("Total Bill = " + cart.getBill() + " = " + cart.getValueOfCartWithoutDiscounts() + " - " + totalValueOfDiscount);
                        }
                    }
                } catch (ExceptionalMassage e) {
                    System.out.println(e.getMessage());
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^show products$", ShowProduct);
        menuForShow.add("Show Product");

        Menu View = new Menu("View Product", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
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
            }
        };
        menusIn.put("^increase \\w+$", Increase);
        menuForShow.add("Increase");

        Menu Decrease = new Menu("Decrease", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^decrease \\w+$", Decrease);
        menuForShow.add("Decrease");

        Menu ShowTotalPrice = new Menu("Show Total Price", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^show total price$", ShowTotalPrice);
        menuForShow.add("Show Total Price");

        menusIn.put("^purchase$", new PurchaseMenu(this));
        menuForShow.add("Purchase");
    }
}
