package menu.profile.supplierProfileMenu;

import exceptionalMassage.ExceptionalMassage;
import menu.menuAbstract.Menu;
import menu.profile.ProfileMenu;

import java.util.regex.Matcher;

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
                System.out.println(controller.controlViewCompanyInfo());
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^view company information$", ViewCompanyInformation);
        menuForShow.add("View Company Information");

        Menu ViewSalesHistory = new Menu("View Sales History", this) {
            @Override
            public void show() {
                //System.out.println(controller.showSalesHistory());
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^view sales history", ViewSalesHistory);
        menuForShow.add("View Sales History");

        menusIn.put("^manage products$", new ManageProductsMenu(this));
        menuForShow.add("Manage Products");

        Menu AddProduct = new Menu("Add Product", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                //need modification!
                System.out.println();
                //controller.controlAddProduct();
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
                String regex = "^remove product (\\w+)$";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()){
                    try{
                        controller.controlRemoveProductById(matcher.group(1));
                    } catch (ExceptionalMassage ex){
                        System.out.println(ex.getMessage());
                    }
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^remove product (\\w+)$", RemoveProduct);
        menuForShow.add("Remove Product");

        Menu ShowCategories = new Menu("ShowCategories", this) {
            @Override
            public void show() {
                System.out.println(controller.controlGetAllCategories());
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^show categories$", ShowCategories);
        menuForShow.add("Show Categories");

        menusIn.put("^view off menu$", new ViewOffsMenu(this));
        menuForShow.add("View Offs");

        Menu ViewBalance = new Menu("View Balance", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                System.out.println(controller.controlViewBalance());
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^view balance$", ViewBalance);
        menuForShow.add("View Balance");
    }
}
