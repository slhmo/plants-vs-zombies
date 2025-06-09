package testgroup.plantsvszombies;

import testgroup.plantsvszombies.plants.Plant;

public class Card {
    int n;
    static Card[] cards = new Card[18];

    private Plant getInstance() {

    }

    public void select() {

    }

    public Card(int n) {
        cards[n] = this;
    }
}
