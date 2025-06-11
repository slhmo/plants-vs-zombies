package testgroup.plantsvszombies.plants;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import testgroup.plantsvszombies.Grid;

public class PeaShooter extends Plant{
    public static final int PRICE = 100;
    protected Timeline timeline;

    public PeaShooter(Grid grid, StackPane stackPane, int row, int column) {
        new PeaShooter(grid, stackPane, row, column, "/plants/peaShooter.gif");
    }

    protected PeaShooter(Grid grid, StackPane stackPane, int row, int column, String imageUrl) {
        super(grid, stackPane, row, column, imageUrl);
        timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> shoot()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @Override
    public void vanish() {
        System.out.println("vanishing");
        timeline.stop();
        grid.getPlantsList()[row][column] = null;
        stackPane.getChildren().remove(image);
    }

    protected void shoot() {
        new Pea(this, stackPane, grid);
    }


    @Override
    public void plant(Grid grid, StackPane cell, int row, int column) {
        super.plant(grid, cell, row, column);
    }


    @Override
    public int getEaten() {
        return 0;
    }

    @Override
    public int getX() {
        return 0;
    }

    public void stop() {
        // todo
    }
}
