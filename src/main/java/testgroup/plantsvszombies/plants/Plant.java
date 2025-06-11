package testgroup.plantsvszombies.plants;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import testgroup.plantsvszombies.Grid;
import testgroup.plantsvszombies.zombies.Zombie;

public abstract class Plant {
    protected ImageView image;
    protected Grid grid;
    protected int row;
    protected int column;
    protected StackPane stackPane;
    public final static int PRICE = 0;
    public final static int HP;


    public Plant(Grid grid, StackPane stackPane, int row, int column, String imageUrl) {
        this.grid = grid;
        this.row = row;
        this.column = column;
        this.stackPane = stackPane;
        image = new ImageView(getClass().getResource(imageUrl).toString());
        plant(grid, stackPane, row, column);
    }

    protected Plant() {}

    public abstract int getEaten(Zombie zombie);
    public abstract int getX();

    public void plant(Grid grid, StackPane cell, int row, int column) {
        grid.placePlant(this, row, column);
        image.setFitHeight(100);
        image.setFitWidth(100);
        cell.getChildren().add(image);
        System.out.println("i got planted" + row + column + this);
    }

    public abstract void vanish();

    public abstract void stop();
}
