import java.util.ArrayList;

public class Model {
    private ArrayList<Card> cards;
    private int cardIndex;
    private boolean[][] clipboard;

    public Model() {
        clipboard = new boolean[1][1];
        cards = new ArrayList<>();
        cards.add(new Card(gridType.Triangular));
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

    public void setClipboard(boolean[][] clipboard) {
        this.clipboard = clipboard;
    }

    public boolean[][] getClipboard() {
        return clipboard;
    }

    public void pasteClipboard(int x, int y) {
        if (x + clipboard.length > Game.GRIDSIZE)
            x = Game.GRIDSIZE - clipboard.length;
        if (y + clipboard[0].length > Game.GRIDSIZE)
            y = Game.GRIDSIZE - clipboard.length;
        for (int i = 0; i < clipboard.length; i++)
            for (int j = 0; j < clipboard[0].length; i++)
                if (clipboard[i][j]) cards.get(cardIndex).draw(i, j, true);
    }

    public void draw(int codedPosition, boolean state) {
        int y = codedPosition % Game.GRIDSIZE;
        cards.get(cardIndex).draw((codedPosition - y) / Game.GRIDSIZE, y, state);
    }

    public void setDelay(double delay) {
        cards.get(cardIndex).setDelay(delay);
    }

    public double getDelay() {
        return cards.get(cardIndex).getDelay();
    }

    public void pause() {
        cards.get(cardIndex).switchPause();
    }

    public void incZoom(int offset) {
        cards.get(cardIndex).incZoom(offset);
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
