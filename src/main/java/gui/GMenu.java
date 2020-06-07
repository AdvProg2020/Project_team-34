package gui;

import javafx.scene.Node;
import javafx.scene.Scene;
import menu.menuAbstract.Menu;

public abstract class GMenu {
    public static final Node HEADER = createHeader();
    private final Menu parentMenu;
    private final String menuName;

    public GMenu(String menuName, Menu parentMenu) {
        this.menuName = menuName;
        this.parentMenu = parentMenu;
    }

    public Scene getScene() {
        return createScene();
    }

    protected abstract Scene createScene();

    private static Node createHeader() {
        return null;
    }
}
