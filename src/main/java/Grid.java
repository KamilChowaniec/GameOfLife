public abstract class Grid {
    protected CellProperties[][] cellProperties;
    protected boolean[][] alive;
    protected boolean[][] buffer;
    private int zoom=0;
    private double xoff=0, yoff=0;

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

    public void drawOnGrid(int x, int y) {
        alive[x][y] = !alive[x][y];
    }

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
            for (int j = 0; j < Game.GRIDSIZE; j++) {
                alive[i][j] = Math.random() > 0.5 ? true : false;
            }
    }

    public void incZoom(int offset, double[] mousePosition) {
        this.zoom += offset;
        if (zoom < 0) zoom = 0;
        else if (zoom > 100) zoom = 100;


    }

    public int getZoom() {
        return zoom;
    }

    public void moveGrid(double x, double y)
    {
            this.xoff +=x;
            if (this.xoff > 0) this.xoff = 0;
            this.yoff += y;
            if (this.yoff > 0) this.yoff = 0;
    }

    public double getXoff() {return xoff;}
    public double getYoff() {return yoff;}

    public void setXoff(double xoff) {
        this.xoff = xoff;
    }

    public void setYoff(double yoff) {
        this.yoff = yoff;
    }
}





