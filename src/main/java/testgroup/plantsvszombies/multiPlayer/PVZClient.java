package testgroup.plantsvszombies.multiPlayer;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class PVZClient extends Thread{
    private final int PORT = 8080;
    private Socket socket = null;
    BufferedReader in ;
    PrintWriter out;

    private StackPane root;
    private AnchorPane anchorPane;
    ClientGame clientGame;


    public PVZClient(StackPane root, AnchorPane anchorPane, String ip) {
        this.root = root;
        this.anchorPane = anchorPane;
        try {
            socket = new Socket(InetAddress.getByName(ip), PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGame() {
        clientGame = new ClientGame(root, this);
        clientGame.createGame();
    }


    public void run() {
        InputHandler inputHandler = new InputHandler();
        inputHandler.start();
    }

    private class InputHandler extends Thread {
        public void run() {
            while (true) {
                String input = null;
                try {
                    input = in.readLine();
                    if ("END".equals(input)) {
                        placeInputs(input);
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("client received message: " + input);
                placeInputs(input);
            }
        }
    }

    public void sendMessage(String s) {
        System.out.println("client sent message: " + s);
        out.println(s);
    }


    private synchronized void placeInputs(String s) {
        System.out.println(s);
        if ("END".equals(s)) {
            sendMessage("END");
            shutDown();
        }

        else if ("STOP GAME".equals(s)) {
            clientGame.getGrid().stopAll();
            Platform.runLater(() -> {
                Label tmp = new Label("game stopped");
                anchorPane.getChildren().add(tmp);
            });
        }

        else if ("RESUME GAME".equals(s)) {
            clientGame.getGrid().resumeAll();
        }

        else if ("WON".equals(s)) {
            Platform.runLater(() -> {
                clientGame.othersWon();
            });
        }

        else if ("START GAME".equals(s)) {
            clientGame.getGrid().resumeAll();
        }

        else if ("YOU WON".equals(s)) {
            Platform.runLater(() -> {
                clientGame.gameWon();
            });
        }

        else {
            Platform.runLater(() -> {
                clientGame.loadZombie(s);
            });
        }
    }

    private void shutDown() {
        System.out.println("shutting down");
        try {
            socket.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean connected() {
        return socket != null;
    }
}
