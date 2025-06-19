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
import java.util.ArrayList;

public class PVZServer extends Thread{
    boolean chose = false;
    private final int PORT = 8080;

    private final int MAX_CLIENTS = 5;
    private ArrayList<ServerWorker> workers = new ArrayList<>();
    private ServerSocket serverSocket;
    private boolean waitingForClients = true;
    private StackPane root;

    private AnchorPane anchorPane;
    ServerGame serverGame;

    public PVZServer(StackPane root, AnchorPane anchorPane) {
        this.root = root;
        this.anchorPane = anchorPane;
    }

    public void send(String message) {
        System.out.println("server sending message: " + message);
        for (ServerWorker worker : workers) {
            worker.sendMessage(message);
        }
        if ("END".equals(message))  shutDown();
    }

    public void startGame() {
        waitingForClients = false;
        try {
            serverSocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        serverGame = new ServerGame(root, this);
        serverGame.createGame();
    }

    public boolean allChose() {
        boolean result = chose;
        for (ServerWorker worker : workers) {
            if (!worker.chose) {
                result = false;
                break;
            }
        }
        return result;
    }

    public void run() {
        runServer();
    }

    public void runServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            int i = 0;
            try {
                lookForNewClient:
                while (i < MAX_CLIENTS && waitingForClients) {
                    Socket socket = serverSocket.accept();
                    System.out.println("socket accepted");
                    System.out.println(socket);
                    ServerWorker serverWorker = new ServerWorker(socket);
//                serverWorker.start();
                    workers.add(serverWorker);
                    i++;
                }
            } catch (SocketException e) {
                System.out.println("sockets closed successfully.");

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    private synchronized void placeInputs(ServerWorker worker, String s) {
        System.out.println("server received message: " + s);
        if ("END".equals(s)) {
            send("END");
            shutDown();
        }

        if ("STOP GAME".equals(s)) {
            serverGame.getGrid().stopAll();
            Platform.runLater(() -> {
                Label tmp = new Label("Player " + worker.id + " stopped game");
                anchorPane.getChildren().add(tmp);
            });
            for (ServerWorker worker1 : workers) {
                if (worker1 != worker) {
                    worker1.sendMessage("STOP GAME");
//                    worker1.sendMessage("Worker " + worker.id + " stopped game");
                }
            }
        }

        if ("RESUME GAME".equals(s)) {
            serverGame.getGrid().resumeAll();
            for (ServerWorker worker1 : workers) {
                if (worker1 != worker) {
                    worker1.sendMessage("RESUME GAME");
                }
            }
        }

        if ("EXIT GAME".equals(s)) {
            worker.closeSocket();
            workers.remove(worker);
            if (workers.isEmpty()) {    // everyone left
                Platform.runLater(() -> {
                    serverGame.gameWon();
                });
            }
        }

        if ("WON".equals(s)) {
            for (ServerWorker worker1 : workers) {
                if (worker1 != worker) {
                    worker1.sendMessage("WON");
//                    worker1.sendMessage("Worker " + worker.id + " stopped game");
                }
            }
            Platform.runLater(() -> {
                serverGame.othersWon(worker.id);
            });
        }

        if ("LOST".equals(s)) {
            workers.remove(worker);
            if (workers.isEmpty()) {
                Platform.runLater(() -> {
                    serverGame.gameWon();
                });
            }
        }
    }

    public ArrayList<ServerWorker> getWorkers() {
        return workers;
    }

    private void shutDown() {
        System.out.println("shutting down. closing sockets...");
        for (ServerWorker worker : workers) {
            worker.closeSocket();
            System.out.println("worker " + worker.id + "closed");
        }
        try {
            serverSocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ServerWorker extends Thread {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        static int id = 0;
        private boolean chose = false;

        public ServerWorker(Socket s) throws IOException {
            socket = s;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            id++;
            Platform.runLater(() -> {
                Label nameLb = new Label("player " + id);
                nameLb.setLayoutX(110);
                nameLb.setLayoutY(id * 90 + 250);
                nameLb.setStyle("-fx-font-size: 50px; -fx-text-fill: gray;");
                nameLb.setPrefSize(400, 100);
                anchorPane.getChildren().add(nameLb);
            });
            start();
        }

        public void closeSocket() {
            try {
                socket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            String s;
            try {
                while (!"END".equals(s = in.readLine())) {
                    if ("CHOSE".equals(s)) {
                        chose = true;
                    }
                    placeInputs(this, s);
                }
                placeInputs(this, s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void sendMessage(String s) {
            out.println(s);
        }
    }
}
