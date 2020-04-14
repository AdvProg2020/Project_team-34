package menu.profile.supervisorProfileMenu;

import menu.menuAbstract.Menu;

public class ManageRequestsMenu extends Menu {
    public ManageRequestsMenu(Menu parentMenu) {
        super("Manage Requests Menu", parentMenu);

        Menu Details = new Menu("Details", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^$details (\\w+)", Details);
        menuForShow.add("Details");

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
