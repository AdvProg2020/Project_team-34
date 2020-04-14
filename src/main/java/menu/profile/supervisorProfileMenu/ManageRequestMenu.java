package menu.profile.supervisorProfileMenu;

import menu.menuAbstract.Menu;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public class ManageRequestMenu extends Menu {
    public ManageRequestMenu(Menu parentMenu) {
        super("Manage Request Menu", parentMenu);

        Menu AcceptRequest = new Menu("Accept Request", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^accept (\\w+)$", AcceptRequest);
        menuForShow.add("Accept");

        Menu DeclineRequest = new Menu("Decline Request", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^decline (\\w+)$", DeclineRequest);
        menuForShow.add("Decline");
    }
}
