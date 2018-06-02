public class Squared extends Grid {

    public Squared() {
        super();
        randomize();
    }

    @Override
    public int countNeighbors(int x, int y) {
        int neighbors = 0;
        int i = x - 1, j = y - 1, imax = x + 1, jmax = y + 1;

        if (x == 0) i = x;
        else if (x == Game.GRIDSIZE - 1) imax = x;
        if (y == 0) j = x;
        else if (y == Game.GRIDSIZE - 1) jmax = x;


        for (; i <= imax; i++)
            for (; j <= jmax; j++)
                if (i != x || j != y)
                    neighbors += alive[i][j] ? 1 : 0;

        return neighbors;
    }

    @Override
    public void drawOnGrind() {

    }

    @Override
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
        swapBuffers2();
    }
}