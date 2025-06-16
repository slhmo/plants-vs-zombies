package testgroup.plantsvszombies.plants;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import testgroup.plantsvszombies.Grid;
import testgroup.plantsvszombies.Selector;

public class SnowPeaShooter extends PeaShooter{

    public SnowPeaShooter(Grid grid, StackPane stackPane, int row, int column) {
        super(grid, stackPane, row, column, "/plants/SnowPeaShooter.gif", 4); //todo fix gif
    }

    @Override
    protected void shoot() {
        if (!grid.getZombies()[row].isEmpty())
            new SnowPea(this, stackPane, grid);
    }

    @Override
    public void loadPlant(StackPane stackPane, Selector selector) {
        grid.removePlant(this);
        new SnowPeaShooter(grid, stackPane, row, column);
    }

}
