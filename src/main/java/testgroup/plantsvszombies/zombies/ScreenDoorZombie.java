package testgroup.plantsvszombies.zombies;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import testgroup.plantsvszombies.Grid;

public class ScreenDoorZombie extends Zombie{
    private Timeline timeline;
    private StackPane stackPane;

    public ScreenDoorZombie(Grid grid){
        super(10, grid.PIXELS_PER_BLOCK / 4);

        image = new ImageView(getClass().getResource("/zombies/bucketheadzombie.gif").toString());
    }
}
