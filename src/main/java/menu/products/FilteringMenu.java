package menu.products;

import menu.menuAbstract.Menu;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class FilteringMenu extends Menu {
    public static ArrayList<String> currentFiltersList;
    public FilteringMenu(Menu parentMenu) {
        super("Filtering Menu", parentMenu);
        currentFiltersList = new ArrayList<>();
        Menu showAvailableFilters = new Menu("Show Available Filters", this) {
            @Override
            public void show() {
                System.out.println("Filters :");
                for (String filter : controller.controlGetAllAvailableFilters()) {
                    System.out.println(filter);
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
                    if(controller.isThisFilterAvailable(matcher.group(1))){
                        System.out.println("Filter added!");
                        currentFiltersList.add(matcher.group(1));
                    } else {
                        System.out.println("Filter not available!");
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
                for (String filter : currentFiltersList) {
                    System.out.println(filter);
                }
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
                String regex = "^disable filter (.+)$";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()){
                    try{
                        currentFiltersList.remove(matcher.group(1));
                    } catch (Exception ex){
                        System.out.println(ex.getMessage());
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
