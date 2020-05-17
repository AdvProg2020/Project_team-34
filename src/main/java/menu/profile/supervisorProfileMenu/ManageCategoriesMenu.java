package menu.profile.supervisorProfileMenu;

import exceptionalMassage.ExceptionalMassage;
import menu.menuAbstract.Menu;

import java.util.regex.Matcher;
//show arian
public class ManageCategoriesMenu extends Menu {
    public ManageCategoriesMenu(Menu parentMenu) {
        super("Manage Categories Menu", parentMenu);

        menusIn.put("^edit category (\\w+)$", new EditCategoryMenu("Edit category",this));
        menuForShow.add("Edit category");


        Menu RemoveProduct = new Menu("Add category", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                System.out.println("Enter the name:");
                String newCategoryName = scanner.nextLine();
                System.out.print("Enter the upper level category (if you dont want enter 0): ");
                String parentName = scanner.nextLine();
                if (parentName.equals("0"))
                    parentName = "All Products";
                System.out.print("Is this category a category classifier? (y for yes, any other expresion for no): ");
                String isParentCategoryString = scanner.nextLine();
                boolean isParentCategoryBoolean = isParentCategoryString.equalsIgnoreCase("y");
                try {
                    controller.getProductController().controlAddCategory(newCategoryName, isParentCategoryBoolean, parentName);
                } catch (ExceptionalMassage e) {
                    System.out.println(e.getMessage());
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^add \\w+$", RemoveProduct);
        menuForShow.add("Add Category");

        Menu RemoveCategory = new Menu("Remove Category", this) {
            @Override
            public void show() {
            }
            @Override
            public void execute() {
                String name;
                System.out.println("Enter the name:");
                name = scanner.nextLine();
                try {
                    controller.getProductController().controlRemoveCategory(name);
                } catch (ExceptionalMassage ex){
                    System.out.println(ex.getMessage());
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^remove \\w+$", RemoveCategory);
        menuForShow.add("Remove");

    }

    @Override
    public void show() {
        System.out.println(controller.getProductController().controlGetAllCategories());
        super.show();
    }
}
