package menu.profile.supplierProfileMenu;

import account.Supplier;
import exceptionalMassage.ExceptionalMassage;
import log.SupplierLog;
import menu.menuAbstract.Menu;
import menu.profile.ProfileMenu;
import product.Category;

import java.util.HashMap;
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
                System.out.println(controller.getAccountController().controlViewCompanyInfo());
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
                for (SupplierLog log : SupplierLog.getSupplierSupplierLog((Supplier) controller.getAccount())) {
                    System.out.println(log);
                }
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
                System.out.print("Enter product name: ");
                String productName = scanner.nextLine();
                System.out.print("Enter name of company: ");
                String nameOfCompany = scanner.nextLine();
                System.out.print("Enter the price: ");
                int price;
                String priceIO = scanner.nextLine();
                System.out.print("Enter remained numbers: ");
                int numbers;
                String numberIO = scanner.nextLine();
                System.out.print("Enter the category name: ");
                String categoryName = scanner.nextLine();
                System.out.print("Enter description: ");
                String description = scanner.nextLine();
                System.out.println("Enter the field of specification:(enter \"end\" when you are finished)");
                String field = null;
                HashMap<String, String> specifications = new HashMap<>();
                while(!(field = scanner.nextLine()).equalsIgnoreCase("end")){
                    System.out.println("Enter the specification:");
                    String value = scanner.nextLine();
                    specifications.put(field, value);
                    System.out.println("Enter the field of specification:(enter end when u are finished)");
                }
                try {
                    price = Integer.parseInt(priceIO);
                    numbers = Integer.parseInt(numberIO);
                    try {
                        controller.getProductController().controlAddProduct(productName, nameOfCompany, price, numbers, categoryName, description, specifications);
                    } catch (ExceptionalMassage ex){
                        System.out.println(ex.getMessage());
                    }
                } catch (NumberFormatException e) {
                    System.out.println("you must enter integer for values of price and remained number.");
                }
                parentMenu.show();
                parentMenu.execute();

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
                        controller.getProductController().controlRemoveProductById(matcher.group(1));
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
                System.out.println(controller.getProductController().controlGetAllCategories());
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
                System.out.println(controller.getAccountController().controlViewBalance());
            }

            @Override
            public void execute() {

                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^view balance$", ViewBalance);
        menuForShow.add("View Balance");
    }
}
