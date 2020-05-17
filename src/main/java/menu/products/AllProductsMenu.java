package menu.products;

import menu.menuAbstract.Menu;

public class AllProductsMenu extends Menu {
    public AllProductsMenu(Menu parentMenu) {
        super("All Products Menu", parentMenu);
        Menu viewCategories = new Menu("View Categories", this) {
            @Override
            public void show() {
                System.out.println("Categories: ");
            }

            @Override
            public void execute() {
                System.out.println(controller.getProductController().controlGetAllCategories());
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^view categories$", viewCategories);
        menuForShow.add("View Categories");

        menusIn.put("^filtering$", new FilteringMenu(this));
        menuForShow.add("Filtering");

        menusIn.put("^sorting$", new SortingMenu(this));
        menuForShow.add("Sort");

        Menu showProducts = new Menu("Show Products", this) {
            @Override
            public void show() {
                System.out.println("All products : ");
            }

            @Override
            public void execute() {
                for (String product : controller.getProductController().controlGetAllProducts()) {
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
        for (String product : controller.getProductController().controlGetAllProducts()) {
            System.out.println(product);
        }
        super.show();
    }
}
