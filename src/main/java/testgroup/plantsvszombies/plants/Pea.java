package testgroup.plantsvszombies.plants;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import testgroup.plantsvszombies.Grid;
import testgroup.plantsvszombies.zombies.Zombie;

public class Pea {
    private int x;
    private int row;
    private Plant rootPlant;
    protected ImageView image;
    private AnchorPane anchorPane;
    private Timeline timeline;
    private static final double speed = 2;
    Grid grid;
    private int originX;

    public Pea(Plant rootPlant, StackPane stackPane, Grid grid) {
        new Pea(rootPlant, stackPane, grid, "/plants/pea.png");
    }

    protected Pea(Plant rootPlant, StackPane stackPane, Grid grid, String imageUrl) {
        this.rootPlant = rootPlant;
        this.anchorPane = (AnchorPane) stackPane.getParent().getParent();
//        System.out.println(anchorPane.getChildren());
        this.grid = grid;

        x = 480 + rootPlant.column * 152 + 105;
        originX = x;
        row = rootPlant.row;
        image = new ImageView(getClass().getResource(imageUrl).toString());
        image.setX(x);
        image.setY(170 + row * 175 - 20);

        timeline = new Timeline(new KeyFrame(Duration.seconds(0.025), event -> {
            move();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        grid.placePea(this, row);
        anchorPane.getChildren().add(1, image);
        timeline.play();

    }

    private void move() {
        x += (int) (speed * 152 / 40);
        image.setX(x);
    }

    public int getX() {
        return x;
    }
    public void vanish() {
        anchorPane.getChildren().remove(image);
    }

    public void hit(Zombie zombie) {
        System.out.println(zombie);
        zombie.getNormalHit();
        vanish();
    }

    public int getOriginX() {
        return originX;
    }

    public void stop() {
        timeline.pause();
    }

    public void resume() {
        timeline.play();
    }
}
