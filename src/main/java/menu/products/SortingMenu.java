package menu.products;

import exceptionalMassage.ExceptionalMassage;
import menu.menuAbstract.Menu;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class SortingMenu extends Menu {
    public static ArrayList<String> currentSortsList = new ArrayList<>();
    public SortingMenu(Menu parentMenu) {
        super("Sorting Menu", parentMenu);
        Menu showAvailableSorts = new Menu("Show All Sorts", this) {
            @Override
            public void show() {
                for (String sort : controller.controlGetAllAvailableSorts()) {
                    System.out.println(sort);
                }
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^show available sorts$", showAvailableSorts);
        menuForShow.add("Show Available Sorts");

        Menu sort = new Menu("Sort", this) {
            @Override
            public void show() {

            }

            @Override
            public void execute() {
                String regex = "^sort (.+)$";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()){
                    if(controller.isThisSortAvailable(matcher.group(1))){
                        System.out.println("sort added!");
                        currentSortsList.add(matcher.group(1));
                    }   else    {
                        System.out.println("Sort not available!");
                    }
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^sort (.+)$", sort);
        menuForShow.add("Sort [an available sort]");

        Menu currentSort = new Menu("Current Sort", this) {
            @Override
            public void show() {
                for (String s : currentSortsList) {
                    System.out.println(s);
                }
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^current sort$", currentSort);
        menuForShow.add("Current Sort");

        Menu disableSort = new Menu("Disable Sort", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                String regex = "^disable sort (.+)$";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()){
                    try {
                        currentSortsList.remove(matcher.group(1));
                        System.out.println("sort removed!");
                    } catch (Exception ex){
                        System.out.println(ex.getMessage());
                    }
                }
            }
        };
        menusIn.put("^disable sort (.+)$" , disableSort);
        menuForShow.add("Disable Sort [appliedSort]");



    }
}
