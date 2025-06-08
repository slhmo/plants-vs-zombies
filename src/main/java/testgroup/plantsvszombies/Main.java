package testgroup.plantsvszombies;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    MainMenu mainMenu;


    @Override
    public void start(Stage stage) throws IOException {
        // set scene/stage/pane
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 800, 600);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("f4"));

        ImageView startingImg = new ImageView("start.jpg");
        startingImg.setFitWidth(1920);
        startingImg.setFitHeight(1080);
        startingImg.setX(0);
        startingImg.setY(0);
        root.getChildren().add(startingImg);
        Button button = new Button("test");
        root.setAlignment(button, Pos.TOP_LEFT);
        button.toFront();
        root.getChildren().add(button);

        mainMenu = MainMenu.createMenu(root);

        System.out.println(root.getChildren());


        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}