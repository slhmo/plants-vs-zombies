package testgroup.plantsvszombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import testgroup.plantsvszombies.plants.Pea;
import testgroup.plantsvszombies.plants.Plant;
import testgroup.plantsvszombies.zombies.Zombie;

import java.util.ArrayList;

public class Grid {
    private ArrayList<Zombie>[] zombies = new ArrayList[5]; //unsorted (because zombies have different speeds)
    private Plant[][] plants = new Plant[5][9]; // sorted by x
    private ArrayList<Pea>[] peas = new ArrayList[5]; // sorted by x
    Zombie[] firstZombieInRow = new Zombie[5];   // list of closest zombies to our plants in each row

    final static int GRID_START_X = 480;    //todo
    final static int GRID_END_X = 1840;

    public static final int PIXELS_PER_BLOCK = (GRID_END_X - GRID_START_X) / 9;

    private Timeline timeline;

    public Grid() {
        for (int i = 0; i<5; i++) {
            zombies[i] = new ArrayList<>();
            peas[i] = new ArrayList<>();
        }

        timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), event -> {
            update();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    public void update() {
        /// this method will be called repeatedly

        for (int i = 0; i<5; i++) { // find first zombies
            ArrayList<Zombie> row = zombies[i];
            if (row.isEmpty()) {    // no zombies in this row
                firstZombieInRow[i] = null;
                continue;
            }

            Zombie firstInRow = row.getFirst();
            for (Zombie zombie : row) {
                if (zombie.getX() < firstInRow.getX())
                    firstInRow = zombie;
            }
            firstZombieInRow[i] = firstInRow;
        }

        // zombies eat plants?
        for (int row = 0; row < plants.length; row++) {

            Plant firstPlant = null;
            for (int i = plants[row].length - 1; i >= 0; i--) {  // find first plant in this row
                if (plants[row][i] != null)
                {
                    firstPlant = plants[row][i];
                    break;
                }
            }

            if ((firstPlant != null && firstZombieInRow[row] != null) && (firstPlant.getX() >= firstZombieInRow[row].getX())) {
                firstZombieInRow[row].eat(firstPlant);
            }
        }

        // peas hit zombies?
        for (int row = 0; row < plants.length; row++) {
            if ((!peas[row].isEmpty() && firstZombieInRow[row] != null) && (peas[row].getLast().getX() >= firstZombieInRow[row].getX())) {
                firstZombieInRow[row].hit();
                Pea tmp = peas[row].getLast();
                peas[row].remove(tmp);
                tmp.vanish();
            }
        }

        // zombies reach house?
        for (Zombie zombie : firstZombieInRow) {
            if (zombie == null)
                continue;
            if (zombie.getX() <= GRID_START_X) {
                // todo end game lost
            }
        }

        // peas exit grid?
        for (ArrayList<Pea> row : peas) {
            if (row.isEmpty())
                continue;
            if (row.getLast().getX() >= GRID_END_X) {   // todo
                row.getLast().vanish();
                row.removeLast();
            }
        }

    }

    public void placeZombie(Zombie zombie, int row) {
        zombies[row].add(zombie);
    }

    public void placePlant(Plant plant, int row, int column) {
        if (plants[row][column] == null)
            plants[row][column] = plant;
    }

    public void placePea(Pea pea, int row) {
        ArrayList<Pea> tmp = peas[row];
        int i;
        for (i = 0; i<tmp.size() && pea.getX() > tmp.get(i).getX(); i++) {}  // place pea before its next pea based on x
        tmp.add(i, pea);
    }

    public void removeZombie(Zombie zombie) {
        for (ArrayList<Zombie> row : zombies) {
            for (Zombie tmp : row) {
                if (zombie == tmp)     // todo: == or equals?
                    row.remove(tmp);
            }
        }
    }

    public void removePlant(Plant plant) {
        for (Plant[] row : plants) {
            for (int i = 0; i<row.length; i++) {
                if (plant == row[i])
                    row[i] = null;
            }
        }
    }

    public void removePea(Pea pea) {
        for (ArrayList<Pea> row : peas) {
            for (Pea tmp : row) {
                if (pea == tmp)
                    row.remove(tmp);
            }
        }
    }

    public Plant[][] getPlantsList() {
        return plants;
    }
}
