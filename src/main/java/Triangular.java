public class Triangular extends Grid {
    public Triangular() {
        super();
    }

    @Override
    public int countNeighbors(int x, int y) {
        int neighbors = 0;
        int a;
        for (int i = x - 2; i <= x + 2; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                try {
                    a = alive[i][j] ? 1 : 0;
                } catch (ArrayIndexOutOfBoundsException e) {
                    a = 0;
                }

                neighbors += a;
            }
        }

        if ((x + y) % 2 == 0) {
            try {
                a = alive[x + 2][y - 1] ? 1 : 0;
            } catch (ArrayIndexOutOfBoundsException e) {
                a = 0;
            }
            neighbors -= a;
            try {
                a = alive[x - 2][y - 1] ? 1 : 0;
            } catch (ArrayIndexOutOfBoundsException e) {
                a = 0;
            }
            neighbors -= a;
        } else {
            try {
                a = alive[x - 2][y + 1] ? 1 : 0;
            } catch (ArrayIndexOutOfBoundsException e) {
                a = 0;
            }
            neighbors -= a;
            try {
                a = alive[x + 2][y + 1] ? 1 : 0;
            } catch (ArrayIndexOutOfBoundsException e) {
                a = 0;
            }
            neighbors -= a;
        }
        return neighbors - (alive[x][y] ? 1 : 0);
    }

    @Override
    public void drawOnGrind() {

    }
}