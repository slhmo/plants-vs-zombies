package testgroup.plantsvszombies.plants;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import testgroup.plantsvszombies.Grid;
import testgroup.plantsvszombies.Selector;
import testgroup.plantsvszombies.zombies.Zombie;

public class WallNut extends Plant {

    public WallNut(Grid grid, StackPane stackPane, int row, int column) {
        super(grid, stackPane, row, column, "/plants/WallNut.gif", 10);
    }

    public void stop() {
    }

    public void resume() {

    }

    @Override
    public void loadPlant(StackPane stackPane, Selector selector) {
        grid.removePlant(this);
        new WallNut(grid, stackPane, row, column);
    }
}