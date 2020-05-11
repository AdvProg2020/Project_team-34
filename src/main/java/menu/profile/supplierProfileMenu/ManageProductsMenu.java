package menu.profile.supplierProfileMenu;

import exceptionalMassage.ExceptionalMassage;
import menu.menuAbstract.Menu;
import product.Product;

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
                        System.out.println(controller.controlGetDigestInfosOfProduct(Product.getProductById(matcher.group(1))));
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
                       controller.controlViewBuyersOfProduct(matcher.group(1));
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
            }

            @Override
            public void execute() {
                //need modification!
                //controller.controlEditProductById();
            }
        };
        menusIn.put("^edit (\\w+)$", Edit);
        menuForShow.add("Edit");
    }

    @Override
    public void show() {
        System.out.println(controller.controlGetListOfProductsForThisSupplier());
        super.show();
    }
}
