package menu.profile.supervisorProfileMenu;

import exceptionalMassage.ExceptionalMassage;
import menu.menuAbstract.Menu;
import product.Category;

import java.util.regex.Matcher;
//show arian
public class ManageCategoriesMenu extends Menu {
    public ManageCategoriesMenu(Menu parentMenu) {
        super("Manage Categories Menu", parentMenu);
        Menu  editCategory = new Menu("Edit Category", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                String regex = "^edit (\\w+)$";
                Matcher matcher = getMatcher(command, regex);
                if(matcher.find()){
                    String newName;
                    System.out.println("Enter the new Name: ");
                    newName  = scanner.nextLine();
                    try {
                        controller.getProductController().controlChangeCategoryName(matcher.group(1), newName);
                    } catch (ExceptionalMassage ex){
                        System.out.println(ex.getMessage());
                    }
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^edit \\w+$", editCategory);
        menuForShow.add("Edit");

        Menu RemoveProduct = new Menu("Add category", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                String newCategoryName;
                String parentName;
                System.out.println("Enter the name:");
                newCategoryName = scanner.nextLine();
                System.out.println("Enter the upper level category(if you dont want enter 0)");
                parentName = scanner.nextLine();
                if(parentName == "0"){
                    try{
                        controller.getProductController().controlAddCategory(newCategoryName, true,null);
                    } catch (ExceptionalMassage ex){
                        System.out.println(ex.getMessage());
                        parentMenu.show();
                        parentMenu.execute();
                    }
                } else {
                    try{
                        controller.getProductController().controlAddCategory(newCategoryName, false,parentName);
                    } catch (ExceptionalMassage ex){
                        System.out.println(ex.getMessage());
                        parentMenu.show();
                        parentMenu.execute();
                    }
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
