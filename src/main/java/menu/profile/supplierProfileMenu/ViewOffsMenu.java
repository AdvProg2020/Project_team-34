package menu.profile.supplierProfileMenu;

import exceptionalMassage.ExceptionalMassage;
import menu.menuAbstract.Menu;
import menu.profile.ProfileMenu;
import product.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class ViewOffsMenu extends Menu {
    public ViewOffsMenu(Menu parentMenu) {
        super("View Offs Menu", parentMenu);

        Menu View = new Menu("View", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                String regex = "^view (\\w+)$";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()){
                    if(controller.getOffController().controlGetSaleById(matcher.group(1)) == null){
                        System.out.println("no such id!");
                    } else {
                        System.out.println(controller.getOffController().controlGetSaleById(matcher.group(1)));
                    }
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^view (\\w+)$", View);
        menuForShow.add("View");

        Menu Edit = new Menu("Edit", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                ArrayList<Product> addingProduct = new ArrayList<>();
                ArrayList<Product> removingProduct = new ArrayList<>();
                System.out.println("Enter the starting date: (dd/MM/yyyy HH:mm:ss) ");
                String startingDate = scanner.nextLine();
                Date startDate = null;
                try {
                    startDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(startingDate);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    parentMenu.show();
                    parentMenu.execute();
                }
                System.out.println("Enter the ending date: (dd/MM/yyyy HH:mm:ss) ");
                String endingDate = scanner.nextLine();
                Date endDate = null;
                try {
                    endDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(endingDate);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    parentMenu.show();
                    parentMenu.execute();
                }
                System.out.println("Enter the new Percent :");
                int newPercent = scanner.nextInt();
                String productId = null;
                System.out.println("Enter the adding products IDs :");
                while(!(productId = scanner.nextLine()).equalsIgnoreCase("end")){
                    if(Product.getProductById(productId) == null){
                        System.out.println("no such id!");
                    } else {
                        addingProduct.add(Product.getProductById(productId));
                    }
                }
                productId = null;
                System.out.println("Enter the removing products IDs :");
                while(!(productId = scanner.nextLine()).equalsIgnoreCase("end")){
                    if(Product.getProductById(productId) == null){
                        System.out.println("no such id!");
                    } else {
                        removingProduct.add(Product.getProductById(productId));
                    }
                }
                String regex = "^edit (\\w+)$";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()){
                    try{
                        controller.getOffController().controlEditSaleById(productId,endDate,startDate,newPercent,addingProduct,removingProduct);
                    } catch (ExceptionalMassage ex){
                        System.out.println(ex.getMessage());
                    }
                }
                parentMenu.show();
                parentMenu.execute();

            }
        };
        menusIn.put("^edit (\\w+)$", Edit);
        menuForShow.add("Edit");

        Menu AddOff = new Menu("Add Menu", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                ArrayList<Product> addingProduct = new ArrayList<>();
                System.out.println("Enter the starting date: (dd/MM/yyyy HH:mm:ss) ");
                String startingDate = scanner.nextLine();
                Date startDate = null;
                try {
                    startDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(startingDate);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    parentMenu.show();
                    parentMenu.execute();
                }
                System.out.println("Enter the ending date: (dd/MM/yyyy HH:mm:ss) ");
                String endingDate = scanner.nextLine();
                Date endDate = null;
                try {
                    endDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(endingDate);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    parentMenu.show();
                    parentMenu.execute();
                }
                System.out.println("enter the percent :");
                int percent = 0;
                percent = scanner.nextInt();
                String productId = null;
                System.out.println("Enter the adding products IDs :");
                while(!(productId = scanner.nextLine()).equalsIgnoreCase("end")){
                    if(Product.getProductById(productId) == null){
                        System.out.println("no such id!");
                    } else {
                        addingProduct.add(Product.getProductById(productId));
                    }
                }
                controller.getOffController().controlCreateSale(startDate, endDate, percent, addingProduct);
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^add off$", AddOff);
        menuForShow.add("Add off");
    }
}
