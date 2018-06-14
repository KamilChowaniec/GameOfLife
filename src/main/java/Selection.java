
public class Selection {
    private int x, y;
    private int width, height;

    public Selection(int codedPos) {
        y = codedPos % Game.GRIDSIZE;
        x = (codedPos - y) / Game.GRIDSIZE;
        width = 1;
        height = 1;
    }

    public Selection(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setWH(int codedPos2) {
        int endY = codedPos2 % Game.GRIDSIZE;
        int endX = (codedPos2 - endY) % Game.GRIDSIZE;
        int tmp;
        if (endX < x) {
            tmp = x;
            x = endX;
            endX = tmp;
        }
        if (endY < y) {
            tmp = y;
            y = endY;
            endY = y;
        }
        width = endY - y;
        height = endX - x;
    }

    public void setXY(int codedPos) {
        y = codedPos % Game.GRIDSIZE;
        x = (codedPos - y) / Game.GRIDSIZE;
    }

    public boolean[][] getClipboard(Grid grid) {
        trimExcess(grid);
        boolean[][] clip = new boolean[width][height];
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                clip[i][j] = grid.isCellAlive(i, j);
        return clip;
    }

    private void trimExcess(Grid grid) {
        boolean present = false;
        int x2 = x + width;
        int y2 = y + height;
        while (y < y2) {
            for (int i = x; i <= x2; i++) {
                if (grid.isCellAlive(i, y)) {
                    present = true;
                    break;
                }
            }
            if (!present) y += 1;
            else break;
        }
        present = false;
        while (y2 > y) {
            for (int i = x; i <= x2; i++) {
                if (grid.isCellAlive(i, y2)) {
                    present = true;
                    break;
                }
            }
            if (!present) y2 -= 1;
            else break;
        }

        present = false;
        while (x < x2) {
            for (int i = y; i <= y2; i++) {
                if (grid.isCellAlive(x, i)) {
                    present = true;
                    break;
                }
            }
            if (!present) x += 1;
            else break;
        }
        present = false;
        while (x2 > x) {
            for (int i = y; i <= y2; i++) {
                if (grid.isCellAlive(x2, i)) {
                    present = true;
                    break;
                }
            }
            if (!present) x2 -= 1;
            else break;
        }
        width = x2 - x;
        height = y2 - y;
    }



    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
