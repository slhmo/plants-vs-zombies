package testgroup.plantsvszombies;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class Selector{
    AnchorPane root ;
    AnchorPane anchorPane;
    public int selectedInt = 0;
    private int sunBalance = 250;
    Label sunValueCounter;
    private Card selectedCard;

    public Selector (AnchorPane root) {
        this.root = root;
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
        AnchorPane anchorPane1 = new AnchorPane();
        ImageView selectorImg = new ImageView(getClass().getResource("/selector/selector.png").toString());
        selectorImg.setX(0);
        selectorImg.setY(0);
        selectorImg.setFitWidth(830);
        selectorImg.setFitHeight(100);
        anchorPane1.getChildren().add(selectorImg);

        sunValueCounter = new Label("" + sunBalance);
        sunValueCounter.setPrefSize(40, 18);
        sunValueCounter.setMaxSize(40, 18);
        sunValueCounter.setLayoutX(25);
        sunValueCounter.setLayoutY(62);
        sunValueCounter.setStyle("-fx-background-color: #FAD7A0; -fx-font-size: 12px; -fx-text-fill: #D2691E;");

        Card sunflowerCard = new Card("/selector/card_sunflower.png", 1, anchorPane1);

        Card peaShooterCard = new Card("/selector/card_peashooter.png", 2, anchorPane1);

        Card repeaterCard = new Card("/selector/card_repeaterPea.png", 3, anchorPane1);

        Card snowPeaShooter = new Card("/selector/card_snowPeaShooter.png", 4, anchorPane1);

        Card wallNut = new Card("/selector/card_wallNut.png", 5, anchorPane1);

        Card tallWallNut = new Card("/selector/card_bigWallNut.png", 6, anchorPane1);

        Card cherryBomb = new Card("/selector/card_cherryBomb.png", 7, anchorPane1);

        Card jalapeno = new Card("/selector/card_jalapeno.png", 8, anchorPane1);

        // todo add plants
        anchorPane1.getChildren().add(sunValueCounter);


        Card shovelCard = new Card("/items/Shovel.png", 19, anchorPane1) {
          @Override
          public void select() {
              Selector.this.deSelect();
              image.setOpacity(0.8);
              Selector.this.selectedCard = this;
              Selector.this.selectedInt = 19;
          }

          @Override
          public void deSelect() {
              image.setOpacity(1);
          }

          @Override
          protected void setCardPlace() {
              image.setFitWidth(60);
              image.setFitHeight(60);
              image.setX(830);
              image.setY(20);
              image.setOnMouseClicked(event -> {
                  if (Selector.this.selectedInt == 19)
                      Selector.this.deSelect();
                  else {
                      this.select();
                  }
              });
              anchorPane.getChildren().add(image);
          }
        };

        return anchorPane1;
    }

    public void deSelect() {
        if (selectedCard != null)
            selectedCard.deSelect();
        selectedInt = 0;
    }

    private class Card {
        int n;
        protected ImageView image;
        AnchorPane anchorPane;


        public Card(String imageUrl, int n, AnchorPane anchorPane) {
            this.image = new ImageView((getClass().getResource(imageUrl).toString()));
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


        protected void setCardPlace() {
            image.setFitWidth(40);
            image.setFitHeight(52);
            image.setX(40*n + 30 );
            image.setY(25);
            image.setOnMouseClicked(event -> {
                if (Selector.this.selectedInt == n)
                    Selector.this.deSelect();
                else {
                    this.select();
                }
            });
            anchorPane.getChildren().add(image);
        }
    }

}

