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
        super(grid, anchorPane, row, "/zombies/ZombieWalk.gif", "/zombies/ZombieAttack.gif", "/zombies/ZombieDie.gif", HP, 3);
    }
}
