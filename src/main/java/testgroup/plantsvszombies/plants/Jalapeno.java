package testgroup.plantsvszombies.plants;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import testgroup.plantsvszombies.Grid;
import testgroup.plantsvszombies.zombies.Zombie;

public class Jalapeno extends Plant{
    protected Timeline timeline;
    protected ImageView explosionImg;

    public Jalapeno(Grid grid, StackPane stackPane, int row, int column){
        super(grid, stackPane, row, column, "/plants/jalapeno.gif", 4);

        AnchorPane anchorPane = (AnchorPane) stackPane.getParent().getParent();

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.4));
        timeline = new Timeline(new KeyFrame(Duration.seconds(1.9), event -> {
            Explosion(grid);
            vanish();
            explosionImg = new ImageView(getClass().getResource("/plants/JalapenoAttack.gif").toString());
            explosionImg.setY(getY());
            explosionImg.setX(480);
            explosionImg.setFitWidth(1360);
            explosionImg.setFitHeight(100);
            pauseTransition.play();
            anchorPane.getChildren().add(explosionImg);
            explosionImg.toFront();
        }));
        timeline.setCycleCount(1);
        timeline.play();
        pauseTransition.setOnFinished(event1 -> {
            anchorPane.getChildren().remove(explosionImg);
        });
    }

    public void Explosion(Grid grid){
        for (int i = grid.getZombies()[row].size()-1; i>=0; i--) {
            grid.getZombies()[row].get(i).burn();
        }
    }

    @Override
    public void vanish() {
        System.out.println("vanishing");
        timeline.stop();
        grid.getPlantsList()[row][column] = null;
        stackPane.getChildren().remove(image);
    }

    public void stop() {
        timeline.pause();
    }

    public void resume() {
        timeline.play();
    }
}
