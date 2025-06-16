package testgroup.plantsvszombies;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import testgroup.plantsvszombies.zombies.Zombie;

import java.util.ArrayList;

public class LoadSaveGame {
    private Grid grid;
    private PlayGame game;


    public LoadSaveGame(Grid grid, PlayGame game) {
        this.game = game;
        this.grid = grid;
    }

    public void saveGame() {
    }


    public void loadGame(AnchorPane anchorPane, GridPane gridPane, Selector selector) {
        grid.loadGrid(game);
        for (int row = 0; row < grid.getZombies().length; row++) {
            for (int j = grid.getZombies()[row].size() - 1; j >= 0; j--) {
                grid.getZombies()[row].get(j).loadZombie(anchorPane);
            }
        }
        
        for (Node stackPane : gridPane.getChildren()) {
            int row = GridPane.getRowIndex(stackPane);
            int column = GridPane.getColumnIndex(stackPane);
            if (grid.getPlantsList()[row][column] != null)
                grid.getPlantsList()[row][column].loadPlant((StackPane) stackPane, selector);
        }

        for (int row=0; row<grid.getPeas().length; row++) {
            for (int j = grid.getPeas()[row].size()-1; j>=0; j--) {
                grid.getPeas()[row].get(j).loadPea(anchorPane);
            }
        }


    }

    private class ZombieSave {
        int HP;
        int type;
        int x;
        int y;
        public ZombieSave(int x, int y, int HP, int type) {
            this.x = x;
            this.y = y;
            this.HP = HP;
            this.type = type;
        }
    }

}
