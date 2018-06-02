public class Hexagonal extends Grid {
    public Hexagonal() {
        super();
    }

    @Override
    public int countNeighbors(int x, int y) {
        int neighbors = 0;
        int starti = x - 1, startj = y - 1, imax = x + 1, jmax = y + 1;
        int miss = y % 2 == 1 ? y - 1 : y + 1;

        if (x == 0) starti = x;
        else if (x == Game.GRIDSIZE - 1) imax = x;
        if (y == 0) startj = y;
        else if (y == Game.GRIDSIZE - 1) jmax = y;

        for (int i = starti; i <= imax; i++)
            for (int j = startj; j <= jmax; j++)
                if ((j != miss || (i != x - 2 && i != x + 2)) && (i != x || j != y))
                    neighbors += alive[i][j] ? 1 : 0;

        return neighbors;
    }

    @Override
    public void drawOnGrind() {

    }
}