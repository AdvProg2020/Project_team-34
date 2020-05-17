package menu.profile.supervisorProfileMenu;

import exceptionalMassage.ExceptionalMassage;
import menu.menuAbstract.Menu;
import product.Product;

public class ManageProductsMenu extends Menu {
    public ManageProductsMenu(Menu parentMenu) {
        super("Manage Products", parentMenu);

        Menu RemoveProduct = new Menu("Remove Product", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                String id;
                System.out.println("Enter the product ID");
                id = scanner.nextLine();
                try {
                    controller.getProductController().controlRemoveProductById(id);
                } catch (ExceptionalMassage ex){
                    System.out.println(ex.getMessage());
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^remove \\w+$", RemoveProduct);
        menuForShow.add("Remove");
    }

    @Override
    public void show() {
        for (Product product : controller.getProductController().controlFilterGetFilteredAndSortedProducts()) {
            System.out.println("name =>" + product.getName() + "ID =>" + product.getProductId());
        }
        super.show();
    }
}
