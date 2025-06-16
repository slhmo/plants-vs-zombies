package testgroup.plantsvszombies.plants;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.Serializable;

public class Card implements Serializable {
    private static Card[] cards = new Card[18];

    static {
        cards[0] = new Card("/selector/card_sunflower.png", 0, 2, 50);
        cards[1] = new Card("/selector/card_peashooter.png", 1, 4, 100);
        cards[2] = new Card("/selector/card_repeaterPea.png", 2, 4, 200);
        cards[3] = new Card("/selector/card_snowPeaShooter.png", 3, 4, 175);
        cards[4] = new Card("/selector/card_wallNut.png", 4, 5, 50);
        cards[5] = new Card("/selector/card_bigWallNut.png", 5, 5, 125);
        cards[6] = new Card("/selector/card_cherryBomb.png", 6, 6, 150);
        cards[7] = new Card("/selector/card_jalapeno.png", 7, 6, 125);
    }

    private transient ImageView image;
    private int id;
    private transient Timeline coolDownTimeLine;
    private boolean coolDownFinished = true;
    private int price;

    protected Card(String imageUrl, int id, int coolDown, int price) {
        image = new ImageView(getClass().getResource(imageUrl).toString());
        this.id = id;
        coolDownTimeLine = new Timeline(new KeyFrame(Duration.seconds(coolDown), event -> {
            image.setEffect(null);
            coolDownFinished = true;
        }));
        coolDownTimeLine.setCycleCount(1);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void place() {
        coolDownFinished = false;
        ColorAdjust coolDownEffect = new ColorAdjust();
        coolDownEffect.setBrightness(0.5);
        image.setEffect(coolDownEffect);
        coolDownTimeLine.play();
    }

    public boolean isCoolDownFinished() {
        return coolDownFinished;
    }

    public ImageView getImageView() {
        return image;
    }

    public int getId() {
        return id;
    }

    public static Card[] getCards() {
        return cards;
    }

    @Override
    public String toString() {
        return "card: " + id;
    }

    public static void stop() {
        for (Card card : cards) {
            if (card != null)
                card.coolDownTimeLine.pause();
        }
    }

    public static void resume() {
        for (Card card : cards) {
            if (card != null)
                card.coolDownTimeLine.play();
        }
    }

    public static void revert() {
        cards[0] = new Card("/selector/card_sunflower.png", 0, 2, 50);
        cards[1] = new Card("/selector/card_peashooter.png", 1, 4, 100);
        cards[2] = new Card("/selector/card_repeaterPea.png", 2, 4, 200);
        cards[3] = new Card("/selector/card_snowPeaShooter.png", 3, 4, 175);
        cards[4] = new Card("/selector/card_wallNut.png", 4, 5, 50);
        cards[5] = new Card("/selector/card_bigWallNut.png", 5, 5, 125);
        cards[6] = new Card("/selector/card_cherryBomb.png", 6, 6, 150);
        cards[7] = new Card("/selector/card_jalapeno.png", 7, 6, 125);
    }
}
