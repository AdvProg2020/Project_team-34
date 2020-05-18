package menu.products;

import exceptionalMassage.ExceptionalMassage;
import menu.menuAbstract.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class FilteringMenu extends Menu {
    public FilteringMenu(Menu parentMenu) {
        super("Filtering Menu", parentMenu);
        Menu showAvailableFilters = new Menu("Show Available Filters", this) {
            @Override
            public void show() {
                System.out.println("Filters :");
                System.out.println("price\n"+
                                    "name\n"+
                                    "category\n"+
                                    "availability\n"+
                                    "brand");
                HashMap<String , ArrayList<String>> filters = controller.getProductController().controlGetAllAvailableFilters();
                for (String filter : filters.keySet()) {
                    System.out.println(filter + " : ");
                    for (String available : filters.get(filter)) {
                        System.out.println(available);
                    }
                }
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^show available filters$", showAvailableFilters);
        menuForShow.add("Show Available Filters");

        Menu filter = new Menu("Filter", this) {
            @Override
            public void show() {

            }

            @Override
            public void execute() {
                String regex = "^filter (.+)$";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()){
                    if(matcher.group(1).equals("price")){
                        System.out.println("Enter upper price bound:  (Enter 0 for no filter)");
                        int upperBound=0;
                        try{
                            upperBound = Integer.parseInt(scanner.nextLine());
                        }catch (Exception ex){
                            System.out.println(ex.getMessage());
                        }
                        System.out.println("Enter lower price bound:  (Enter 0 for no filter)");
                        int lowerBound=0;
                        try{
                            lowerBound = Integer.parseInt(scanner.nextLine());
                        } catch (Exception ex){
                            System.out.println(ex.getMessage());
                        }try {
                            controller.getProductController().controlFilterSetPriceLowerBound(lowerBound);
                            controller.getProductController().controlFilterSetPriceUpperBound(upperBound);
                        }catch (Exception ex){
                            System.out.println(ex.getMessage());
                        }

                    }
                    else if(matcher.group(1).equals("name")){
                        System.out.println("Enter the name:");
                        String name = scanner.nextLine();
                        try {
                            controller.getProductController().controlFilterAddNameFilter(name);
                        }catch (Exception ex){
                            System.out.println(ex.getMessage());
                        }
                    }
                    else if(matcher.group(1).equals("category")){
                        System.out.println("Enter the category");
                        String name = scanner.nextLine();
                        try{
                            controller.getProductController().controlFilterSetCategoryFilter(name);
                        } catch (Exception ex){
                            System.out.println(ex.getMessage());
                        }
                    }
                    else if(matcher.group(1).equals("availability")){
                        try{
                            controller.getProductController().controlFilterSetAvailabilityFilter(true);
                        } catch (Exception ex){
                            System.out.println(ex.getMessage());
                        }
                    }
                    else if(matcher.group(1).equals("brand")){
                        System.out.println("Enter brand name: ");
                        String brandName = scanner.nextLine();
                        try{
                            controller.getProductController().controlFilterAddBrandFilter(brandName);
                        } catch (Exception ex){
                            System.out.println(ex.getMessage());
                        }
                    }
                    else {
                        System.out.println("enter the type:");
                        String type = scanner.nextLine();
                        try {
                            controller.getProductController().controlFilterAddSpecialFilter(matcher.group(1), type);
                        } catch (ExceptionalMassage ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^filter (.+)$", filter);
        menuForShow.add("Filter with available filters");

        Menu currentFilters = new Menu("Current Filters", this) {
            @Override
            public void show() {
                System.out.println(controller.getProductController().controlCurrentFilters());
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^current filters$", currentFilters);
        menuForShow.add("Current Filters");

        Menu disableFilter = new Menu("Disable Filter", this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                String regex = "^filter (.+)$";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()){
                    if(matcher.group(1).equals("price")){
                        try {
                            controller.getProductController().controlFilterSetPriceLowerBound(0);
                            controller.getProductController().controlFilterSetPriceUpperBound(0);
                        }catch (Exception ex){
                            System.out.println(ex.getMessage());
                        }

                    }
                    else if(matcher.group(1).equals("name")){
                        System.out.println("Enter the name:");
                        String name = scanner.nextLine();
                        try {
                            controller.getProductController().controlFilterRemoveNameFilter(name);
                        }catch (Exception ex){
                            System.out.println(ex.getMessage());
                        }
                    }
                    else if(matcher.group(1).equals("category")){
                        try{
                            controller.getProductController().controlFilterSetCategoryFilter(null);
                        } catch (Exception ex){
                            System.out.println(ex.getMessage());
                        }
                    }
                    else if(matcher.group(1).equals("availability")){
                        try{
                            controller.getProductController().controlFilterSetAvailabilityFilter(false);
                        } catch (Exception ex){
                            System.out.println(ex.getMessage());
                        }
                    }
                    else if(matcher.group(1).equals("brand")){
                        System.out.println("Enter brand name: ");
                        String brandName = scanner.nextLine();
                        try{
                            controller.getProductController().controlFilterRemoveBrandFilter(brandName);
                        } catch (Exception ex){
                            System.out.println(ex.getMessage());
                        }
                    }
                    else {
                        System.out.println("enter the type:");
                        String type = scanner.nextLine();
                        try {
                            controller.getProductController().controlFilterRemoveSpecialFilter(matcher.group(1), type);
                        } catch (ExceptionalMassage ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^disable filter (.+)$", disableFilter);
        menuForShow.add("Disable Filter [a selected filter]");
    }
}
