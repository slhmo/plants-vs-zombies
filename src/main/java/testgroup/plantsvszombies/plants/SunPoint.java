package testgroup.plantsvszombies.plants;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import testgroup.plantsvszombies.Selector;

public class SunPoint {
    private ImageView image;
    private Timeline timeline;
    private StackPane pane;
    private Selector selector;

    public SunPoint(StackPane pane, Selector selector) {
        this.pane = pane;
        this.selector = selector;

        createSunPoint();

        timeline = new Timeline(new KeyFrame(Duration.seconds(10)));
        timeline.setCycleCount(1);
        timeline.setOnFinished(event -> { // player didn't pick sun
            vanish();
        });
        timeline.play();
    }

    private void createSunPoint() {
        image = new ImageView(getClass().getResource("/items/sunPoint.png").toString());

        image.setOnMouseEntered(event -> {
            image.setOpacity(0.9);
        });

        image.setOnMouseExited(event -> {
            image.setOpacity(1);
        });

        image.setOnMouseClicked(event -> {  // player picked sun
            selector.increaseBalance();
            vanish();
        });
        pane.getChildren().add(image);
    }

    private void vanish() {
        pane.getChildren().remove(image);
    }
}
