package gui;

import controller.Controller;
import javafx.scene.Node;

import javafx.scene.Scene;
import javafx.stage.Stage;
import menu.menuAbstract.Menu;

public abstract class GMenu {
    public final Node HEADER = createHeader();
    protected final GMenu parentMenu;
    protected final String menuName;
    protected final Stage stage;
    public final Controller controller;

    public GMenu(String menuName, GMenu parentMenu, Stage stage) {
        this.menuName = menuName;
        this.parentMenu = parentMenu;
        this.controller = new Controller();
        this.stage = stage;
    }

    public Scene getScene() {
        return createScene();
    }

    protected abstract Scene createScene();

    private  Node createHeader() {
        return null;
    }
}
