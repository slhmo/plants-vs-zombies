package testgroup.plantsvszombies.plants;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import testgroup.plantsvszombies.Grid;
import testgroup.plantsvszombies.zombies.Zombie;

import java.io.Serializable;

public class Pea implements Serializable {
    private int x;
    private int row;
    private Plant rootPlant;
    protected transient ImageView image;
    private transient AnchorPane anchorPane;
    private transient Timeline timeline;
    private static final double speed = 2;
    Grid grid;
    private int originX;
    String imageUrl;

    public Pea(Plant rootPlant, StackPane stackPane, Grid grid) {
        new Pea(rootPlant, stackPane, grid, "/plants/pea.png");
    }

    protected Pea(Plant rootPlant, StackPane stackPane, Grid grid, String imageUrl) {
        this.rootPlant = rootPlant;
        this.anchorPane = (AnchorPane) stackPane.getParent().getParent();
//        System.out.println(anchorPane.getChildren());
        this.grid = grid;
        this.imageUrl = imageUrl;

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
        System.out.println("pea vanishing");
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

    public ImageView getImage() {
        return image;
    }

    public void loadPea(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;

        image = new ImageView(getClass().getResource(imageUrl).toString());
        image.setX(x);
        image.setY(170 + row * 175 - 20);

        timeline = new Timeline(new KeyFrame(Duration.seconds(0.025), event -> {
            move();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        anchorPane.getChildren().add(1, image);
        timeline.play();
    }
}
