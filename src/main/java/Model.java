import java.util.ArrayList;

public class Model {
    private ArrayList<Card> cards;
    private int cardIndex;

    public Model() {
        cards = new ArrayList<>();
        cards.add(new Card(gridType.Squared));
        cardIndex = 0;
    }

    public void update() {
        cards.get(cardIndex).mechanic();
    }

    public Grid getGridValues() {
        return cards.get(cardIndex).getGrid();
    }

    public void addCard(gridType type) {
        cards.add(new Card(type));
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

    public void draw(int codedPosition) {
        int y = codedPosition % Game.GRIDSIZE;
        cards.get(cardIndex).draw((codedPosition - y) / Game.GRIDSIZE, y);
    }

    public void pause() {
        cards.get(cardIndex).switchPause();
    }

    public void incZoom(int offset, double[] mousePosition) {
        cards.get(cardIndex).incZoom(offset, mousePosition);
    }

    public void moveGrid(double x, double y) {
        cards.get(cardIndex).moveGrid(x, y);
    }

    public int getCardsAmount() {
        return cards.size();
    }

    public void setRule(int alive, int number, boolean state) {
        cards.get(cardIndex).setRule(alive, number, state);
    }

    public boolean getRule(int alive, int number) {
        return cards.get(cardIndex).getRule(alive, number);
    }

    public int getRuleSize() {
        return cards.get(cardIndex).getRulesSize();
    }


}