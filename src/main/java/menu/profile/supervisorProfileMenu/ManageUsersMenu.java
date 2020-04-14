package menu.profile.supervisorProfileMenu;

import menu.menuAbstract.Menu;

public class ManageUsersMenu extends Menu {
    public ManageUsersMenu(Menu parentMenu) {
        super("Manage User Menu", parentMenu);

        Menu View = new Menu("View", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^view \\w+$", View);
        menuForShow.add("View");

        Menu DeleteUser = new Menu("Delete User", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^delete user \\w+$", DeleteUser);
        menuForShow.add("Delete User");

        Menu CreateSupervisorProfile = new Menu("Create Supervisor Profile", this) {
            @Override
            public void show() {
            }

            @Override
            public void execute() {
            }
        };
        menusIn.put("^create manager profile \\w+$", CreateSupervisorProfile);
        menuForShow.add("Create Manager Profile");
    }
}
