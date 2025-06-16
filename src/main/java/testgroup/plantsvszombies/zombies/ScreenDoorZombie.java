package testgroup.plantsvszombies.zombies;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import testgroup.plantsvszombies.Grid;

public class ScreenDoorZombie extends Zombie{

    public static final int HP = 10;
    public static final int SPEED = Grid.PIXELS_PER_BLOCK / 4;

    public ScreenDoorZombie(Grid grid, AnchorPane anchorPane, int row){
        super(grid, anchorPane, row, 1800, "/zombies/ScreenZombieWalk.gif", "/zombies/ScreenZombieAttack.gif", "/zombies/ZombieDie.gif", HP, 3);
    }

    public ScreenDoorZombie(Grid grid, AnchorPane anchorPane, int row, int startingX){
        super(grid, anchorPane, row, startingX, "/zombies/ScreenZombieWalk.gif", "/zombies/ScreenZombieAttack.gif", "/zombies/ZombieDie.gif", HP, 3);
    }


    public void loadZombie(AnchorPane anchorPane1) {
        grid.removeZombie(this);
        new ScreenDoorZombie(grid, anchorPane1, row, getX());
    }
}
