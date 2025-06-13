package testgroup.plantsvszombies.plants;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import testgroup.plantsvszombies.Selector;

public class SunPoint {
    private ImageView image;
    private Timeline timeline;
    private StackPane pane;
    private Selector selector;
    private AnchorPane anchorPane;

    public SunPoint(StackPane pane, Selector selector) {
//        new SunPoint(pane, selector, 0, 0);
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

    public SunPoint(AnchorPane anchorPane, Selector selector, int x, int y) {
        this.anchorPane = anchorPane;
        this.selector = selector;

        createSunPoint(x, y);

        timeline = new Timeline(new KeyFrame(Duration.seconds(10)));
        timeline.setCycleCount(1);
        timeline.setOnFinished(event -> { // player didn't pick sun
            vanish();
        });
        timeline.play();
;
    }

    private void createSunPoint() {
        image = new ImageView(getClass().getResource("/items/sunPoint.gif").toString());
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

    private void createSunPoint(int x, int y) {
        image = new ImageView(getClass().getResource("/items/sunPoint.gif").toString());
        image.setX(x);
        image.setY(y);
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
        anchorPane.getChildren().add(image);
    }

    private void vanish() {
        if (pane != null)
            pane.getChildren().remove(image);
        else {
            anchorPane.getChildren().remove(image);
        }
    }
}
