package testgroup.plantsvszombies.plants;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import testgroup.plantsvszombies.Grid;

public class RepeaterPeaShooter extends PeaShooter{
    public static final int PRICE = 200;

    public RepeaterPeaShooter(Grid grid, StackPane stackPane, int row, int column) {
        super(grid, stackPane, row, column, "/plants/repeater.gif");
    }

    @Override
    protected void shoot() {
        Timeline innerTimeLine = new Timeline(new KeyFrame(Duration.seconds(0.2), event -> {
            new Pea(this, stackPane, grid);
        }));
        innerTimeLine.setCycleCount(1);
        innerTimeLine.play();
        new Pea(this, stackPane, grid);
    }
}
