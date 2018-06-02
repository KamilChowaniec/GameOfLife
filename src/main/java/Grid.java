import graphics.Color;
public abstract class Grid {
    protected CellProperties[][] cellProperties;
    protected boolean[][] alive;
    protected boolean[][] buffer;

    Grid() {
        cellProperties = new CellProperties[Game.GRIDSIZE][Game.GRIDSIZE];
        for (int i = 0; i < Game.GRIDSIZE; i++) {
            for (int j = 0; j < Game.GRIDSIZE; j++) {
                cellProperties[i][j] = new CellProperties();
            }
        }
        alive = new boolean[Game.GRIDSIZE][Game.GRIDSIZE];
        buffer = new boolean[Game.GRIDSIZE][Game.GRIDSIZE];
    }

    protected abstract int countNeighbors(int x, int y);

    public abstract void drawOnGrind();

    public void mechanic(boolean[][] rules) {
        for (int i = 0; i < Game.GRIDSIZE; i++) {
            for (int j = 0; j < Game.GRIDSIZE; j++) {
                int a = countNeighbors(i, j);
                if (alive[i][j]) {
                    buffer[i][j] = rules[0][a];
                    cellProperties[i][j].incLived();
                } else {
                    buffer[i][j] = rules[1][a];
                    cellProperties[i][j].setLived(0);
                }
            }
        }
        swapBuffers();
    }

    protected void swapBuffers() {
        boolean[][] tmp = alive;
        alive = buffer;
        buffer = tmp;
    }

    public boolean isCellAlive(int x, int y) {
        return alive[x][y];
    }

    public CellProperties getCellProperties(int x, int y) {
        return cellProperties[x][y];
    }

    public void randomize() {
        for (int i = 0; i < Game.GRIDSIZE; i++)
            for (int j = 0; j < Game.GRIDSIZE; j++){
                alive[i][j] = Math.random()>0.5?true:false;
            }



    }

}