package menu.profile.supplierProfileMenu;

import menu.menuAbstract.Menu;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class ViewOffsMenu extends Menu {
    public ViewOffsMenu(Menu parentMenu) {
        super("View Offs Menu", parentMenu);

        Menu View = new Menu("View", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {

            }
        };
        menusIn.put("^view (\\w+)$", View);
        menuForShow.add("View");

        Menu Edit = new Menu("Edit", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^edit (\\w+)$", Edit);
        menuForShow.add("Edit");

        Menu AddOff = new Menu("Add Menu", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
                //controller.controlCreateSale();
            }
        };
        menusIn.put("^add off$", AddOff);
        menuForShow.add("Add off");
    }
}
