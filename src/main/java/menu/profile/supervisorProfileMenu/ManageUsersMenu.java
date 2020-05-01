package menu.profile.supervisorProfileMenu;

import account.Account;
import exceptionalMassage.ExceptionalMassage;
import menu.menuAbstract.Menu;

import java.util.regex.Matcher;

public class ManageUsersMenu extends Menu {
    public ManageUsersMenu(Menu parentMenu) {
        super("Manage User Menu", parentMenu);

        Menu ViewCommand = new Menu("View", this) {
            @Override
            public void show() {
                Matcher commandMatcher = getMatcher(parentMenu.command, "^view (\\w+)$");
                if (commandMatcher.find()) {
                    System.out.println("View user " + commandMatcher.group(1));
                }
            }

            @Override
            public void execute() {
                Matcher commandMatcher = getMatcher(parentMenu.command, "^view (\\w+)$");
                if (commandMatcher.find()) {
                    try {
                        String info = controller.controlViewUserInfo(commandMatcher.group(1));
                        System.out.println(info);
                    } catch (ExceptionalMassage e) {
                        System.out.println(e.getMessage());
                    }
                    parentMenu.show();
                    parentMenu.execute();
                }
            }
        };
        menusIn.put("^view \\w+$", ViewCommand);
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
