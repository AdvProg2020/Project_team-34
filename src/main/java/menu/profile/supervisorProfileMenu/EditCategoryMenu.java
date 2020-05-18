package menu.profile.supervisorProfileMenu;

import exceptionalMassage.ExceptionalMassage;
import menu.menuAbstract.Menu;
import product.Category;
import product.Product;

import java.util.regex.Matcher;

public class EditCategoryMenu extends Menu {
    Category category;

    public EditCategoryMenu(String menuName, Menu parentMenu) {
        super(menuName, parentMenu);

        Menu EditName = new Menu("Edit Category Name Command", this) {
            @Override
            public void show() {
                System.out.println("Edit Category Name: ");
            }

            @Override
            public void execute() {
                System.out.print("Enter new name: ");
                String newName = scanner.nextLine();
                try {
                    controller.getProductController().controlChangeCategoryName(category.getName(), newName);
                } catch (ExceptionalMassage e) {
                    System.out.println(e.getMessage());
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^edit name$", EditName);
        menuForShow.add("Edit Name");

        Menu AddSpecialFilter = new Menu("Add Special Filter Command", this) {
            @Override
            public void show() {
                System.out.println("Add Special Filter: ");
            }

            @Override
            public void execute() {
                System.out.print("Enter filter key: ");
                String filterKey = scanner.nextLine();
                System.out.print("Enter filter values separated by \",\" in a line: ");
                String filterValues = scanner.nextLine();
                try {
                    controller.getProductController().controlAddSpecialFieldToCategory(category.getName(), filterKey, filterValues);
                } catch (ExceptionalMassage e) {
                    System.out.println(e.getMessage());
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("add special filter", AddSpecialFilter);
        menuForShow.add("Add Special Filter");

        Menu RemoveSpecialField = new Menu("Remove Special Filter Command", this) {
            @Override
            public void show() {
                System.out.println("Remove Special Filter: ");
            }

            @Override
            public void execute() {
                System.out.print("Enter filter key: ");
                String filterKey = scanner.nextLine();
                System.out.print("Enter filter value: ");
                String filterValue = scanner.nextLine();
                try {
                    controller.getProductController().controlRemoveSpecialFieldFromCategory(category.getName(), filterKey, filterValue);
                } catch (ExceptionalMassage e) {
                    System.out.println(e.getMessage());
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("remove special field", RemoveSpecialField);
        menuForShow.add("Remove Special Field");
    }

    @Override
    public void execute() {
        String regex = "^edit category (.+)$";
        Matcher matcher = getMatcher(command, regex);
        if(matcher.find()){
            if(Category.getCategoryByName(matcher.group(1)) == null){
                System.out.println("No such category");
                parentMenu.show();
                parentMenu.execute();
            }
            category = Category.getCategoryByName(matcher.group(1));
        }
        super.execute();
    }
}


