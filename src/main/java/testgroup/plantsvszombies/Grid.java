package testgroup.plantsvszombies;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import testgroup.plantsvszombies.plants.Card;
import testgroup.plantsvszombies.plants.Pea;
import testgroup.plantsvszombies.plants.Plant;
import testgroup.plantsvszombies.zombies.Zombie;
import testgroup.plantsvszombies.zombies.ZombieGenerator;

import java.io.Serializable;
import java.util.ArrayList;

public class Grid implements Serializable {
    private ArrayList<Zombie>[] zombies = new ArrayList[5]; //unsorted (because zombies have different speeds)
    private Plant[][] plants = new Plant[5][9]; // sorted by x
    private ArrayList<Pea>[] peas = new ArrayList[5]; // sorted by x
    Zombie[] firstZombieInRow = new Zombie[5];   // list of closest zombies to our plants in each row

    public final static int GRID_START_X = 480;    //todo
    public final static int GRID_END_X = 1840;

    public static final int PIXELS_PER_BLOCK = (GRID_END_X - GRID_START_X) / 9;

    private transient Timeline timeline;

    private transient Playable playable;
    ArrayList<Card> chosenCards;

    public Grid(Playable playable, AnchorPane anchorPane, ArrayList<Card> chosenCards) {
        for (int i = 0; i<5; i++) {
            zombies[i] = new ArrayList<>();
            peas[i] = new ArrayList<>();
        }

        this.playable = playable;
        this.chosenCards = chosenCards;

        timeline = new Timeline(new KeyFrame(Duration.seconds(0.0025), event -> {
            update();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Timeline trace = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            printEntities();
        }));
        trace.setCycleCount(Timeline.INDEFINITE);
//        trace.play();
    }

    public ArrayList<Card> getChosenCards() {
        return chosenCards;
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

        for (int row = 0; row < plants.length; row++) {
            for (int i = zombies[row].size()-1; i>=0; i--) {
                Zombie zombie = zombies[row].get(i);
                // zombies eat plants?
                for (int k = 0; k<plants[row].length; k++) {
                    Plant plant = plants[row][k];
                        if ((plant != null && zombie != null) && (!zombie.isEating()) && (plant.getX()+10 >= zombie.getX() && plant.getX()-40 <= zombie.getX())) {
                        zombie.eat(plant);
                    }
                }

                // peas hit zombies?
                for (int j = peas[row].size()-1; j>=0; j--) {
                    Pea pea = peas[row].get(j);
                    if ((pea != null && zombie != null) && (pea.getX() >= zombie.getX() - 5 && pea.getOriginX() <= zombie.getX() + 5)) {
                        pea.hit(zombie);
                        peas[row].remove(pea);
                    }
                }
            }
        }


        // zombies reach house?
        for (Zombie zombie : firstZombieInRow) {
            if (zombie == null)
                continue;
            if (zombie.getX() <= GRID_START_X) {
                playable.gameLost();
                return;
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
        for (int i = 0; i<zombies.length; i++) {
            for (int j = 0; j<zombies[i].size(); j++) {
                if (zombie == zombies[i].get(j))     // todo: == or equals?
                {
                    System.out.println(zombie + " " + zombies[i].get(j));
                    zombies[i].remove(j);
                }
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
            for (int i = row.size()-1; i>=0;i--) {
                if (pea == row.get(i))
                    row.remove(i);
            }
        }
    }

    public Plant[][] getPlantsList() {
        return plants;
    }

    public ArrayList<Zombie>[] getZombies() {
        return zombies;
    }

    public void printEntities() {
        System.out.println("##############zombies#############");
        for (ArrayList<Zombie> row : zombies) {
            for (Zombie zombie : row) {
                if (zombie != null)
                    System.out.print(zombie.getX() + " ");
            }
            System.out.println();
        }
        System.out.println("##############plants#############");
        for (Plant[] row : plants) {
            for (Plant plant : row) {
                if(plant != null)
                    System.out.print(plant.getX() + " ");
            }
            System.out.println();
        }
        System.out.println("##############peas#############");
        for (ArrayList<Pea> row : peas) {
            for (Pea pea : row) {
                if (pea != null)
                    System.out.print(pea.getX() + " ");
            }
            System.out.println();
        }
    }

    public void stopAll() {
        System.out.println("grid stop all");
        Card.stop();

        for (int i = 0; i<zombies.length; i++) {
            for (int j = zombies[i].size()-1; j>=0; j--) {
                if (zombies[i].get(j) != null) {
                    zombies[i].get(j).stop();
                }
            }
        }

        for (int i = 0; i<peas.length; i++) {
            for (int j = peas[i].size()-1; j>=0; j--) {
                if (peas[i].get(j) != null) {
                    peas[i].get(j).stop();
                }
            }
        }

        for (int i = 0; i<plants.length; i++) {
            for (int j = 0; j<plants[i].length; j++) {
                if (plants[i][j] != null) {
                    plants[i][j].stop();
                }
            }
        }

        playable.stop();
        timeline.pause();
    }

    public void resumeAll() {
        System.out.println("grid resume");
        timeline.play();
        Card.resume();
        for (int i = 0; i<zombies.length; i++) {
            for (int j = zombies[i].size()-1; j>=0; j--) {
                if (zombies[i].get(j) != null)
                    zombies[i].get(j).resume();
            }
        }

        for (int i = 0; i<peas.length; i++) {
            for (int j = peas[i].size()-1; j>=0; j--) {
                if (peas[i].get(j) != null)
                    peas[i].get(j).resume();
            }
        }

        for (int i = 0; i<plants.length; i++) {
            for (int j = 0; j<plants[i].length; j++) {
                if (plants[i][j] != null)
                    plants[i][j].resume();
            }
        }
        playable.resume();
    }

    public boolean noZombiesInMap() {
        boolean tmp = true;
        for (int row = 0; row<zombies.length; row++) {
            if(!zombies[row].isEmpty())
                tmp = false;
        }
        return tmp;
    }

    public ArrayList<Pea>[] getPeas() {
        return peas;
    }

    public void loadGrid(PlayGame game) {
        this.playable = game;
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.0025), event -> {
            update();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
