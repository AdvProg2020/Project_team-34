package menu.products;

import exceptionalMassage.ExceptionalMassage;
import menu.menuAbstract.Menu;

import java.util.regex.Matcher;

public class FilteringMenu extends Menu {
    public FilteringMenu(Menu parentMenu) {
        super("Filtering Menu", parentMenu);
        Menu showAvailableFilters = new Menu("Show Available Filters", this) {
            @Override
            public void show() {
                System.out.println("Filters :");
                for (String filter : controller.getProductController().controlGetAllAvailableFilters()) {
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
                System.out.println("Please select the filter type :");
                String type = scanner.nextLine();
                String regex = "^filter (.+)$";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()){
                    try {
                        controller.getProductController().controlFilterAddSpecialFilter(matcher.group(1), type);
                    }
                    catch (ExceptionalMassage ex){
                        System.out.println(ex.getMessage());
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
                System.out.println("Select the filter type:");
                String type = scanner.nextLine();
                String regex = "^disable filter (.+)$";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()){
                    try{
                        controller.getProductController().controlFilterRemoveSpecialFilter(matcher.group(1), type);
                    } catch (ExceptionalMassage ex){
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
