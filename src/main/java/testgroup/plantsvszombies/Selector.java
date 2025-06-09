package testgroup.plantsvszombies;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import testgroup.plantsvszombies.plants.Plant;


public class Selector{
    AnchorPane root ;
    AnchorPane anchorPane;
    public int selectedInt = 0;
    private int sunBalance = 50;
    Label sunValueCounter;
    private Card selectedCard;

    public Selector (AnchorPane root) {
        this.root = root;
        anchorPane = createSelector();
        root.getChildren().add(anchorPane);
    }

    public int paySunPrice(Plant plant) {
        sunBalance -= plant.getValue();
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
        AnchorPane anchorPane1 = new AnchorPane();

        ImageView selectorImg = new ImageView("selector.png");
        selectorImg.setX(0);
        selectorImg.setY(0);
        selectorImg.setFitWidth(800);
        selectorImg.setFitHeight(100);
        anchorPane1.getChildren().add(selectorImg);

        sunValueCounter = new Label("" + sunBalance);
        sunValueCounter.setPrefSize(40, 18);
        sunValueCounter.setMaxSize(40, 18);
        sunValueCounter.setLayoutX(25);
        sunValueCounter.setLayoutY(62);
        sunValueCounter.setStyle("-fx-background-color: #FAD7A0; -fx-font-size: 12px; -fx-text-fill: #D2691E;");

        ImageView sunflowerCard = new ImageView("card_sunflower.png");
        Card sunflower = new Card(sunflowerCard, 1, anchorPane1);

        ImageView peaShooterCard = new ImageView("card_peashooter.png");
        Card peaShooter = new Card(peaShooterCard, 2, anchorPane1);

        ImageView repeaterCard = new ImageView("card_repeaterpea.png");
        Card repeater = new Card(repeaterCard, 3, anchorPane1);
        anchorPane1.getChildren().add(sunValueCounter);

        return anchorPane1;
    }

    private class Card {
        int n;
        private ImageView image;
        AnchorPane anchorPane;


        public Card(ImageView image, int n, AnchorPane anchorPane) {
            this.image = image;
            this.anchorPane = anchorPane;
            this.n = n;
            setCardPlace();
        }

        public void select() {
            Selector.this.deSelect();
            image.setY(25 + 5);
            Selector.this.selectedCard = this;
            Selector.this.selectedInt = n;
        }

        public void deSelect() {
            image.setY(25);
        }


        private void setCardPlace() {
            image.setFitWidth(40);
            image.setFitHeight(52);
            image.setX(40*n + 30 );
            image.setY(25);
            image.setOnMouseClicked(event -> {
                this.select();
            });
            anchorPane.getChildren().add(image);
        }
    }
    public void deSelect() {
        if (selectedCard != null)
            selectedCard.deSelect();
    }
}

