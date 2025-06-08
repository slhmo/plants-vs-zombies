package testgroup.plantsvszombies;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class MainMenu {
    StackPane root;
    AnchorPane anchorPane;

    public MainMenu(StackPane root) {
        this.root = root;
    }

    public void createMenu() {
        LoadingScreen loadingScreen = new LoadingScreen(root);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                heavyTask();
                return null;
            }
        };

        loadingScreen.runTask(task);
        task.setOnSucceeded(event -> {
            loadingScreen.done();
            root.getChildren().add(root.getChildren().size() - 1, anchorPane);
            System.out.println(root.getChildren());
        });

    }
    private void heavyTask() {
        try { //todo
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        anchorPane = new AnchorPane();

        // create images
        ImageView mainMenuBackground = new ImageView("MainMenu.png");
        mainMenuBackground.setFitWidth(1920);
        mainMenuBackground.setFitHeight(1080);
        ImageView newGameImg = createNewGameImg();
        ImageView loadGameImg = createLoadGameImg();
        ImageView multiplayerImg = createMultiplayerImg();
        ImageView exitImg = createExitImg();

        anchorPane.getChildren().addAll(mainMenuBackground, newGameImg, loadGameImg, multiplayerImg, exitImg);
    }

    private ImageView createNewGameImg() {
        ImageView newGameImg = new ImageView("newGameMainMenu.png");
        newGameImg.setX(1050);
        newGameImg.setY(100);
        newGameImg.setFitWidth(600);
        newGameImg.setFitHeight(300);
        newGameImg.setOnMouseEntered(event -> {
            newGameImg.setOpacity(0.5);
        });
        newGameImg.setOnMouseExited(event -> {
            newGameImg.setOpacity(1);
        });
        newGameImg.setOnMouseClicked(event -> {
            //todo
        });

        return newGameImg;
    }

    private ImageView createLoadGameImg() {
        ImageView loadGameImg = new ImageView("loadGameMainMenu.png");
        loadGameImg.setX(1050);
        loadGameImg.setY(300);
        loadGameImg.setFitWidth(600);
        loadGameImg.setFitHeight(300);
        loadGameImg.setOnMouseEntered(event -> {
            loadGameImg.setOpacity(0.5);
        });
        loadGameImg.setOnMouseExited(event -> {
            loadGameImg.setOpacity(1);
        });
        loadGameImg.setOnMouseClicked(event -> {
            //todo
        });

        return loadGameImg;
    }

    private ImageView createMultiplayerImg() {
        ImageView multiplayerImg = new ImageView("multiplayerMainMenu.png");
        multiplayerImg.setX(1050);
        multiplayerImg.setY(500);
        multiplayerImg.setFitWidth(600);
        multiplayerImg.setFitHeight(300);
        multiplayerImg.setOnMouseEntered(event -> {
            multiplayerImg.setOpacity(0.5);
        });
        multiplayerImg.setOnMouseExited(event -> {
            multiplayerImg.setOpacity(1);
        });
        multiplayerImg.setOnMouseClicked(event -> {
            //todo
        });

        return multiplayerImg;
    }

    private ImageView createExitImg() {
        ImageView exitImg = new ImageView("exitMainMenu.png");
        exitImg.setX(1350);
        exitImg.setY(850);
        exitImg.setFitWidth(200);
        exitImg.setFitHeight(100);
        exitImg.setOnMouseEntered(event -> {
            exitImg.setOpacity(0.5);
        });
        exitImg.setOnMouseExited(event -> {
            exitImg.setOpacity(1);
        });
        exitImg.setOnMouseClicked(event -> {
            Platform.exit();
        });

        return exitImg;
    }
}
