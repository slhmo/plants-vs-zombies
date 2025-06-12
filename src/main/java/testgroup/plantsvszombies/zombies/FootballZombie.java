package testgroup.plantsvszombies.zombies;
import javafx.scene.layout.AnchorPane;
import testgroup.plantsvszombies.Grid;

public class FootballZombie extends Zombie {

    public static final int HP = 15;
    public static final int SPEED = Grid.PIXELS_PER_BLOCK / 2;

    public FootballZombie(Grid grid, AnchorPane anchorPane, int row){
        super(grid, anchorPane, row, "/zombies/FootballZombieWalk.gif", "/zombies/FootballZombieAttack.gif", "/zombies/FootballZombieDie.gif", HP, 6);
    }
}
