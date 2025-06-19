package testgroup.plantsvszombies.multiPlayer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import testgroup.plantsvszombies.*;
import testgroup.plantsvszombies.plants.*;
import testgroup.plantsvszombies.zombies.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;


public class ClientGame implements Playable {
    private StackPane root;
    AnchorPane anchorPane = null;
    Grid grid;
    GridPane gridPane;
    Selector selector;
    Timeline sunGenerator;
    ArrayList<Card> chosenCards;
    PVZClient client;

    public ClientGame(StackPane root, PVZClient client) {
        this.root = root;
        this.client = client;
    }

    public void createGame() {
        LoadingScreen loadingScreen = new LoadingScreen(root);

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                heavyTaskCreate();
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

    private void heavyTaskCreate() {
        try { //todo
            Thread.sleep(400);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        anchorPane = new AnchorPane();

        ImageView frontYardImg = new ImageView("FrontYard.png");
        frontYardImg.setFitWidth(1920);
        frontYardImg.setFitHeight(1080);
        ImageView menuButton = createMenuButton();
        anchorPane.getChildren().addAll(frontYardImg, menuButton);

        Card.revert();

        createChooserMenu();
    }

    private void createChooserMenu() {
        chosenCards = new ArrayList<>(6);

        ImageView chooserMenuImg = new ImageView(getClass().getResource("/selector/chooserMenu.png").toString());
        chooserMenuImg.setFitWidth(600);
        chooserMenuImg.setFitHeight(600);
        chooserMenuImg.setX(660);
        chooserMenuImg.setY(140);

        for (Card card : Card.getCards()) {
            if (card != null) {
                card.getImageView().setFitWidth(95);
                card.getImageView().setFitHeight(120);
            }
        }

        GridPane chooserMenuGrid = new GridPane(2, 10);
        chooserMenuGrid.setLayoutX(670);
        chooserMenuGrid.setLayoutY(200);
        int count = 0;
        for (int i = 0; i<3; i++) {
            for (int j = 0; j<6; j++) {
                if (Card.getCards()[count] != null) {
                    StackPane cell = new StackPane();
                    cell.getChildren().add(Card.getCards()[count].getImageView());
                    final int id = count;
                    cell.setOnMouseClicked(event -> {
                        if (chosenCards.contains(Card.getCards()[id])) {
                            Card.getCards()[id].getImageView().setOpacity(1);
                            chosenCards.remove(Card.getCards()[id]);
                        }
                        else if (!chosenCards.contains(Card.getCards()[id]) && chosenCards.size() < 6){
                            Card.getCards()[id].getImageView().setOpacity(0.6);
                            chosenCards.add(Card.getCards()[id]);
                        }
                        System.out.println(chosenCards);
                    });
                    chooserMenuGrid.add(cell, j, i);
                }
                count++;
            }
        }

        Button play = new Button("play");
        play.setLayoutX(1000);
        play.setLayoutY(800);
        play.setOnMouseClicked(event -> {
            if (chosenCards.size() == 6) {
                client.sendMessage("CHOSE");
                anchorPane.getChildren().removeAll(chooserMenuImg, chooserMenuGrid, play);
                selector = new Selector(anchorPane, chosenCards, 2500);
                gridPane = createGridPane();
                anchorPane.getChildren().add(gridPane);
                grid = new Grid(this, anchorPane, chosenCards);
                sunGenerator = new Timeline(new KeyFrame(Duration.seconds(15), event1 -> {
                    Random random = new Random();
                    new SunPoint(anchorPane, selector, random.nextInt(300, 1500), random.nextInt(150, 800));
                }));
                sunGenerator.setCycleCount(-1);
                sunGenerator.play();
                grid.stopAll();
            }
        });

        anchorPane.getChildren().addAll(chooserMenuImg, chooserMenuGrid, play);
    }

    private GridPane createGridPane() {
        GridPane pane = new GridPane();
        pane.setLayoutX(480);
        pane.setLayoutY(100);
//        pane.setGridLinesVisible(true);
        for (int i = 0; i<5; i++) {
            for (int j = 0; j<9; j++) {
                final int row = i;
                final int column = j;
                StackPane cell = new StackPane();
                cell.setOnMouseClicked(mouseEvent -> {
                    int selectedType = selector.getSelectedId();
                    System.out.println("selected type int: " + selectedType);
                    if (selectedType != -1) {
                        parseSelected(grid, cell, row, column, selectedType);
                    }
                });
                cell.setPrefSize(152, 175);
                pane.add(cell, j, i);
            }
        }
        return pane;
    }

    private void parseSelected(Grid grid, StackPane stackPane, int row, int column, int selected) {
        if (grid.getPlantsList()[row][column] != null && selected != 19) {
            System.out.println("cell already in use");
            return;
        }

        if (!selector.selectedAvailable()) {
            return;
        }

        selector.getSelectedCard().place();

        switch (selected) {
            case -1:
                return;

            case 0:
                new Sunflower(grid, stackPane, row, column, selector);
                break;

            case 1:
                new PeaShooter(grid, stackPane, row, column);
                break;

            case 2:
                new RepeaterPeaShooter(grid, stackPane, row, column);
                break;

            case 3:
                new SnowPeaShooter(grid, stackPane, row, column);
                break;

            case 4:
                new WallNut(grid, stackPane, row, column);
                break;

            case 5:
                new TallNut(grid, stackPane, row, column);
                break;

            case 6:
                new CherryBomb(grid, stackPane, row, column);
                break;

            case 7:
                new Jalapeno(grid, stackPane, row, column);
                break;

            // todo add plants

            case 19:
                if (grid.getPlantsList()[row][column] != null) {
                    System.out.println("1");
                    grid.getPlantsList()[row][column].vanish();
                    return;
                }
                break;
        }
        selector.paySunPrice(selector.getSelectedCard().getPrice());
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
            if (grid != null)
                grid.stopAll();
            client.sendMessage("STOP GAME");
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
            root.getChildren().remove(anchorPane1);
            client.sendMessage("RESUME GAME");
            if (grid != null)
                grid.resumeAll();
        });


        ImageView saveGameImg = new ImageView("pauseMenuSaveButton.png");
        saveGameImg.setFitWidth(500);
        saveGameImg.setFitHeight(100);
        saveGameImg.setX(710);
        saveGameImg.setY(565);
        saveGameImg.setOpacity(0.75);


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
            client.sendMessage("EXIT GAME");
            MainMenu mainMenu = MainMenu.createMenu(root);
        });

        anchorPane1.getChildren().addAll(pauseMenuImg, resumeButtonImg, saveGameImg, mainMenuImg);
        return anchorPane1;
    }

    public void gameLost() {
        client.sendMessage("LOST");
        grid.stopAll();
        Button button = new Button("lost");
        button.setLayoutX(800);
        button.setOnMouseClicked(event1 -> {
            root.getChildren().clear();
            MainMenu.createMenu(root);
        });
        anchorPane.getChildren().add(button);
    }

    public void othersWon() {
        grid.stopAll();
        Label tmp = new Label("others WON!");
        Button button = new Button("lost");
        button.setLayoutX(800);
        button.setOnMouseClicked(event1 -> {
            root.getChildren().clear();
            MainMenu.createMenu(root);
        });
        anchorPane.getChildren().addAll(button, tmp);
    }

    public void gameWon() {
        client.sendMessage("WON");
        grid.stopAll();
        Button button = new Button("won");
        button.setLayoutX(800);
        button.setLayoutY(400);
        button.setOnMouseClicked(event1 -> {
            root.getChildren().clear();
            MainMenu.createMenu(root);
        });
        anchorPane.getChildren().add(button);
    }

    public void stop() {
        sunGenerator.pause();
    }

    public void resume() {
        sunGenerator.play();
    }

    public Grid getGrid() {
        return grid;
    }

    public void allLeft() {

    }

    public void loadZombie(String s) {
        String[] tmp = s.split(" ");
        int type = Integer.parseInt(tmp[0]);
        int row = Integer.parseInt(tmp[1]);
        switch (type) {
            case 1:
                new SimpleZombie(grid, anchorPane, row);
                break;

            case 2:
                new ConeHeadZombie(grid, anchorPane, row);
                break;

            case 3:
                new BucketHeadZombie(grid, anchorPane, row);
                break;

            case 4:
                new ScreenDoorZombie(grid, anchorPane, row);
                break;
            case 5:
                new FootballZombie(grid, anchorPane, row);
        }
    }
}
