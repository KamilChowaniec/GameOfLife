public class Hexagonal extends Grid {
    public Hexagonal() {
        super();
        alive[2][1] = true;
        alive[2][3] = true;
        alive[1][5] = true;
        alive[3][5] = true;
        alive[2][7] = true;
        alive[2][9] = true;
    }

    @Override
    public int countNeighbors(int x, int y) {
        int neighbors = 0;
        int starti = x - 1, startj = y - 1, imax = x + 1, jmax = y + 1;
        int miss = y % 2 == 1 ? x - 1 : x + 1;

        if (x == 0) starti = x;
        else if (x == Game.GRIDSIZE - 1) imax = x;
        if (y == 0) startj = x;
        else if (y == Game.GRIDSIZE - 1) jmax = x;

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