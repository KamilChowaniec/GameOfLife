public abstract class Grid {
    protected CellProperties[][] cellProperties;
    protected boolean[][] alive;
    protected boolean[][] buffer;

    Grid() {
        cellProperties = new CellProperties[Game.GRIDSIZE][Game.GRIDSIZE];
    }

    protected abstract int countNeighbors(int x, int y);

    public abstract void drawOnGrind();

    public abstract void mechanic(int[][] rules);

    protected void swapBuffers() {
        boolean[][] tmp = alive;
        alive = buffer;
        buffer = tmp;
    }
}