public class Triangular extends Grid {
    public Triangular() {
        super();

        //alive[20][20] = true;
        alive[10][10] = true;
        alive[20][20] = true;



        int x=10;
        int y=10;

        int starti = x - 1, startj = y - 2, imax = x + 1, jmax = y + 2;

        for (int i = starti; i <= imax; i++)
            for (int j = startj; j <= jmax; j++)
                alive[i][j] = true;

        /*alive[5][0] = true;
        alive[5][1] = true;
        alive[5][2] = true;
        alive[5][3] = true;
        alive[5][4] = true;

        alive[0][5] = true;
        alive[1][5] = true;
        alive[2][5] = true;
        alive[3][5] = true;
        alive[4][5] = true;*/
    }

    @Override
    public int countNeighbors(int x, int y) {
        int neighbors = 0;
        int starti = x - 1, startj = y - 2, imax = x + 1, jmax = y + 2;
        int miss = x % 2 == 1 ? y - 2 : y + 2;

        if (x < 2) starti = 0;
        else if (x > Game.GRIDSIZE - 3) imax = Game.GRIDSIZE - 1;
        if (y < 2) startj = 0;
        else if (y > Game.GRIDSIZE - 3) jmax = Game.GRIDSIZE - 1;

        for (int i = starti; i <= imax; i++)
            for (int j = startj; j <= jmax; j++)
                if ((i != miss || (j != y - 2 && j != y + 2)) && (i != x || j != y))
                    neighbors += alive[i][j] ? 1 : 0;

        return neighbors;
    }

    @Override
    public void drawOnGrind() {

    }
}