package testgroup.plantsvszombies;

public interface Playable {
    public void stop();
    public void resume();
    public void gameLost();
    public void gameWon();
    public void createGame();
}
