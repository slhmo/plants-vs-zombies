package testgroup.plantsvszombies.zombies;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import testgroup.plantsvszombies.Grid;

public class SimpleZombie extends Zombie{

    public static final int HP = 4;
    public static final int SPEED = Grid.PIXELS_PER_BLOCK / 9;

    public SimpleZombie(Grid grid, AnchorPane anchorPane, int row){
        super(grid, anchorPane, row, 1800, "/zombies/ZombieWalk.gif", "/zombies/ZombieAttack.gif", "/zombies/ZombieDie.gif", HP, 3);
    }

    public SimpleZombie(Grid grid, AnchorPane anchorPane, int row, int startingX){
        super(grid, anchorPane, row, startingX, "/zombies/ZombieWalk.gif", "/zombies/ZombieAttack.gif", "/zombies/ZombieDie.gif", HP, 3);
    }


    public void loadZombie(AnchorPane anchorPane1) {
        grid.removeZombie(this);
        new SimpleZombie(grid, anchorPane1, row, getX());
    }
}
