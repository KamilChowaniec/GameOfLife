public class Card {

    private Grid grid;

    private boolean[][] rules;

    private boolean pause;

    // gridType: 0-Squared, 1-Triangular, 2-Hexagonal
    public Card(gridType type) {
        pause = false;
        switch (type) {
            case Squared:
                grid = new Squared();
                rules = new boolean[2][9];
                rules[0][2] = true;
                rules[0][3] = true;
                rules[1][3] = true;
                break;
            case Triangular:
                grid = new Triangular();
                rules = new boolean[2][13];
                rules[1][1] = true;
                break;
            case Hexagonal:
                grid = new Hexagonal();
                rules = new boolean[2][7];
                rules[0][3] = true;
                rules[0][4] = true;
                rules[1][2] = true;
                break;
            default:
                System.out.println("Unknown Grid type");
        }
    }

    public void handleEvents() {
        // TODO implement me
    }

    public void mechanic() {
        if (!pause) grid.mechanic(rules);
    }

    public Grid getGrid() {
        return grid;
    }

    public void randomize() {
        grid.randomize();
    }

    public void draw(int x, int y) {
        grid.drawOnGrid(x, y);
    }

    public void switchPause() {
        pause = !pause;
    }

    public void incZoom(int offset){
        grid.incZoom(offset);
    }

    public void moveGrid(double x, double y)
    {
        grid.moveGrid(x,y);
    }
}