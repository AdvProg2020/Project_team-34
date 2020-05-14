package menu.profile.supplierProfileMenu;

import exceptionalMassage.ExceptionalMassage;
import menu.menuAbstract.Menu;
import product.Product;

import java.util.HashMap;
import java.util.regex.Matcher;

public class ManageProductsMenu extends Menu {
    public ManageProductsMenu(Menu parentMenu) {
        super("Manage Products Menu", parentMenu);

        Menu View = new Menu("View", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                String regex = "^view (\\w+)$";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()) {
                    try {
                        System.out.println(controller.getProductController().controlGetDigestInfosOfProduct(Product.getProductById(matcher.group(1))));
                    }
                    catch (ExceptionalMassage ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^view (\\w+)$", View);
        menuForShow.add("View");

        Menu ViewBuyers = new Menu("View Buyers", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                String regex = "^view buyers (\\w+)$";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()){
                   try {
                       controller.getProductController().controlViewBuyersOfProduct(matcher.group(1));
                   } catch (ExceptionalMassage ex){
                       System.out.println(ex.getMessage());
                   }
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^view buyers (\\w+)$", ViewBuyers);
        menuForShow.add("View Buyers");

        Menu Edit = new Menu("Edit", this) {
            @Override
            public void show() {
                System.out.println("For editing this product select a field and write the new one:");
            }

            @Override
            public void execute() {
                String regex = "^edit (\\w+)$";
                Matcher matcher = getMatcher(command, regex);
                HashMap<String, String> fieldToChange = new HashMap<>();
                String field = null;
                String newAttribute = null;
                while(!(field = scanner.nextLine()).equalsIgnoreCase("end")){
                    newAttribute =scanner.nextLine();
                    fieldToChange.put(field, newAttribute);
                }
                if(matcher.find()){
                    try {
                        controller.getProductController().controlEditProductById(matcher.group(1), fieldToChange);
                    } catch (ExceptionalMassage ex){
                        System.out.println(ex.getMessage());
                    }
                }

            }
        };
        menusIn.put("^edit (\\w+)$", Edit);
        menuForShow.add("Edit");
    }

    @Override
    public void show() {
        try {
            System.out.println(controller.getAccountController().controlGetListOfProductsForThisSupplier());
        } catch (ExceptionalMassage ex){
            System.out.println(ex.getMessage());
        }
        super.show();
    }
}
