package testgroup.plantsvszombies.plants;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import testgroup.plantsvszombies.Grid;
import testgroup.plantsvszombies.Main;
import testgroup.plantsvszombies.Selector;
import testgroup.plantsvszombies.zombies.Zombie;

import java.util.ArrayList;

public class CherryBomb extends Plant {
    protected transient Timeline timeline;

    public CherryBomb(Grid grid, StackPane stackPane, int row, int column){
        super(grid, stackPane, row, column, "/plants/CherryBomb.gif", 4);

        timeline = new Timeline(new KeyFrame(Duration.seconds(1.9), event -> {
            Explosion(grid, row, column);
            vanish();
        }));
        timeline.setCycleCount(1);
        timeline.play();
    }

    public void Explosion(Grid grid, int row, int column){
        ArrayList<Zombie>[] zombies = grid.getZombies();
        int cellWidth = (Grid.GRID_END_X - Grid.GRID_START_X) / 9;
        int CenterX = (int) (( column + 0.5) * (cellWidth) + 430);
        int CenterY = row * 175 + 120;
        for (int i = 0; i < zombies.length; i++){
            for (int j = zombies[i].size() - 1; j >= 0; j--){
                int ZombieX = zombies[i].get(j).getX();
                int ZombieY = zombies[i].get(j).getRow() * 175 + 120;
                double distance = Math.sqrt(Math.pow(ZombieX - CenterX , 2) + Math.pow(ZombieY - CenterY , 2));
                if (distance < 1.5 * 160){
                    grid.getZombies()[i].get(j).burn();
                }
            }
        }
    }

    @Override
    public void stop() {
        timeline.pause();
    }

    @Override
    public void resume() {
        timeline.play();
    }

    public void loadPlant(StackPane stackPane, Selector selector) {
        grid.removePlant(this);
        new CherryBomb(grid, stackPane, row, column);
    }

}