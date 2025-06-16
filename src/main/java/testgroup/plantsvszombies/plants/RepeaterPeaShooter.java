package testgroup.plantsvszombies.plants;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import testgroup.plantsvszombies.Grid;
import testgroup.plantsvszombies.Selector;

public class RepeaterPeaShooter extends PeaShooter{

    public RepeaterPeaShooter(Grid grid, StackPane stackPane, int row, int column) {
        super(grid, stackPane, row, column, "/plants/repeater.gif", 4);
    }

    @Override
    protected void shoot() {
        if (!grid.getZombies()[row].isEmpty()){

            Timeline innerTimeLine = new Timeline(new KeyFrame(Duration.seconds(0.2), event -> {
                new Pea(this, stackPane, grid);
            }));
            innerTimeLine.setCycleCount(1);
            innerTimeLine.play();
            new Pea(this, stackPane, grid);
        }
    }

    @Override
    public void loadPlant(StackPane stackPane, Selector selector) {
        grid.removePlant(this);
        new RepeaterPeaShooter(grid, stackPane, row, column);
    }

}
