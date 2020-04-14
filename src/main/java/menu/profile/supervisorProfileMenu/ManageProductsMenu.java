package menu.profile.supervisorProfileMenu;

import menu.menuAbstract.Menu;

public class ManageProductsMenu extends Menu {
    public ManageProductsMenu(Menu parentMenu) {
        super("Manage Products", parentMenu);

        Menu RemoveProduct = new Menu("Remove Product", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^remove \\w+$", RemoveProduct);
        menuForShow.add("Remove");
    }
}
