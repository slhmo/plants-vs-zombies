package testgroup.plantsvszombies.plants;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import testgroup.plantsvszombies.Grid;
import testgroup.plantsvszombies.Selector;

public abstract class Plant {
    ImageView image;
    protected static int value;

    public Plant (Grid grid, StackPane stackPane, int row, int column, Selector selector) {

    }

    public static int getValue() {
        return value;
    }

    public abstract int getEaten();
    public abstract int getX();

    public void plant(Grid grid, StackPane cell, int row, int column, Selector selector) {

        grid.placePlant(this, row, column);
        image.setFitHeight(100);
        image.setFitWidth(100);
        cell.getChildren().add(image);
        selector.paySunPrice(this);

        System.out.println("i got planted" + row + column + this);
    }
}
