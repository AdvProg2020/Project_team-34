package menu.profile.supervisorProfileMenu;

import exceptionalMassage.ExceptionalMassage;
import menu.menuAbstract.Menu;

import java.util.regex.Matcher;

public class ManageUsersMenu extends Menu {
    public ManageUsersMenu(Menu parentMenu) {
        super("Manage User Menu", parentMenu);

        Menu ViewCommand = new Menu("View", this) {
            @Override
            public void show() {
                Matcher commandMatcher = getMatcher(command, "^view (\\w+)$");
                if (commandMatcher.find()) {
                    System.out.println("View user " + commandMatcher.group(1));
                }
            }

            @Override
            public void execute() {
                Matcher commandMatcher = getMatcher(command, "^view (\\w+)$");
                if (commandMatcher.find()) {
                    try {
                        String info = controller.getAccountController().controlViewUserInfo(commandMatcher.group(1));
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
                Matcher commandMatcher = getMatcher(command, "^delete user (\\w+)$");
                commandMatcher.find();
                String username = commandMatcher.group(1);
                try {
                    controller.getAccountController().controlDeleteUser(username);
                    System.out.println("User deleted successfully.");
                } catch (ExceptionalMassage e) {
                    System.out.println(e.getMessage());
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^delete user \\w+$", DeleteUser);
        menuForShow.add("Delete User");

        Menu CreateSupervisorProfile = new Menu("Create Supervisor Profile", this) {
            @Override
            public void show() {
                System.out.println("Create Supervisor Profile:");
            }

            @Override
            public void execute() {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                System.out.print("Enter family name: ");
                String familyName = scanner.nextLine();
                System.out.print("Enter email: ");
                String email = scanner.nextLine();
                System.out.print("Enter phone number: ");
                String phoneNumber = scanner.nextLine();
                try {
                    controller.getAccountController().controlCreateAccount(username, "supervisor", name, familyName, email, phoneNumber, password, 0, null);
                } catch (ExceptionalMassage e) {
                    System.out.println(e.getMessage());
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
        menusIn.put("^create manager profile$", CreateSupervisorProfile);
        menuForShow.add("Create Manager Profile");
    }

    @Override
    public void show() {
        System.out.println(controller.getAccountController().controlGetListOfAccounts());
        super.show();
    }
}
