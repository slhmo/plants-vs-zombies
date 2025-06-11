package testgroup.plantsvszombies.zombies;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import testgroup.plantsvszombies.Grid;

public class SimpleZombie extends Zombie{
    private Timeline timeline;
    private StackPane stackPane;

    public SimpleZombie(Grid grid, StackPane stackPane){
        super(5, grid.PIXELS_PER_BLOCK / 4);

        image = new ImageView(getClass().getResource("/zombies/SimpleZombie.gif").toString());
    }
}
