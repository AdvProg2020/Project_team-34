package menu.menuAbstract;

import controller.Controller;
import exceptionalMassage.ExceptionalMassage;
import menu.loginMenu.LoginMenu;
import menu.profile.customerProfileMenu.CustomerProfileMenu;
import menu.profile.supervisorProfileMenu.SupervisorProfileMenu;
import menu.profile.supplierProfileMenu.SupplierProfileMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Aryan Ahadinia
 * @since 0.0.1
 */

public abstract class Menu {
    public static Scanner scanner = new Scanner(System.in);
    protected static Controller controller = new Controller();
    protected ArrayList<String> menuForShow;
    protected HashMap<String, Menu> menusIn;
    protected ArrayList<String> menuForShowClone;
    protected HashMap<String, Menu> menusInClone;
    public String command;
    protected Menu parentMenu;
    private String menuName;

    public Menu(String menuName, Menu parentMenu) {
        this.menuName = menuName;
        this.parentMenu = parentMenu;
        menuForShowClone = new ArrayList<>();
        menusInClone = new HashMap<>();
        menuForShow = new ArrayList<>();
        menusIn = new HashMap<>();
        if (!(menuName.equals("Help") || menuName.equals("Sort") || menuName.equals("Back"))) {
            Menu Help = new Menu("Help", this) {
                @Override
                public void show() {
                }

                @Override
                public void execute() {
                    for (String s : parentMenu.menusIn.keySet()) {
                        System.out.println("\t" + s);
                    }
                    parentMenu.show();
                    parentMenu.execute();
                }
            };
            menusIn.put("^help$", Help);
            menuForShow.add("Help");

            Menu SortCommands = new Menu("Sort", this) {
                @Override
                public void show() {
                }

                @Override
                public void execute() {
                }
            };
            menusIn.put("^sort$", SortCommands);
            menuForShow.add("Sort");

            if (this.parentMenu == null) {
                Menu exit = new Menu("Exit", this) {
                    @Override
                    public void show() {

                    }

                    @Override
                    public void execute() {
                        System.exit(0);
                    }
                };
                menusIn.put("^exit$", exit);
                menuForShow.add("Exit");
            } else {
                menusIn.put("^back$", parentMenu);
                menuForShow.add("Back");
            }
        }
    }

    public static Matcher getMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    public void show() {
        menusInClone = new HashMap<>(menusIn);
        menuForShowClone = new ArrayList<>(menuForShow);
        if (!controller.getAccountController().hasSomeOneLoggedIn()) {
            if (!(menuName.equals("Login/Register Menu") || menuName.equals("Login") || menuName.equals("Create Account"))) {
                menusInClone.put("^login/register$", new LoginMenu(this));
                menuForShowClone.add("Login/Register");
            }
        } else {
            if (!menuName.equals("Logout Command")) {
                Menu LogoutCommand = new Menu("Logout Command", this) {
                    @Override
                    public void show() {
                    }

                    @Override
                    public void execute() {
                        controller.getAccountController().controlLogout();
                        Menu newLoginMenu = new LoginMenu(null);
                        newLoginMenu.show();
                        newLoginMenu.execute();
                    }
                };
                menusInClone.put("^logout$", LogoutCommand);
                menuForShowClone.add("Logout");
            }

            if (!(menuName.equals("Logout Command") || menuName.equals("Profile Viewer Terminal"))) {
                Menu ProfileViewerTerminal = new Menu("Profile Viewer Terminal", this) {
                    @Override
                    public void show() {
                    }

                    @Override
                    public void execute() {
                        String type = controller.getAccountController().loggedInAccountType();
                        Menu nextMenu = null;
                        if (type.equals("Customer")) {
                            nextMenu = new CustomerProfileMenu(parentMenu);
                        } else if (type.equals("Supplier")) {
                            nextMenu = new SupplierProfileMenu(parentMenu);
                        } else if (type.equals("Supervisor")) {
                            nextMenu = new SupervisorProfileMenu(parentMenu);
                        }
                        nextMenu.show();
                        nextMenu.execute();
                    }
                };
                menusInClone.put("^profile$", ProfileViewerTerminal);
                menuForShowClone.add("Profile");
            }
        }
        for (String s : menuForShow) {
            System.out.println(s);
        }
    }

    public void execute() {
        command = scanner.nextLine();
        Menu nextMenu = this;
        for (String menuRegex : menusInClone.keySet()) {
            if (command.matches(menuRegex)) {
                nextMenu = menusInClone.get(menuRegex);
                break;
            }
        }
        nextMenu.command = command;
        //check aryan
        nextMenu.show();
        nextMenu.execute();
    }
}
