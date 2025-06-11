package testgroup.plantsvszombies.plants;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import testgroup.plantsvszombies.Grid;

public class SnowPeaShooter extends PeaShooter{
    public static final int PRICE = 175;

    public SnowPeaShooter(Grid grid, StackPane stackPane, int row, int column) {
        super(grid, stackPane, row, column, "/selector/card_snowPeaShooter.png"); //todo fix gif
    }

    @Override
    protected void shoot() {
        new SnowPea(this, stackPane, grid);
    }}
