package menu.menuAbstract;

import controller.Controller;
import menu.loginMenu.LoginMenu;

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
    protected String command;
    protected Menu parentMenu;
    private String menuName;

    public Menu(String menuName, Menu parentMenu) {
        this.menuName = menuName;
        this.parentMenu = parentMenu;
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
                        System.out.println(s);
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

            if (!controller.hasSomeOneLoggedIn()) {
                if (!(menuName.equals("Login/Register Menu") || menuName.equals("Login") || menuName.equals("Create Account"))) {
                    menusIn.put("^login/register$", new LoginMenu(this));
                    menuForShow.add("Login/Register");
                }
            } else {
                if (!menuName.equals("Logout Command")) {
                    Menu LogoutCommand = new Menu("Logout Command", this) {
                        @Override
                        public void show() {
                        }

                        @Override
                        public void execute() {
                        }
                    };
                    menusIn.put("^logout$", LogoutCommand);
                    menuForShow.add("Logout");
                }

                if (!(menuName.equals("Logout Command") || menuName.equals("Profile Viewer Terminal"))) {
                    Menu ProfileViewerTerminal = new Menu("Profile Viewer Terminal", this) {
                        @Override
                        public void show() {
                        }

                        @Override
                        public void execute() {
                        }
                    };
                    menusIn.put("^profile$", ProfileViewerTerminal);
                    menuForShow.add("Profile");
                }
            }
        }
    }

    public static Matcher getMatcher(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }

    public void show() {
        System.out.println(menuName + ":");
        for (String menu : menuForShow) {
            System.out.println(menu);
        }
    }

    public void execute() {
        command = scanner.nextLine();
        Menu nextMenu = this;
        for (String menuRegex : menusIn.keySet()) {
            if (command.matches(menuRegex)) {
                nextMenu = menusIn.get(menuRegex);
                break;
            }
        }
        nextMenu.command = command;
        nextMenu.show();
        nextMenu.execute();
    }
}
