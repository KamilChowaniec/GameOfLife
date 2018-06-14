public abstract class Grid {
    protected CellProperties[][] cellProperties;
    protected boolean[][] alive;
    protected boolean[][] buffer;
    private int zoom = 0;
    private double xoff = 0, yoff = 0;
    private float prevZoom = 0;
    private double diffX = 0;
    private double diffY = 0;
    private int highlightedI = 0;
    private int highlightedJ = 0;
    private float oldX = 0;
    private float oldY = 0;


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

    public void drawOnGrid(int x, int y, boolean state) {
        alive[x][y] = state;
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

    public void incZoom(int offset)
    {
        zoom += offset;
        if(zoom>150) zoom+=10*offset;
        else if(zoom>125) zoom+=7*offset;
        else if (zoom > 100) zoom+=5*offset;
        else if(zoom>75) zoom+=2*offset;
        else if(zoom>50) zoom+=offset;
        if (zoom > 200) zoom = 200;
        else if (zoom < 0) zoom = 0;
    }

    public int getZoom() {
        return zoom;
    }

    public void moveGrid(double x, double y) {
        xoff += x;
        yoff += y;
    }

    public double getXoff() {
        return xoff;
    }

    public double getYoff() {
        return yoff;
    }

    public void setXoff(double xoff) {
        this.xoff = xoff;
    }

    public void setYoff(double yoff) {
        this.yoff = yoff;
    }

    public float getPrevZoom() {
        return prevZoom;
    }

    public float getHighlightedI() {
        return highlightedI;
    }

    public float getHighlightedJ() {
        return highlightedJ;
    }

    public float getOldX() {
        return oldX;
    }

    public float getOldY() {
        return oldY;
    }

    public double getDiffX() {
        return diffX;
    }

    public double getDiffY() {
        return diffY;
    }

    public void setOldX(float oldX) {
        this.oldX = oldX;
    }

    public void setOldY(float oldY) {
        this.oldY = oldY;
    }

    public void setHighlightedJ(int highlightedJ) {
        this.highlightedJ = highlightedJ;
    }

    public void setHighlightedI(int highlightedI) {
        this.highlightedI = highlightedI;
    }

    public void setPrevZoom(float prevZoom) {
        this.prevZoom = prevZoom;
    }

    public void setDiffX(double diffX) {
        this.diffX = diffX;
    }

    public void setDiffY(double diffY) {
        this.diffY = diffY;
    }
}

