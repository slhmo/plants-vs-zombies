package testgroup.plantsvszombies;

import javafx.concurrent.Task;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import testgroup.plantsvszombies.plants.PeaShooter;
import testgroup.plantsvszombies.plants.Plant;


public class PlayDay {
    private StackPane root;
    AnchorPane anchorPane = null;
    Grid grid;
    private int selectorSelected;

    public PlayDay(StackPane root) {
        this.root = root;
    }

    public void createGame() {
        if (anchorPane != null) {
            // game already created
            root.getChildren().clear();
            root.getChildren().add(anchorPane);
            System.out.println(root.getChildren());
            return;
        }

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
            root.getChildren().clear();
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

        ImageView frontYardImg = new ImageView("FrontYard.png");
        frontYardImg.setFitWidth(1920);
        frontYardImg.setFitHeight(1080);

        grid = new Grid();
        AnchorPane selectorAnc = createSelector();
        ImageView menuButton = createMenuButton();

        anchorPane.getChildren().addAll(frontYardImg, selectorAnc, menuButton);

    }

    private ImageView createMenuButton() {
        ImageView menuButton = new ImageView("menuButtonInGame.png");
        menuButton.setX(1750);
        menuButton.setY(0);
        menuButton.setFitWidth(150);
        menuButton.setFitHeight(75);

        AnchorPane pauseMenu = createPauseMenu();

        menuButton.setOnMouseEntered(event -> {
            menuButton.setOpacity(0.8);
        });

        menuButton.setOnMouseExited(event -> {
            menuButton.setOpacity(1);
        });

        menuButton.setOnMouseClicked(event -> {
            //todo stop timeline
            root.getChildren().add(pauseMenu);
        });



        return menuButton;
    }

    private AnchorPane createPauseMenu() {
        AnchorPane anchorPane1 = new AnchorPane();
        ImageView pauseMenuImg = new ImageView("pauseMenu.png");
        pauseMenuImg.setFitWidth(1000);
        pauseMenuImg.setFitHeight(1000);
        pauseMenuImg.setX(460);
        pauseMenuImg.setY(40);

        ImageView resumeButtonImg = new ImageView("pauseMenuResumeButton.png");
        resumeButtonImg.setFitWidth(500);
        resumeButtonImg.setFitHeight(100);
        resumeButtonImg.setX(710);
        resumeButtonImg.setY(490);
        resumeButtonImg.setOnMouseEntered(event -> {
            resumeButtonImg.setOpacity(0.75);
        });

        resumeButtonImg.setOnMouseExited((event -> {
            resumeButtonImg.setOpacity(1);
        }));

        resumeButtonImg.setOnMouseClicked(event -> {
            root.getChildren().remove(anchorPane1);   // todo
        });


        ImageView saveGameImg = new ImageView("pauseMenuSaveButton.png");
        saveGameImg.setFitWidth(500);
        saveGameImg.setFitHeight(100);
        saveGameImg.setX(710);
        saveGameImg.setY(565);
        saveGameImg.setOnMouseEntered(event -> {
            saveGameImg.setOpacity(0.75);
        });

        saveGameImg.setOnMouseExited((event -> {
            saveGameImg.setOpacity(1);
        }));

        saveGameImg.setOnMouseClicked(event -> {
            // todo
        });


        ImageView mainMenuImg = new ImageView("pauseMenuMainMenuButton.png");
        mainMenuImg.setFitWidth(500);
        mainMenuImg.setFitHeight(100);
        mainMenuImg.setX(710);
        mainMenuImg.setY(640);
        mainMenuImg.setOnMouseEntered(event -> {
            mainMenuImg.setOpacity(0.75);
        });

        mainMenuImg.setOnMouseExited((event -> {
            mainMenuImg.setOpacity(1);
        }));

        mainMenuImg.setOnMouseClicked(event -> {
            root.getChildren().clear();
            MainMenu mainMenu = MainMenu.createMenu(root);
        });

        anchorPane1.getChildren().addAll(pauseMenuImg, resumeButtonImg, saveGameImg, mainMenuImg);
        return anchorPane1;
    }

    private AnchorPane createSelector() {
        AnchorPane anchorPane1 = new AnchorPane();

        ImageView selectorImg = new ImageView("selector.png");
        selectorImg.setX(0);
        selectorImg.setY(0);
        selectorImg.setFitWidth(800);
        selectorImg.setFitHeight(100);

        ImageView sunflowerCard = new ImageView("card_sunflower.png");
        setCardPlace(sunflowerCard, 0);

        ImageView peaShooterCard = new ImageView("card_peashooter.png");
        setCardPlace(peaShooterCard, 1);


        ImageView repeaterCard = new ImageView("card_repeaterpea.png");
        setCardPlace(repeaterCard, 2);

        anchorPane1.getChildren().addAll(selectorImg, sunflowerCard, peaShooterCard, repeaterCard);

        return anchorPane1;
    }

    private void setCardPlace(ImageView card, int n) {
        card.setFitWidth(40);
        card.setFitHeight(52);
        card.setX(40*n + 70 );
        card.setY(25);
        card.setOnMouseClicked(event -> {
            card.setY(25 + 5);
            selectorSelected = n;
        });
    }

    private void selectCard(int nthCard) {

    }


}
