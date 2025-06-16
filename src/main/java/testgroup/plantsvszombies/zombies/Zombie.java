package testgroup.plantsvszombies.zombies;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import testgroup.plantsvszombies.Grid;
import testgroup.plantsvszombies.plants.Plant;

import java.io.Serializable;


public abstract class Zombie implements Serializable {
    transient protected ImageView walkImg;
    transient protected ImageView eatImg;
    transient protected ImageView dieImg;
    transient protected ImageView burnImg;
    transient protected ImageView image;

    protected int current_HP;
    int speed;
    int row;
    private int x;
    protected transient AnchorPane anchorPane;
    transient Timeline moveTimeLine;
    transient Timeline eatTimeline;
    transient Timeline snowTimeline;

    protected Grid grid;

    private int counter;
    protected boolean snowy;

    protected Plant currentlyEating;

    public int getX() {
        return x;
    }

    public int getY() {
        return row*175 + 120;
    }

    public int getCurrent_HP() {
        return current_HP;
    }

    public int getRow() {
        return row;
    }

    public Zombie(Grid grid, AnchorPane anchorPane, int row, int startingX, String walkImageUrl, String eatImageUrl, String dieImgUrl, int HP, int speed){
        walkImg = new ImageView(getClass().getResource(walkImageUrl).toString());
        eatImg = new ImageView(getClass().getResource(eatImageUrl).toString());
        dieImg = new ImageView(getClass().getResource(dieImgUrl).toString());
        burnImg = new ImageView(getClass().getResource("/zombies/burntZombie.gif").toString());

        this.row = row;
        this.current_HP = HP;
        this.speed = speed;
        this.anchorPane = anchorPane;
        this.grid = grid;

        x = startingX;
        walkImg.setX(x);
        walkImg.setY(row * 175 + 120);
        walkImg.setFitWidth(120);
        walkImg.setFitHeight(140);
        eatImg.setY(row * 175 + 120);
        eatImg.setFitWidth(120);
        eatImg.setFitHeight(140);
        dieImg.setY(row * 175 + 120);
        dieImg.setFitWidth(120);
        dieImg.setFitHeight(140);
        burnImg.setX(x);
        burnImg.setY(row * 175 + 120);
        image = walkImg;
        anchorPane.getChildren().add(image);

        grid.placeZombie(this, row);

        moveTimeLine = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            move();
        }));
        moveTimeLine.setCycleCount(Timeline.INDEFINITE);
        moveTimeLine.play();

        snowTimeline = new Timeline(new KeyFrame(Duration.seconds(4), event -> {
            snowy = false;
            this.speed *= 2;
            image.setEffect(null);
        }));
        snowTimeline.setCycleCount(1);

    }

    public void getNormalHit() {
        this.current_HP -= 1;
        System.out.println(current_HP);
        if (current_HP == 0){
            die();
        }
    }

    public void getSnowHit() {
        if (!snowy) {
            snowy = true;
            speed /= 2;
            ColorAdjust snowEffect = new ColorAdjust();
            snowEffect.setHue(-0.5);
            image.setEffect(snowEffect);
            snowTimeline.play();
        }
        snowTimeline.playFromStart();
        getNormalHit();
    }

    public void eat(Plant plant) {
        switchImage(eatImg);
        currentlyEating = plant;
        moveTimeLine.stop();
        plant.getEaten(this);

        eatTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            if (counter % 4 == 0) {
                plant.HP -= 1;
                plant.getEaten(this);
            }
            counter++;
        }));
        eatTimeline.setCycleCount(-1);
        eatTimeline.play();
    }

    protected void move() {
        x -= speed;
        image.setX(x);
    }

    public void stopEating() {
        switchImage(walkImg);
        eatTimeline.stop();
        currentlyEating = null;
        moveTimeLine.play();

    }

    private void die() {
        grid.removeZombie(this);
        moveTimeLine.stop();
        switchImage(dieImg);
        if (currentlyEating != null) {
            currentlyEating.stopEating(this);
            eatTimeline.stop();
        }
        Timeline tmp = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            anchorPane.getChildren().remove(image);
        }));
        tmp.setCycleCount(1);
        tmp.play();
    }

    public void burn() {
        grid.removeZombie(this);
        moveTimeLine.stop();
        switchImage(burnImg);
        if (currentlyEating != null) {
            currentlyEating.stopEating(this);
            eatTimeline.stop();
        }
        Timeline tmp = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            anchorPane.getChildren().remove(image);
        }));
        tmp.setCycleCount(1);
        tmp.play();
    }

    public boolean isEating() {
        return  (currentlyEating != null);
    }

    private void switchImage(ImageView switchTo) {
        anchorPane.getChildren().remove(image);
        switchTo.setX(x);
        image = switchTo;
        anchorPane.getChildren().add(image);
    }

    public void stop() {
        if (isEating()) {
            eatTimeline.pause();
        }
        if (!isEating()) {
            moveTimeLine.pause();
        }
        if (snowy) {
            snowTimeline.pause();
        }
    }

    public void resume() {
        if (isEating()) {
            eatTimeline.play();
        }
        if (!isEating()) {
            moveTimeLine.play();
        }
        if (snowy) {
            snowTimeline.play();
        }
    }

    @Override
    public String toString() {
        return "zombie x: " + x + ", y: " + getY();
    }

    public ImageView getImage() {
        return image;
    }

    public void restoreImage(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
        anchorPane.getChildren().add(image);
    }

    public abstract void loadZombie(AnchorPane anchorPane1) ;
}
