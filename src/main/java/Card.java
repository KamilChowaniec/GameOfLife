public class Card {

    private Grid grid;

    private boolean[][] rules;

    // gridType: 0-Squared, 1-Triangular, 2-Hexagonal
    public Card(int gridType) {
        switch(gridType){
            case 0:
                grid = new Squared();
                rules = new boolean[2][9];
                rules[0][0] = true;
                rules[0][1] = true;
                rules[0][4] = true;
                rules[0][5] = true;
                rules[1][3] = true;
                rules[0][4] = true;
                rules[1][6] = true;
                break;
            case 1:
                grid = new Triangular();
                rules = new boolean[2][13];
                rules[0][2] = true;
                rules[0][3] = true;
                rules[1][3] = true;
                break;
            case 2:
                grid = new Hexagonal();
                rules = new boolean[2][7];
                rules[0][2] = true;
                rules[0][3] = true;
                rules[1][3] = true;
                break;
            default:
                System.out.println("Unknown Grid type");
        }
    }

    public void handleEvents() {
        // TODO implement me
    }

    public void mechanic() {
        grid.mechanic(rules);
    }

    public Grid getGrid(){
        return grid;
    }
}