package testgroup.plantsvszombies;

import java.util.ArrayList;

public class Grid {
    private ArrayList<Zombie>[] zombies = new ArrayList[5]; //unsorted (because zombies have different speeds)
    private Plant[][] plants = new Plant[5][9]; // sorted by x
    private ArrayList<Pea>[] peas = new ArrayList[5]; // sorted by x

    final int GRID_START_X = 200;    //todo
    final int GRID_END_X = 1200;

    {
        for (int i = 0; i<5; i++) {
            zombies[i] = new ArrayList<>();
            peas[i] = new ArrayList<>();
        }
    }


    public void update() {
        /// this method will be called repeatedly

        Zombie[] firstZombieInLine = new Zombie[5];   // list of closest zombies to our plants in each row
        for (int i = 0; i<5; i++) {
            ArrayList<Zombie> row = zombies[i];
            if (row.isEmpty()) {    // no zombies in this row
                firstZombieInLine[i] = null;
                continue;
            }

            Zombie firstInLine = row.getFirst();
            for (Zombie zombie : row) {
                if (zombie.getX() < firstInLine.getX())
                    firstInLine = zombie;
            }
            firstZombieInLine[i] = firstInLine;
        }

        // zombies eat plants?
        for (int row = 0; row < plants.length; row++) {

            Plant firstPlant = null;
            for (int i = 0; i < plants[row].length; i++) {  // find first plant in this row
                if (plants[row][i] != null)
                {
                    firstPlant = plants[row][i];
                    break;
                }
            }

            if ((firstPlant != null && firstZombieInLine[row] != null) && (firstPlant.getX() >= firstZombieInLine[row].getX())) {
                firstZombieInLine[row].eat(firstPlant);
            }
        }

        // peas hit zombies?
        for (int row = 0; row < plants.length; row++) {
            if ((!peas[row].isEmpty() && firstZombieInLine[row] != null) && (peas[row].getLast().getX() >= firstZombieInLine[row].getX())) {
                firstZombieInLine[row].hit();
                Pea tmp = peas[row].getLast();
                peas[row].remove(tmp);
                tmp.vanish();
            }
        }

        // zombies reach house?
        for (Zombie zombie : firstZombieInLine) {
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


    public static void main(String[] args) {
        ArrayList<Integer> j = new ArrayList<>();
        j.add(12);
        j.add(14);

        Integer x = 16;
        int i;
        for (i = 0; i<j.size() && x > j.get(i); i++) {
        }
        j.add(i, x);
        System.out.println(j);


    }
}
