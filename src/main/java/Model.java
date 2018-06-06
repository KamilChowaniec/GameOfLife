import java.util.ArrayList;

public class Model {
    private ArrayList<Card> cards;
    private int cardIndex;

    public Model() {
        cards = new ArrayList<>();
        cards.add(new Card(2));
        cardIndex = 0;
    }

    public void update() {
        cards.get(cardIndex).mechanic();
    }

    public Grid getGridValues() {
        return cards.get(cardIndex).getGrid();
    }

    public void addCard(int gridType) {
        cards.add(new Card(gridType));
        cardIndex = cards.size() - 1;
    }

    public void setCardIndex(int cardIndex) {
        this.cardIndex = cardIndex;
    }

    public void randomize() {
        cards.get(cardIndex).randomize();
    }

    public void nextCard() {
        if (cardIndex < cards.size() - 1) cardIndex++;
    }

    public void prevCard() {
        if (cardIndex > 0) cardIndex--;
    }
}