package menu.profile.supplierProfileMenu;

import menu.menuAbstract.Menu;
import menu.profile.ProfileMenu;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class SupplierProfileMenu extends ProfileMenu {
    public SupplierProfileMenu(Menu parentMenu) {
        super("Supplier Profile Menu", parentMenu);

        Menu ViewCompanyInformation = new Menu("View Company Information", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^view company information$", ViewCompanyInformation);
        menuForShow.add("View Company Information");

        Menu ViewSalesHistory = new Menu("View Sales History", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^view company information$", ViewSalesHistory);
        menuForShow.add("View Sales History");

        //manage product

        Menu AddProduct = new Menu("Add Product", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^add product$", AddProduct);
        menuForShow.add("Add Product");

        Menu RemoveProduct = new Menu("Remove Product", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^remove product (\\w+)$", RemoveProduct);
        menuForShow.add("Remove Product");

        Menu ShowCategories = new Menu("ShowCategories", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^show categories$", ShowCategories);
        menuForShow.add("Show Categories");

        //menu view offs

        Menu ViewBalance = new Menu("View Balance", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^view balance$", ViewBalance);
        menuForShow.add("View Balance");
    }
}
