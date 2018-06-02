public class Triangular extends Grid {
    public Triangular() {
        super();

        alive[20][20] = true;
    }

    @Override
    public int countNeighbors(int x, int y) {
        int neighbors = 0;
        int starti = x - 2, startj = y - 1, imax = x + 2, jmax = y + 1;
        int miss = y % 2 == 1 ? x - 1 : x + 1;

        if (x < 2) starti = 0;
        else if (x > Game.GRIDSIZE - 3) imax = Game.GRIDSIZE - 1;
        if (y == 0) startj = y;
        else if (y == Game.GRIDSIZE - 1) jmax = y;

        for (int i = starti; i <= imax; i++)
            for (int j = startj; j <= jmax; j++)
                if ((i != miss || (j != y - 1 && j != y + 1)) && (i != x || j != y))
                    neighbors += alive[i][j] ? 1 : 0;

        return neighbors;
    }

    @Override
    public void drawOnGrind() {

    }
}