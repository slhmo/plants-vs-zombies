package testgroup.plantsvszombies;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class MainMenu {     // singleton class
    private StackPane root;
    private AnchorPane anchorPane;
    private static MainMenu instance = null;
    PlayDay playDay = null;
    ImageView continueGame;


    private MainMenu(StackPane root) {
        this.root = root;
    }

    public static MainMenu createMenu(StackPane root) {
        if (instance == null) {
            instance = new MainMenu(root);
            instance.initializeAnchorPane();
        }
        else {
            root.getChildren().add(instance.anchorPane);
        }
        return instance;
    }

    private void initializeAnchorPane() {
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
            root.getChildren().add(anchorPane);
            System.out.println(root.getChildren());
        });
    }

    private void heavyTask() {
        try { //todo
            Thread.sleep(400);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        anchorPane = new AnchorPane();

        // create images
        ImageView mainMenuBackground = new ImageView("MainMenu.png");
        mainMenuBackground.setFitWidth(1920);
        mainMenuBackground.setFitHeight(1080);
        ImageView continueGameImg = createContinueGameImg();
        ImageView newGameImg = createNewGameImg();
        ImageView loadGameImg = createLoadGameImg();
        ImageView multiplayerImg = createMultiplayerImg();
        ImageView exitImg = createExitImg();

        anchorPane.getChildren().addAll(mainMenuBackground, newGameImg, loadGameImg, multiplayerImg, exitImg, continueGameImg);
    }

    private ImageView createNewGameImg() {
        ImageView newGameImg = new ImageView("mainMenuNewGameButton.png");
        newGameImg.setX(1000);
        newGameImg.setY(100);
        newGameImg.setFitWidth(750);
        newGameImg.setFitHeight(200);
        newGameImg.setOnMouseEntered(event -> {
            newGameImg.setOpacity(0.5);
        });
        newGameImg.setOnMouseExited(event -> {
            newGameImg.setOpacity(1);
        });
        newGameImg.setOnMouseClicked(event -> {
            updateContinueButton();
            playDay = new PlayDay(root);
            playDay.createGame();
            //todo
        });

        return newGameImg;
    }

    private ImageView createContinueGameImg() {
        continueGame = new ImageView("mainMenuContinueButton.png");
        continueGame.setX(1000);
        continueGame.setY(250);
        continueGame.setFitWidth(750);
        continueGame.setFitHeight(200);
        continueGame.setOpacity(0.5);
        return continueGame;
    }

    private ImageView createLoadGameImg() {
        ImageView loadGameImg = new ImageView("mainMenuLoadButton.png");
        loadGameImg.setX(1000);
        loadGameImg.setY(400);
        loadGameImg.setFitWidth(750);
        loadGameImg.setFitHeight(200);
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
        ImageView multiplayerImg = new ImageView("mainMenuMultiplayerButton.png");
        multiplayerImg.setX(1000);
        multiplayerImg.setY(550);
        multiplayerImg.setFitWidth(750);
        multiplayerImg.setFitHeight(200);
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

    private void updateContinueButton() {
        continueGame.setOpacity(1);
        continueGame.setOnMouseEntered(event -> {
            continueGame.setOpacity(0.5);
        });
        continueGame.setOnMouseExited(event -> {
            continueGame.setOpacity(1);
        });
        continueGame.setOnMouseClicked(event -> {
            playDay.continueGame();
        });

    }
}
