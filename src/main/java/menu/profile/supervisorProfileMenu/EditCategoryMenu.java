package menu.profile.supervisorProfileMenu;

import exceptionalMassage.ExceptionalMassage;
import menu.menuAbstract.Menu;
import product.Category;

public class EditCategoryMenu extends Menu {
    Category category;

    public EditCategoryMenu(String menuName, Menu parentMenu, Category category) {
        super(menuName, parentMenu);
        this.category = category;

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
                    controller.controlChangeCategoryName(category.getName(), newName);
                } catch (ExceptionalMassage e) {
                    System.out.println(e.getMessage());
                }
            }
        };
        menusIn.put("^edit name$", EditName);
        menuForShow.add("Edit Name");

        Menu AddSpecialFilter = new Menu("Add Special Filter Command", this) {
            @Override
            public void show() {
                System.out.println("Remove Special Filter: ");
            }

            @Override
            public void execute() {
                System.out.print("Enter filter key: ");
                String filterKey = scanner.nextLine();
                System.out.print("Enter filter values separated by \",\" in a line: ");
                String filterValues = scanner.nextLine();
                try {
                    controller.controlAddSpecialFilterToCategory(category.getName(), filterKey, filterValues);
                } catch (ExceptionalMassage e) {
                    System.out.println(e.getMessage());
                }
            }
        };
        menusIn.put("add special filter", AddSpecialFilter);
        menuForShow.add("Add Special Filter");

        Menu RemoveSpecialFilter = new Menu("Remove Special Filter Command", this) {
            @Override
            public void show() {
                System.out.println("Add Special Filter: ");
            }

            @Override
            public void execute() {
                System.out.print("Enter filter key: ");
                String filterKey = scanner.nextLine();
                try {
                    controller.controlRemoveSpecialFilterFromCategory(category.getName(), filterKey);
                } catch (ExceptionalMassage e) {
                    System.out.println(e.getMessage());
                }
            }
        };
        menusIn.put("remove special filter", AddSpecialFilter);
        menuForShow.add("Remove Special Filter");
    }
}