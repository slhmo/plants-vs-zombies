package testgroup.plantsvszombies.plants;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import testgroup.plantsvszombies.Grid;
import testgroup.plantsvszombies.zombies.Zombie;

public class WallNut extends Plant {
    public static final int PRICE = 50;

    public WallNut(Grid grid, StackPane stackPane, int row, int column) {
        super(grid, stackPane, row, column, "/plants/WallNut.gif", 10);
    }

    public void stop() {
    }

    public void resume() {

    }

    @Override
    public void getEaten(Zombie zombie) {
        if (!zombiesEating.contains(zombie))
            zombiesEating.add(zombie);
        if (HP == 7)
            switchImage(new ImageView(getClass().getResource("/plants/WallNut1.gif").toString()));

        if (HP == 4)
            switchImage(new ImageView(getClass().getResource("/plants/WallNut2.gif").toString()));

        if (HP == 0) {
            vanish();
            for (Zombie zombie1 : zombiesEating) {
                if (zombie1.getCurrent_HP() > 0) {
                    zombie1.stopEating();
                }
            }
        }
        ColorAdjust eatenEffect = new ColorAdjust();
        eatenEffect.setSaturation(1);
        eatenEffect.setBrightness(0.2);
        image.setEffect(eatenEffect);
    }

}