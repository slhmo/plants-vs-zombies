package testgroup.plantsvszombies.zombies;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import testgroup.plantsvszombies.Grid;
import testgroup.plantsvszombies.plants.Plant;


public class Zombie {
    protected ImageView walkImg;
    protected ImageView eatImg;
    protected ImageView image;

    protected int current_HP;
    int speed;
    int row;
    private int x;
    protected AnchorPane anchorPane;

    protected Grid grid;

    public int getX() {
        return x;
    }

    public Zombie(Grid grid, AnchorPane anchorPane, String walkImageUrl, String eatImageUrl, int HP, int speed){
        walkImg = new ImageView(getClass().getResource(walkImageUrl).toString());
        eatImg = new ImageView(getClass().getResource(eatImageUrl).toString());
        image = walkImg;
        this.current_HP = HP;
        this.speed = speed;
        this.anchorPane = anchorPane;
        this.grid = grid;
    }

    public void getNormalHit() {
        this.current_HP -= 1;
        if (current_HP == 0){
            vanish();
        }
    }

    public void getSnowHit() {
        this.speed = (int) speed/2;
        // todo add freezing animation
        getNormalHit();
    }

    public void eat(Plant plant) {
        plant.getEaten(this);
        image = eatImg;
    }

    private void stopEating(){
        image = walkImg;
    }

    private void vanish() {
        grid.removeZombie(this);
        anchorPane.getChildren().remove(image);
    }

    public void burn(){
        image = new ImageView(getClass().getResource("/zombies/burntZombie.gif").toString());
        grid.removeZombie(this);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            anchorPane.getChildren().remove(image);
        }));
        timeline.setCycleCount(1);
        timeline.play();
    }


}
