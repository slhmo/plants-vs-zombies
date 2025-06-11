package testgroup.plantsvszombies.zombies;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import testgroup.plantsvszombies.Grid;

public class ImpZombie extends Zombie {
    private Timeline timeline;
    private StackPane stackPane;

    public ImpZombie(Grid grid){
        super(3, grid.PIXELS_PER_BLOCK / 2);

        image = new ImageView(getClass().getResource("/zombies/bucketheadzombie.gif").toString());
    }
}
