package testgroup.plantsvszombies;

import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoadingScreen {
    VBox loadingScreen;
    StackPane parentRoot;
    Label loadingText;
    ExecutorService executor;

    public LoadingScreen(StackPane root) {
        loadingScreen = createLoadingScreen();
        parentRoot = root;
    }

    private VBox createLoadingScreen() {
        loadingScreen = new VBox(20);
        loadingScreen.setAlignment(Pos.CENTER);
        loadingScreen.setPrefSize(400, 400);
        loadingScreen.setMaxSize(400, 400);
        ImageView gif = new ImageView("loading.gif");
        gif.setFitWidth(600);
        gif.setFitHeight(400);
        gif.setOpacity(0.95);
        loadingScreen.getChildren().add(gif);
        loadingScreen.setStyle("-fx-background-color: rgba(150, 75, 0, 0.0);");

        loadingText = new Label("loading, please wait...");
        loadingText.setStyle("-fx-font-size: 38px; -fx-text-fill: #A52A2A;");

        loadingScreen.getChildren().add(loadingText);
        return loadingScreen;
    }

    public void runTask(Task<?> task) {
        // todo: thread does not close if task doesn't complete(closed window)
        parentRoot.getChildren().add(loadingScreen);
        executor = Executors.newCachedThreadPool();
        executor.submit(task);

//        task.setOnSucceeded(event -> {
//            System.out.println("task done");
//            parentRoot.getChildren().remove(loadingScreen);
//            executor.close();
//        });
//        task.setOnCancelled(event -> {
//            System.out.println("task canceled");
//            parentRoot.getChildren().remove(loadingScreen);
//            executor.close();
//        });
//        task.setOnFailed(event -> {
//            System.out.println("task failed");
//            parentRoot.getChildren().remove(loadingScreen);
//            executor.close();
//        });
    }
    public void done() {
        parentRoot.getChildren().remove(loadingScreen);
        executor.close();
    }
}
