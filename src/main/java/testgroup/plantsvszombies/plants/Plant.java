package testgroup.plantsvszombies.plants;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import testgroup.plantsvszombies.Grid;
import testgroup.plantsvszombies.Selector;
import testgroup.plantsvszombies.zombies.Zombie;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Plant implements Serializable {
    protected transient ImageView image;
    protected Grid grid;
    protected int row;
    protected int column;
    protected transient StackPane stackPane;
    public int HP;
    protected ArrayList<Zombie> zombiesEating = new ArrayList<>();

    public Plant(Grid grid, StackPane stackPane, int row, int column, String imageUrl, int HP) {
        this.grid = grid;
        this.row = row;
        this.column = column;
        this.stackPane = stackPane;
        image = new ImageView(getClass().getResource(imageUrl).toString());
        this.HP = HP;
        plant(grid, stackPane, row, column);
    }

    protected Plant() {}

    public int getX() {
        return 480 + column * 152 + 105;
    }

    public int getY() {
        return 100 + row * 175 + 75;
    }

    public void plant(Grid grid, StackPane cell, int row, int column) {
        grid.placePlant(this, row, column);
        image.setFitHeight(100);
        image.setFitWidth(100);
        cell.getChildren().add(image);
    }

    public void getEaten(Zombie zombie) {
        if (!zombiesEating.contains(zombie))
            zombiesEating.add(zombie);
        if (HP == 0) {
            vanish();
            for (Zombie zombie1 : zombiesEating) {
                if (zombie1.getCurrent_HP() > 0)
                    zombie1.stopEating();
            }
        }
        ColorAdjust eatenEffect = new ColorAdjust();
        eatenEffect.setSaturation(1);
        eatenEffect.setBrightness(0.2);
        image.setEffect(eatenEffect);
    }

    public void stopEating(Zombie zombie) {
        zombiesEating.remove(zombie);
        if (zombiesEating.isEmpty()) {
            image.setEffect(null);
        }
    }

    public void vanish() {
        stop();
        grid.getPlantsList()[row][column] = null;
        stackPane.getChildren().remove(image);
    }

    public abstract void stop();

    public abstract void resume();

    protected void switchImage(ImageView switchTo) {
        stackPane.getChildren().remove(image);
        image = switchTo;
        stackPane.getChildren().add(image);
    }

    public ImageView getImage() {
        return image;
    }

    public void restoreImg(StackPane stackPane) {
        this.stackPane =stackPane;
        stackPane.getChildren().add(image);
    }

    public abstract void loadPlant(StackPane stackPane, Selector selector);
}
