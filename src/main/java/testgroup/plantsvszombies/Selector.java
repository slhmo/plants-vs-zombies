package testgroup.plantsvszombies;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import testgroup.plantsvszombies.plants.Card;

import java.util.ArrayList;

public class Selector{
    AnchorPane root ;
    AnchorPane anchorPane;
    private int sunBalance;
    Label sunValueCounter;
    private Card selectedCard;
    private ArrayList<Card> cards;

    public Selector (AnchorPane root, ArrayList<Card> cards, int initialBalance) {
        this.root = root;
        this.cards = cards;
        sunBalance = initialBalance;
        anchorPane = createSelector();
        root.getChildren().add(anchorPane);
    }

    public int paySunPrice(int price) {
        sunBalance -= price;
        sunValueCounter.setText("" + sunBalance);
        return sunBalance;
    }

    public int increaseBalance() {
        sunBalance += 25;
        sunValueCounter.setText("" + sunBalance);
        return sunBalance;
    }

    public int getBalance() {
        return sunBalance;
    }

    private AnchorPane createSelector() {
        anchorPane = new AnchorPane();
        ImageView selectorImg = new ImageView(getClass().getResource("/selector/selector.png").toString());
        selectorImg.setX(0);
        selectorImg.setY(0);
        selectorImg.setFitWidth(830);
        selectorImg.setFitHeight(100);
        anchorPane.getChildren().add(selectorImg);

        sunValueCounter = new Label("" + sunBalance);
        sunValueCounter.setPrefSize(40, 18);
        sunValueCounter.setMaxSize(40, 18);
        sunValueCounter.setLayoutX(25);
        sunValueCounter.setLayoutY(62);
        sunValueCounter.setStyle("-fx-background-color: #FAD7A0; -fx-font-size: 12px; -fx-text-fill: #D2691E;");
        anchorPane.getChildren().add(sunValueCounter);


        for (int i = 0; i<cards.size(); i++) {
            placeCard(cards.get(i), i);
        }

        Card shovelCard = new Card("/items/Shovel.png", 19, 0, 0) {};
        shovelCard.getImageView().setFitWidth(80);
        shovelCard.getImageView().setFitHeight(80);
        shovelCard.getImageView().setX(40*19 + 70);
        shovelCard.getImageView().setY(25);
        shovelCard.getImageView().setOnMouseClicked(event -> {
            if (selectedCard == shovelCard)
                deSelect();
            else {
                deSelect();
                select(shovelCard);
            }
        });
        anchorPane.getChildren().add(shovelCard.getImageView());

        return anchorPane;
    }

    private void placeCard(Card card, int n) {
        System.out.println("card: " + card + "placed: " + (40*n + 70));
        card.getImageView().setFitWidth(40);
        card.getImageView().setFitHeight(52);
        card.getImageView().setX(40*n + 70);
        card.getImageView().setY(25);
        card.getImageView().setOnMouseClicked(event -> {
            if (selectedCard == card)
                deSelect();
            else {
                deSelect();
                select(card);
            }
        });
        anchorPane.getChildren().add(card.getImageView());
    }

    public void deSelect() {
        if (selectedCard != null) {
            selectedCard.getImageView().setY(25);
        }
        selectedCard = null;
    }

    private void select(Card card) {
        card.getImageView().setY(25 + 5);
        selectedCard = card;
    }

    public int getSelectedId() {
        if (selectedCard == null)
            return -1;

        return selectedCard.getId();
    }

    public boolean selectedAvailable() {
        return (selectedCard.isCoolDownFinished() && sunBalance >= selectedCard.getPrice());
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

}
