package testgroup.plantsvszombies;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class WonLostMenu<T extends Playable> {
    private StackPane root;
    private AnchorPane anchorPane;
    T game;

    public WonLostMenu(StackPane root, T game){
        this.root = root;
        this.game = game;
    }

    public void createOfflineLostMenu() {
        anchorPane = new AnchorPane();
        ImageView back = createBackgroundLost();
        ImageView mainMenu = createMainMenuImg();
        ImageView tryAgain = createTryAgainImg();
        anchorPane.getChildren().addAll(back, mainMenu, tryAgain);
        root.getChildren().add(anchorPane);
    }

    public void createOfflineWonMenu() {
        anchorPane = new AnchorPane();
        ImageView back = createBackgroundWon();
        ImageView mainMenu = createMainMenuImg();
        ImageView playAgain = createPlayAgainImg();
        anchorPane.getChildren().addAll(back, mainMenu, playAgain);
        root.getChildren().add(anchorPane);
    }

    public void createMultiLostMenu() {  // todo
        anchorPane = new AnchorPane();
        ImageView back = createBackgroundLost();
        ImageView mainMenu = createMainMenuImg();
        anchorPane.getChildren().addAll(back, mainMenu);
        root.getChildren().add(anchorPane);
    }

    public void createMultiOthersWon() {  // todo
        anchorPane = new AnchorPane();
        ImageView back = createBackgroundOthersWon();
        ImageView mainMenu = createMainMenuImg();
        anchorPane.getChildren().addAll(back, mainMenu);
        root.getChildren().add(anchorPane);
    }

    public void createMultiWonMenu() {  // todo
        anchorPane = new AnchorPane();
        ImageView back = createBackgroundWon();
        ImageView mainMenu = createMainMenuImg();
        anchorPane.getChildren().addAll(back, mainMenu);
        root.getChildren().add(anchorPane);
    }

    private ImageView createBackgroundLost() {
        ImageView loseMainMenuBackground = new ImageView(getClass().getResource("/WonLostMenu/LoseBackGround.png").toString());
        loseMainMenuBackground.setFitWidth(1920);
        loseMainMenuBackground.setFitHeight(1080);
        return loseMainMenuBackground;
    }

    private ImageView createBackgroundOthersWon() {
        ImageView loseMainMenuBackground = new ImageView(getClass().getResource("/WonLostMenu/OthersWonBackGround.png").toString());
        loseMainMenuBackground.setFitWidth(1920);
        loseMainMenuBackground.setFitHeight(1080);
        return loseMainMenuBackground;
    }

    private ImageView createBackgroundWon() {
        ImageView loseMainMenuBackground = new ImageView(getClass().getResource("/WonLostMenu/WinBackGround.png").toString());
        loseMainMenuBackground.setFitWidth(1920);
        loseMainMenuBackground.setFitHeight(1080);
        return loseMainMenuBackground;
    }

    private ImageView createTryAgainImg() {
        ImageView TryAgainButtonImg = new ImageView(getClass().getResource("/WonLostMenu/LoseTryAgain.png").toString());
        TryAgainButtonImg.setX(340);
        TryAgainButtonImg.setY(480);
        TryAgainButtonImg.setFitWidth(1130);
        TryAgainButtonImg.setFitHeight(600);
        TryAgainButtonImg.setOnMouseEntered(event -> {
            TryAgainButtonImg.setScaleX(1.05);
            TryAgainButtonImg.setScaleY(1.05);
        });
        TryAgainButtonImg.setOnMouseExited(event -> {
            TryAgainButtonImg.setScaleX(1);
            TryAgainButtonImg.setScaleY(1);
        });
        TryAgainButtonImg.setOnMouseClicked(event -> {
            game.createGame();
        });
        return TryAgainButtonImg;
    }

    private ImageView createPlayAgainImg() {
        ImageView TryAgainButtonImg = new ImageView(getClass().getResource("/WonLostMenu/LoseTryAgain.png").toString());
        TryAgainButtonImg.setX(340);
        TryAgainButtonImg.setY(480);
        TryAgainButtonImg.setFitWidth(1130);
        TryAgainButtonImg.setFitHeight(600);
        TryAgainButtonImg.setOnMouseEntered(event -> {
            TryAgainButtonImg.setScaleX(1.05);
            TryAgainButtonImg.setScaleY(1.05);
        });
        TryAgainButtonImg.setOnMouseExited(event -> {
            TryAgainButtonImg.setScaleX(1);
            TryAgainButtonImg.setScaleY(1);
        });
        TryAgainButtonImg.setOnMouseClicked(event -> {
            game.createGame();
        });
        return TryAgainButtonImg;
    }

    private ImageView createMainMenuImg() {
        ImageView MainMenuButtonImg = new ImageView(getClass().getResource("/WonLostMenu/LoseMainMenu.png").toString());
        MainMenuButtonImg.setX(488);
        MainMenuButtonImg.setY(875);
        MainMenuButtonImg.setFitWidth(830);
        MainMenuButtonImg.setFitHeight(150);
        MainMenuButtonImg.setOnMouseEntered(event -> {
            MainMenuButtonImg.setScaleX(1.05);
            MainMenuButtonImg.setScaleY(1.05);
        });
        MainMenuButtonImg.setOnMouseExited(event -> {
            MainMenuButtonImg.setScaleX(1);
            MainMenuButtonImg.setScaleY(1);
        });
        MainMenuButtonImg.setOnMouseClicked(event -> {
            root.getChildren().clear();
            MainMenu.createMenu(root);
        });
        return MainMenuButtonImg;
    }
}
