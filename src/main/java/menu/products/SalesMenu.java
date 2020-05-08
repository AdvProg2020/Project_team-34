package menu.products;

import discount.Sale;
import menu.menuAbstract.Menu;
import product.Product;

public class SalesMenu extends Menu {
    public SalesMenu(Menu parentMenu) {
        super("Sales Menu", parentMenu);

        menusIn.put("^filtering$", new FilteringMenu(this));
        menuForShow.add("Filtering");

        menusIn.put("^sorting$", new SortingMenu(this));
        menuForShow.add("Sort");

        Menu showProducts = new Menu("Show Products", this) {
            @Override
            public void show() {

            }

            @Override
            public void execute() {
                //Need Modification!
                for (String product : controller.controlGetAllProducts()) {
                    //Need a quick introduction!
                    System.out.println(product);
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^show products$", showProducts);
        menuForShow.add("Show Product");


        menusIn.put("^show product ([^\\s]+)$", new ProductMenu(this));
        menuForShow.add("Show Product By Id");
    }

    @Override
    public void show() {
        for (Sale sale : controller.controlGetAllSales()) {
            System.out.println("Sale id : " + sale.getOffId());
            for (Product product : sale.getProducts()) {
                System.out.println(product);
                //Need modification!
            }
        }
        super.show();
    }
}
