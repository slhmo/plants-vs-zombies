package testgroup.plantsvszombies.plants;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import testgroup.plantsvszombies.Grid;
import testgroup.plantsvszombies.Selector;

public class PeaShooter extends Plant{
    static {
        value = 100;
    }

    public PeaShooter(Grid grid, StackPane stackPane, int row, int column, Selector selector) {
        super(grid, stackPane, row, column, selector);
        image = new ImageView(getClass().getResource("/plants/peashooter.gif").toString());
        plant(grid, stackPane, row, column, selector);
    }

    @Override
    public int getEaten() {
        return 0;
    }

    @Override
    public int getX() {
        return 0;
    }
}
