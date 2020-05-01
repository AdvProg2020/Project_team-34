package menu.profile.supervisorProfileMenu;

import exceptionalMassage.ExceptionalMassage;
import menu.menuAbstract.Menu;
import product.Category;

import java.util.regex.Matcher;

public class ManageCategoriesMenu extends Menu {
    public ManageCategoriesMenu(Menu parentMenu) {
        super("Manage Categories Menu", parentMenu);

        Menu AddCategoryCommand = new Menu("Add Category Command", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                Matcher commandMatcher = getMatcher(parentMenu.command, "^add (\\w+)$");
                commandMatcher.find();
                String name = commandMatcher.group(1);
                System.out.println("Enter parent category name: ");
                String parentCategoryName = scanner.nextLine();
                System.out.println("Is this category a category classifier (y/n): ");
                String isParentInput = scanner.nextLine();
                if (isParentInput.equals("y") || isParentInput.equals("n")) {
                    boolean isParent;
                    isParent = isParentInput.equals("y");
                    try {
                        controller.controlAddCategory(name, isParent, parentCategoryName);
                    } catch (ExceptionalMassage e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("Invalid input.");
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^add (\\w+)$", AddCategoryCommand);
        menuForShow.add("Add Category");

        Menu RemoveCategoryCommand = new Menu("Remove Category Command", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                Matcher commandMatcher = getMatcher(parentMenu.command, "^add (\\w+)$");
                commandMatcher.find();
                String name = commandMatcher.group(1);
                try {
                    controller.controlRemoveCategory(name);
                } catch (ExceptionalMassage e) {
                    System.out.println(e.getMessage());
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^remove (\\w+)$", RemoveCategoryCommand);
        menuForShow.add("Remove Category");

        Menu EditCategoryCommand = new Menu("Edit Category Command", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                Matcher commandMatcher = getMatcher(parentMenu.command, "^add (\\w+)$");
                commandMatcher.find();
                String name = commandMatcher.group(1);
                Category category = Category.getCategoryByName(name);
                if (category == null) {
                    System.out.println("Category not found.");
                } else {
                    EditCategoryMenu editCategoryMenu = new EditCategoryMenu("Edit Category", parentMenu, category);
                    editCategoryMenu.show();
                    editCategoryMenu.execute();
                }
            }
        };
        menusIn.put("^edit (\\w+)$", EditCategoryCommand);
        menuForShow.add("Edit Category");
    }
}
