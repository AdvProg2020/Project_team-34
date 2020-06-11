module java.gui.loginMenu{
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.sql;
    requires gson;

    opens gui.loginMenu to javafx.fxml;
    exports gui.loginMenu;

    opens main to javafx.fxml;
    exports main;

}
