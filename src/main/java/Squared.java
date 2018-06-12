import graphics.Input.MouseHandler;
import graphics.Rectangle;
import graphics.Text;

import static org.lwjgl.opengl.GL11.glColor3f;

public class Squared extends Grid {
    private float squaredPrevZoom = 2;
    private double sqX = 0;
    private double sqY = 0;
    private int isq = 0;
    private int jsq = 0;
    private float oldX = 0;
    private float oldY = 0;
    private int si = 0;
    private int sj = 0;

    public Squared() {
        super();
        //randomize();
        alive[10][3] = true;

    }

    @Override
    public int countNeighbors(int x, int y) {
        int neighbors = 0;
        int starti = x - 1, startj = y - 1, imax = x + 1, jmax = y + 1;

        if (x == 0) starti = x;
        else if (x == Game.GRIDSIZE - 1) imax = x;
        if (y == 0) startj = y;
        else if (y == Game.GRIDSIZE - 1) jmax = y;

        for (int i = starti; i <= imax; i++)
            for (int j = startj; j <= jmax; j++)
                neighbors += alive[i][j] ? 1 : 0;
        return neighbors - (alive[x][y] ? 1 : 0);
    }

    @Override
    public int display(int gridX, int gridY, int gridWidth, int gridHeight) {
        double mouseX = MouseHandler.xPos();
        double mouseY = MouseHandler.yPos();
        double xoff = this.getXoff();
        double yoff = this.getYoff();

        int codedPosition = -1;
        glColor3f(0.8f, 0.8f, 0.8f);
        float size = 10;


        int columns = (int) (gridWidth / size)+1;
        int rows = (int) (gridHeight / size)+1 ;


        if(xoff>0) xoff=0;
        if(yoff>0) yoff=0;
        double starti = ((-xoff) / size);
        double startj = ((-yoff) / size);
        if (starti < 0) starti = 0;
        if (startj < 0) startj = 0;
        if (starti + columns >= Game.GRIDSIZE) {
            starti = Game.GRIDSIZE - columns;
            xoff = -starti * size;
        }
        if (startj + rows >= Game.GRIDSIZE) {
            startj = Game.GRIDSIZE - rows;
            yoff = -startj * size;
        }

        float x = gridX + (float) (xoff);
        float y = gridY + (float) (yoff);


        for (int i = (int) starti; i < columns + starti; i++)
            for (int j = (int) startj; j < rows + startj; j++)
                Rectangle.display(x + i * size, y + j * size, size, size, this.isCellAlive(i, j));

        //i
        // M = x + i * size
        // i = (M-x)/size

        //j
        // M = y + j * size
        // j = (M-x)/size


        if (mouseX > gridX && mouseX < (gridX + gridWidth)) {
            if (mouseY > gridY && mouseY < (gridY + gridHeight)) {
                int i = (int) ((mouseX - x) / size);
                int j = (int) ((mouseY - y) / size);
                glColor3f(0, 1, 0);
                Rectangle.display(x + i * size, y + j * size, size, size, false);
                codedPosition = Game.GRIDSIZE * i + j;

                isq = i;
                jsq = j;
                oldX = x + i * size;
                oldY = y + j * size;
                Text t=new Text(100,100,"adsafds",1,1,1);
                t.setTxt(i + " " + j);
                t.display();
            }
        }



        return codedPosition;
    }
}