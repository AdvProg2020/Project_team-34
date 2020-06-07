package gui;

import menu.menuAbstract.Menu;

public abstract class GMenu {
    public static final Node Header = createHeader();
    protected Menu parentMenu;
    private String menuName;
    private Scene scene;

    public GMenu(String menuName, Menu parentMenu) {
        this.menuName = menuName;
        this.parentMenu = parentMenu;
    }

    private static Node createHeader() {
        return null;
    }
}
