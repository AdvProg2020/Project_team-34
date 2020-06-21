package main;

import gui.GMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Test extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ImageView imageView = GMenu.getImageView("./src/main/resources/icons/boughted.png", 500, 200);
        assert imageView != null;
        Scene scene = new Scene(new HBox(imageView));
        stage.setScene(scene);
        stage.show();
    }
}
