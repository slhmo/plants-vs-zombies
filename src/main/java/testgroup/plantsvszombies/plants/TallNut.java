package testgroup.plantsvszombies.plants;

import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import testgroup.plantsvszombies.Grid;
import testgroup.plantsvszombies.Selector;
import testgroup.plantsvszombies.zombies.Zombie;

public class TallNut extends Plant {

    public TallNut(Grid grid, StackPane stackPane, int row, int column) {
        super(grid, stackPane, row, column, "/plants/TallNut.png", 15);
    }

    public void stop() {
    }

    public void resume() {

    }


    @Override
    public void loadPlant(StackPane stackPane, Selector selector) {
        grid.removePlant(this);
        new TallNut(grid, stackPane, row, column);
    }

}