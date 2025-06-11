package testgroup.plantsvszombies;

import javafx.concurrent.Task;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import testgroup.plantsvszombies.plants.PeaShooter;
import testgroup.plantsvszombies.plants.RepeaterPeaShooter;
import testgroup.plantsvszombies.plants.SnowPeaShooter;
import testgroup.plantsvszombies.plants.Sunflower;


public class PlayDay {
    private StackPane root;
    AnchorPane anchorPane = null;
    Grid grid;
    GridPane gridPane;
    Selector selector;

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
        ImageView menuButton = createMenuButton();
        anchorPane.getChildren().addAll(frontYardImg, menuButton);

        grid = new Grid();

        selector = new Selector(anchorPane);

        gridPane = createGridPane();
        anchorPane.getChildren().add(gridPane);

    }

    private GridPane createGridPane() {
        GridPane pane = new GridPane();
        pane.setLayoutX(480);
        pane.setLayoutY(100);
        pane.setGridLinesVisible(true);
        for (int i = 0; i<5; i++) {
            for (int j = 0; j<9; j++) {
                final int row = i;
                final int column = j;
                StackPane cell = new StackPane();
                cell.setOnMouseClicked(mouseEvent -> {
                    int selectedType = selector.selectedInt;
                    System.out.println("selected type int: " + selectedType);
                    if (selectedType != 0) {
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

        switch (selected) {

            case 0:
                return;

            case 1:
                if (Sunflower.PRICE > selector.getBalance())
                    return;
                new Sunflower(grid, stackPane, row, column, selector);
                selector.paySunPrice(Sunflower.PRICE);
                return;

            case 2:
                if (PeaShooter.PRICE > selector.getBalance())
                    return;
                new PeaShooter(grid, stackPane, row, column);
                selector.paySunPrice(PeaShooter.PRICE);
                return;

            case 3:
                if (RepeaterPeaShooter.PRICE > selector.getBalance())
                    return;
                new RepeaterPeaShooter(grid, stackPane, row, column);
                selector.paySunPrice(RepeaterPeaShooter.PRICE);
                return;

            case 4:
                if (SnowPeaShooter.PRICE > selector.getBalance())
                    return;
                new SnowPeaShooter(grid, stackPane, row, column);
                selector.paySunPrice(SnowPeaShooter.PRICE);
                return;

            // todo add plants

            case 19:
                if (grid.getPlantsList()[row][column] != null) {
                    System.out.println("1");
                    grid.getPlantsList()[row][column].vanish();
                    return;
                }
                return;

        }

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


}
