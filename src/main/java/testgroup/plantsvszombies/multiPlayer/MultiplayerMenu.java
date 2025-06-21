package testgroup.plantsvszombies.multiPlayer;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicBoolean;

public class MultiplayerMenu {
    StackPane root;
    AnchorPane anchorPane;
    ImageView menuImg;
    ImageView createGameImg;
    ImageView joinGameImg;


    public MultiplayerMenu(StackPane root) {
        this.root = root;
        createMenu();
    }

    private void createMenu() {
        anchorPane = new AnchorPane();

        menuImg = new ImageView(getClass().getResource("/multiplayer/Menu.png").toString());
        menuImg.setFitWidth(1920);
        menuImg.setFitHeight(1080);

        createGameImg = new ImageView(getClass().getResource("/multiplayer/CreateGame.png").toString());
        createGameImg.setFitWidth(750);
        createGameImg.setFitHeight(200);
        createGameImg.setX(585);
        createGameImg.setY(100);

        createGameImg.setOnMouseEntered(event -> {
            createGameImg.setOpacity(0.5);
        });

        createGameImg.setOnMouseExited(event -> {
            createGameImg.setOpacity(1);
        });

        createGameImg.setOnMouseClicked(event -> {
            AnchorPane anchorPane1 = new AnchorPane();
            ImageView waitMenuImg = new ImageView(getClass().getResource("/multiplayer/WaitMenu.png").toString());
            waitMenuImg.setFitWidth(1920);
            waitMenuImg.setFitHeight(1080);

            InetAddress serverIP = GetRealIP.getIp();
            assert serverIP != null;
            Label ipAddressLabel = new Label(serverIP.toString());
            ipAddressLabel.setLayoutX(760);
            ipAddressLabel.setLayoutY(560);
            ipAddressLabel.setPrefSize(600, 150);
            ipAddressLabel.setStyle("-fx-font-size: 60px; -fx-text-fill: green;");

            PVZServer server = new PVZServer(root, anchorPane1);
            server.start();

            Button playButton = new Button("START GAME");
            playButton.setLayoutX(820);
            playButton.setLayoutY(850);
            playButton.setPrefSize(300, 100);
            playButton.setStyle("-fx-font-size: 30px; -fx-text-fill: green;");
            playButton.setOnMouseClicked(event1 -> {
                if (!server.getWorkers().isEmpty())
                    server.startGame();
            });

            root.getChildren().clear();
            anchorPane1.getChildren().addAll(waitMenuImg, ipAddressLabel, playButton);
            root.getChildren().add(anchorPane1);
        });

        joinGameImg = new ImageView(getClass().getResource("/multiplayer/JoinGame.png").toString());
        joinGameImg.setFitWidth(750);
        joinGameImg.setFitHeight(200);
        joinGameImg.setX(585);
        joinGameImg.setY(300);

        joinGameImg.setOnMouseEntered(event -> {
            joinGameImg.setOpacity(0.5);
        });

        joinGameImg.setOnMouseExited(event -> {
            joinGameImg.setOpacity(1);
        });
        joinGameImg.setOnMouseClicked(event -> {
            TextField ipTextField = new TextField("enter ip here");
            ipTextField.setLayoutX(760);
            ipTextField.setLayoutY(550);
            ipTextField.setPrefSize(400, 40);
            ipTextField.setStyle("-fx-font-size: 30px; -fx-text-fill: green;");
            AtomicBoolean firstTimeClickingOnTextBox = new AtomicBoolean(true);
            ipTextField.setOnMouseClicked(event1 -> {
                if (firstTimeClickingOnTextBox.get()) {
                    ipTextField.setText("192.168.");
                    firstTimeClickingOnTextBox.set(false);
                }
            });
            Button playButton = new Button("START GAME");
            playButton.setLayoutX(815);
            playButton.setLayoutY(750);
            playButton.setPrefSize(300, 50);
            playButton.setStyle("-fx-font-size: 30px; -fx-text-fill: green;");
            playButton.setOnMouseClicked(event1 -> {
                String ip = ipTextField.getText();
                PVZClient client = new PVZClient(root, anchorPane, ip);
                if (client.connected()) {
                    client.start();
                    client.startGame();
                }
            });
            anchorPane.getChildren().addAll(ipTextField, playButton);
        });

        anchorPane.getChildren().addAll(menuImg, createGameImg, joinGameImg);
        root.getChildren().add(anchorPane);
    }
}
