package testgroup.plantsvszombies.zombies;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import testgroup.plantsvszombies.Grid;

public class ConeHeadZombie extends Zombie{

    public static final int HP = 7;
    public static final int SPEED = Grid.PIXELS_PER_BLOCK / 4;

    public ConeHeadZombie(Grid grid, AnchorPane anchorPane, int row){
        super(grid, anchorPane, row, "/zombies/ConeHeadZombie.gif", "/zombies/ConeHeadZombieAttack.gif", "/zombies/ZombieDie.gif", HP, 3);
    }
}

