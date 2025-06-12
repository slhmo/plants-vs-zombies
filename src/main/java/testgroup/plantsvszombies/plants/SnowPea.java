package testgroup.plantsvszombies.plants;

import javafx.scene.layout.StackPane;
import testgroup.plantsvszombies.Grid;
import testgroup.plantsvszombies.zombies.Zombie;

public class SnowPea extends Pea{
    public SnowPea(Plant rootPlant, StackPane stackPane, Grid grid) {
        super(rootPlant, stackPane, grid, "/plants/SnowPea.png");
    }

    @Override
    public void hit(Zombie zombie) {
        zombie.getSnowHit();
        vanish();
    }
}
