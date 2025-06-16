package testgroup.plantsvszombies.zombies;
import javafx.scene.layout.AnchorPane;
import testgroup.plantsvszombies.Grid;

public class BucketHeadZombie extends Zombie{

    public static final int HP = 10;

    public BucketHeadZombie(Grid grid, AnchorPane anchorPane, int row){
        super(grid, anchorPane, row, 1800, "/zombies/BucketZombieWalk.gif", "/zombies/BucketZombieAttack.gif", "/zombies/ZombieDie.gif", HP, 3);
    }

    public BucketHeadZombie(Grid grid, AnchorPane anchorPane, int row, int startingX){
        super(grid, anchorPane, row, startingX, "/zombies/BucketZombieWalk.gif", "/zombies/BucketZombieAttack.gif", "/zombies/ZombieDie.gif", HP, 3);
    }

    public void loadZombie(AnchorPane anchorPane1) {
        grid.removeZombie(this);
        new BucketHeadZombie(grid, anchorPane1, row, getX());
    }
}