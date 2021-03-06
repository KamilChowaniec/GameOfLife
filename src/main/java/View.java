import graphics.*;
import graphics.Color;
import graphics.Input.MouseButtonsHandler;
import graphics.Input.MouseHandler;

import java.util.Vector;

import static org.lwjgl.opengl.GL11.*;

public class View
{
    private Window window;
    private Vector<Shape> shapes;
    private Vector<Checkbox> checkboxesS;
    public static int screenWidth=1920, screenHeight=1080;
    public static int toolsX=2, toolsY=2,toolsWidth=400, toolsHeight=1076;
    public static int gridX = 404, gridY = 34, gridWidth = 1114, gridHeight = 1044;
    public static int previewX = 1520, previewY = 2, previewWidth = 400, previewHeight = 400;
    public static int clipboardX=1520, clipboardY=540-135, clipboardWidth=400, clipboardHeight=537+135;
    private static Text[] info;


    private boolean selection=true;


    public View()
    {
        window = new Window(1920, 1080, "GOL", true);
        Text.load_font("sansation.ttf");
        info = new Text[9];
        info[0]=new Text(clipboardX+25, clipboardY+50, "Opis schowka:", 1,1,1);
        info[1]=new Text(clipboardX+25, clipboardY+100, "- Podglad dostepny powyzej", 1,1,1);
        info[2]=new Text(clipboardX+25, clipboardY+150, "- Shift + LPM = zaznaczanie", 1,1,1);
        info[3]=new Text(clipboardX+25, clipboardY+200, "- CTRL + c = kopiowanie", 1,1,1);
        info[4]=new Text(clipboardX+25, clipboardY+250, "- CTRL + v = wklejanie", 1,1,1);
        info[5]=new Text(clipboardX+25, clipboardY+400, "Jak grac:", 1,1,1);
        info[6]=new Text(clipboardX+25, clipboardY+450, "- LPM tworzy komorke", 1,1,1);
        info[7]=new Text(clipboardX+25, clipboardY+500, "- PPM usuwa komorke", 1,1,1);
        info[8]=new Text(clipboardX+25, clipboardY+550, "SCROLL - przybliz / oddal", 1,1,1);

        shapes = new Vector<Shape>();
        createLayout();
        checkboxesS = new Vector<Checkbox>();

    }


    private void createLayout() {
        shapes.add(new Rectangle(toolsX, toolsY, toolsWidth, toolsHeight));
        shapes.add(new Rectangle(404, 2, 150, 30));//card
        shapes.add(new Rectangle(556, 2, 150, 30));//card
        shapes.add(new Rectangle(708, 2, 150, 30));//card
        shapes.add(new Rectangle(860, 2, 150, 30));//card
        shapes.add(new Rectangle(1012, 2, 150, 30));//card
        shapes.add(new Rectangle(1164, 2, 150, 30));//card
        shapes.add(new Rectangle(1316, 2, 150, 30));//card
        shapes.add(new Rectangle(gridX, gridY, gridWidth, gridHeight));
        shapes.add(new Rectangle(previewX, previewY, previewWidth, previewHeight));
        shapes.add(new Rectangle(clipboardX, clipboardY, clipboardWidth, clipboardHeight));
    }

    private void displayLayout() {
        for (Shape s : shapes)
            window.display(s);
    }



    public void display() {

        displayLayout();
        for(Text t : info) t.display();
        window.update();
        window.clear();
    }

    public Area getState() {
        for (Area a : Area.values())
            if (a.isFocused((int) MouseHandler.xPos(), (int) MouseHandler.yPos())) return a;
        return Area.grid;
    }


    private void displayMask() {
        glColor3f(0, 0, 0);
        Rectangle.display(0, 0, gridX, screenHeight, true);
        Rectangle.display(gridX, 0, gridWidth, gridY, true);
        Rectangle.display(gridX + gridWidth, 0, screenWidth - gridX - gridWidth, screenHeight, true);
        Rectangle.display(gridX, gridY + gridHeight, gridWidth, screenHeight - gridY - gridHeight, true);
        glColor3f(1, 1, 1);
    }


    public int display(Grid grid, Selection s, Clipboard clipboard) {
        int codedPosition = displayGrid(grid, s, clipboard);
        displayMask();
        displayPreview(grid, clipboard);
        return codedPosition;
    }

    private void displayPreview(Grid grid, Clipboard clipboard) {
        {
            glColor3f(0.8f, 0.8f, 0.8f);
            int length = Math.min(clipboard.getWidth(), clipboard.getHeight());
            float a = previewWidth / length;
            float s = (float) Math.sqrt(3);


            if (grid instanceof Squared) {
                length = Math.max(clipboard.getWidth(), clipboard.getHeight());
                a = (float) previewWidth / length;
                float cellWidth = a;
                float cellHeight = a;
                for (int i = 0; i < clipboard.getWidth(); i++)
                    for (int j = 0; j < clipboard.getHeight(); j++)
                        if (clipboard.isCellAlive(i,j))
                            Rectangle.display(previewX + i * cellWidth, previewY + j * cellHeight, cellWidth, cellHeight, true);

                glColor3f(0.2f, 0.2f, 0.2f);

                for (int i = 0; i < length; i++)
                    for (int j = 0; j < length; j++)
                        Rectangle.display(previewX + i * cellWidth, previewY + j * cellHeight, cellWidth, cellHeight, false);

            } else if (grid instanceof Triangular) {
                length = Math.max(clipboard.getWidth(), (int) (clipboard.getHeight() * s));
                a = (float) 2 * previewWidth / length;
                float cellWidth = a / 2;
                float cellHeight = a * s / 2;
                for (int i = 0; i < clipboard.getWidth(); i++)
                    for (int j = 0; j < clipboard.getHeight(); j++)
                        if (clipboard.isCellAlive(i,j))
                            Triangle.display(previewX + i * cellWidth + (j % 2) * cellWidth + cellWidth + cellWidth / 4, previewY + j * cellHeight, a, (i % 2) > 0, true);
                //                    if(grid.isCellAlive((i + (j % 2)) % Game.GRIDSIZE, j))

                glColor3f(0.2f, 0.2f, 0.2f);
                for (int i = 0; i < length; i++)
                    for (int j = 0; j < length; j++)
                        Triangle.display(previewX + i * cellWidth + (j % 2) * cellWidth + cellWidth + cellWidth / 4, previewY + j * cellHeight, a, (i % 2) > 0, false);

            } else if (grid instanceof Hexagonal) {
                length = (int) Math.max(clipboard.getWidth() * 1.5f, clipboard.getHeight() * s);
                //int length = (clipboard.getWidth()*1.5f > clipboard.getHeight()*s ? clipboard.getWidth() : clipboard.getHeight());
                a = (float) previewWidth / length;
                float cellWidth = a * 1.5f;
                float cellHeight = a * s;

                for (int i = 0; i < clipboard.getWidth(); i++)
                    for (int j = 0; j < clipboard.getHeight(); j++)
                        if (clipboard.isCellAlive(i,j))
                            Hexagon.display(previewX + i * cellWidth + a / 2, previewY + j * cellHeight + (i % 2) * cellHeight / 2, a, true);

                glColor3f(0.2f, 0.2f, 0.2f);
                for (int i = 0; i < length; i++)
                    for (int j = 0; j < length; j++)
                        Hexagon.display(previewX + i * cellWidth + a / 2, previewY + j * cellHeight + (i % 2) * cellHeight / 2, a, false);

            }


            glColor3f(0, 0, 0);
            Rectangle.display(clipboardX, clipboardY, clipboardWidth, clipboardHeight, true);
            glColor3f(1, 1, 1);
        }
    }
    private int displayGrid (Grid grid, Selection sel, Clipboard clipboard)
    {
        int codedPosition = -1;
        int gridType;
        double mouseX = MouseHandler.xPos();
        double mouseY = MouseHandler.yPos();
        float a = grid.getZoom();
        float prevA = grid.getPrevZoom();
        float s = (float) Math.sqrt(3);
        double newX = 0, newY = 0;
        float cellWidth = 0, cellHeight = 0;

        Color lineColor = new Color(0.2f, 0.2f, 0.2f, 1);
        Color fillColor = new Color(0.8f, 0.8f, 0.8f, 1);

        if (grid instanceof Squared) {
            gridType = 0;
            a += 2;
            prevA += 2;
            cellWidth = a;
            cellHeight = a;
        } else if (grid instanceof Triangular) {
            gridType = 1;
            a += 4;
            prevA += 4;
            cellWidth = a / 2;
            cellHeight = a * s / 2;
        } else if (grid instanceof Hexagonal) {
            gridType = 2;
            a += 1;
            prevA += 1;
            cellWidth = a * 1.5f;
            cellHeight = a * s;
        } else return -1;

        int columns = (int) (gridWidth / cellWidth) + 1;
        int rows = (int) (gridHeight / cellHeight) + 1;

        double xoff = grid.getXoff();
        double yoff = grid.getYoff();

        double starti = ((-xoff) / cellWidth);
        double startj = ((-yoff) / cellHeight);

        float x = gridX + (float) (xoff);
        float y = gridY + (float) (yoff);
        if (gridType == 2) y -= cellHeight / 2;

        if (prevA != a) {
            double oldX = grid.getOldX();
            double oldY = grid.getOldY();

            if (gridType == 0) {
                newX = x + grid.getHighlightedI() * cellWidth;
                newY = y + grid.getHighlightedJ() * cellHeight;
            } else if (gridType == 1) {
                newX = x + grid.getHighlightedI() * cellWidth + (grid.getHighlightedJ() % 2) * cellWidth;
                newY = y + grid.getHighlightedJ() * cellHeight;
            } else if (gridType == 2) {
                newX = x + grid.getHighlightedI() * cellWidth;
                newY = y + grid.getHighlightedJ() * cellHeight + (grid.getHighlightedI() % 2) * cellHeight / 2;
            }

            grid.setDiffX(oldX - newX); // roznica miedzy starÄ a nowÄ pozycjÄ podswietlonego
            grid.setDiffY(oldY - newY);

            grid.setPrevZoom(grid.getZoom());
        }

        x += grid.getDiffX();
        y += grid.getDiffY();

        starti -= grid.getDiffX() / cellWidth;
        startj -= grid.getDiffY() / cellHeight;

        if (starti < 0) {
            x += starti * cellWidth;
            starti = 0;
            grid.setXoff(-grid.getDiffX());
            //+ wyzerowac jeszcze offset scrolla
        } else if (starti + columns >= Game.GRIDSIZE) { // jesli ostatni index komorki przekracza ilosc komorek
            starti = Game.GRIDSIZE - columns;
            x = gridX + (float) -starti * cellWidth;
            grid.setXoff(-(Game.GRIDSIZE - columns) * cellWidth - grid.getDiffX());

        }

        if (startj + rows >= Game.GRIDSIZE) {
            startj = Game.GRIDSIZE - rows;
            y = gridY + (float) -startj * cellHeight;
            grid.setYoff(-(Game.GRIDSIZE - rows) * cellHeight - grid.getDiffY());

        } else if (startj < 0) {
            y += startj * cellHeight;
            startj = 0;
            grid.setYoff(-grid.getDiffY());
        }
        glColor3f(fillColor.getR(), fillColor.getG(), fillColor.getB());
        if (gridType == 0) {
            for (int i = (int) starti; i < columns + starti; i++)
                for (int j = (int) startj; j < rows + startj; j++)
                    if (grid.isCellAlive(i, j))
                        Rectangle.display(x + i * cellWidth, y + j * cellHeight, cellWidth, cellHeight, true);

            glColor3f(lineColor.getR(), lineColor.getG(), lineColor.getB());

            for (int i = (int) starti; i < columns + starti; i++)
                for (int j = (int) startj; j < rows + startj; j++)
                    Rectangle.display(x + i * cellWidth, y + j * cellHeight, cellWidth, cellHeight, false);

            if (mouseX > gridX && mouseX < (gridX + gridWidth)) {
                if (mouseY > gridY && mouseY < (gridY + gridHeight)) {
                    int i = (int) ((mouseX - x) / cellWidth);
                    int j = (int) ((mouseY - y) / cellHeight);
                    glColor3f(0, 1, 0);
                    Rectangle.display(x + i * cellWidth, y + j * cellHeight, cellWidth, cellHeight, false);
                    codedPosition = Game.GRIDSIZE * i + j;

                    grid.setHighlightedI(i);
                    grid.setHighlightedJ(j);
                    grid.setOldX(x + i * cellWidth);
                    grid.setOldY(y + j * cellHeight);
                    glColor3f(1, 1, 0);

                    if (selection)
                        Rectangle.displaySelected(x, y, a, cellWidth, cellHeight, sel.getX(), sel.getY(), sel.getWidth(), sel.getHeight());

                    glColor3f(0.4f, 0.4f, 0.4f);

                    float clipXoff = 0;
                    float clipYoff = 0;

                    if ((i - clipboard.getWidth() / 2) < 0) clipXoff = clipboard.getWidth() / 2 - i;
                    else if ((i + clipboard.getWidth() / 2) >= Game.GRIDSIZE)
                        clipXoff = Game.GRIDSIZE - clipboard.getWidth() / 2 - 1 - i;

                    if ((j - clipboard.getHeight() / 2) < 0) clipYoff = clipboard.getHeight() / 2 - j;
                    else if ((j + clipboard.getHeight() / 2) >= Game.GRIDSIZE)
                        clipYoff = Game.GRIDSIZE - clipboard.getHeight() / 2 - 1 - j;


                    for (int ii = 0; ii < clipboard.getWidth(); ii++)
                        for (int jj = 0; jj < clipboard.getHeight(); jj++)
                            if (clipboard.isCellAlive(ii,jj))
                                Rectangle.display(x + (i + ii - clipboard.getWidth() / 2 + clipXoff) * cellWidth, y + (j + jj - clipboard.getHeight() / 2 + clipYoff) * cellHeight, cellWidth - 1, cellHeight - 1, true);

                }
            }
        } else if (gridType == 1) //triangular
        {
            for (int i = (int) starti; i < columns + starti; i++)
                for (int j = (int) startj; j < rows + startj; j++)
                    if (grid.isCellAlive((i + (j % 2)) % Game.GRIDSIZE, j))
                        Triangle.display(x + i * cellWidth + (j % 2) * cellWidth, y + j * cellHeight, a, (i % 2) > 0, true);

            glColor3f(lineColor.getR(), lineColor.getG(), lineColor.getB());
            for (int i = (int) starti; i < columns + starti; i++)
                for (int j = (int) startj; j < rows + startj; j++)
                    Triangle.display(x + i * cellWidth + (j % 2) * cellWidth, y + j * cellHeight, a, (i % 2) > 0, false);

            if (mouseX > gridX && mouseX < (gridX + gridWidth)) {
                if (mouseY > gridY && mouseY < (gridY + gridHeight)) {
                    int j = (int) ((mouseY - y) / cellHeight);
                    int i = (int) ((mouseX - x - (j % 2.) * cellWidth) / cellWidth);
                    // srodek wybranego trojkata = xM , yyy
                    double xM = x + i * cellWidth + (j % 2.) * cellWidth;
                    double dh = (i % 2) == 1 ? cellHeight / 3 : 2 * cellHeight / 3;
                    double dh2 = (i % 2) == 0 ? cellHeight / 3 : 2 * cellHeight / 3;
                    double yyy = (y + j * cellHeight) + dh;
                    double yyy2 = (y + j * cellHeight) + dh2;
                    // srodek lewego sasiada =p  xL , yyy
                    double xL = xM - a / 2;
                    // srodek prawego sasiada =  xR , yyy
                    double xR = xM + a / 2;

                    double rM = radius(mouseX, mouseY, xM, yyy);
                    // odleglosc myszki od srodka wybranego
                    double rL = radius(mouseX, mouseY, xL, yyy2); // odleglosc myszki od srodka lewego sasiada
                    double rR = radius(mouseX, mouseY, xR, yyy2);// odleglosc myszki od srodka prawego sasiada


                    if (rL < rR) {
                        if (rL < rM)
                            i--;
                    } else if (rR < rM) i++;


                    glColor3f(0, 1, 0);
                    Triangle.display(x + i * cellWidth + (j % 2) * cellWidth, y + j * cellHeight, a, (i % 2) > 0, false/*grid.isCellAlive((i + (j % 2)) % Game.GRIDSIZE, j)*/);
                    codedPosition = Game.GRIDSIZE * (i + j % 2) + j;

                    grid.setHighlightedI(i);
                    grid.setHighlightedJ(j);
                    grid.setOldX(x + i * cellWidth + (j % 2) * cellWidth);
                    grid.setOldY(y + j * cellHeight);
                    glColor3f(1, 1, 0);
                    if (selection)
                        Triangle.displaySelected(x, y, a, cellWidth, cellHeight, sel.getX(), sel.getY(), sel.getWidth(), sel.getHeight());


                    glColor3f(0.4f, 0.4f, 0.4f);

                    float clipXoff = 0;
                    float clipYoff = 0;

                    if ((i - clipboard.getWidth() / 2) < 0) clipXoff = clipboard.getWidth() / 2 - i;
                    else if ((i + clipboard.getWidth() / 2) >= Game.GRIDSIZE)
                        clipXoff = Game.GRIDSIZE - clipboard.getWidth() / 2 - 1 - i;

                    if ((j - clipboard.getHeight() / 2) < 0) clipYoff = clipboard.getHeight() / 2 - j;
                    else if ((j + clipboard.getHeight() / 2) >= Game.GRIDSIZE)
                        clipYoff = Game.GRIDSIZE - clipboard.getHeight() / 2 - 1 - j;

                    for (int ii = 0; ii < clipboard.getWidth(); ii++)
                        for (int jj = 0; jj < clipboard.getHeight(); jj++)
                            if (clipboard.isCellAlive(ii,jj))
                                Triangle.display(x + (i + ii - clipboard.getWidth() / 2 + clipXoff) * cellWidth + ((j + jj - clipboard.getHeight() / 2 + clipYoff) % 2) * cellWidth, y + (j + jj - clipboard.getHeight() / 2 + clipYoff) * cellHeight, a, ((i + ii - clipboard.getWidth() / 2 + clipXoff) % 2) > 0, true);

                }
            }

        } else if (gridType == 2) //hex
        {
            for (int i = (int) starti; i < columns + starti; i++)
                for (int j = (int) startj; j < rows + startj; j++)
                    if (grid.isCellAlive(i, j))
                        Hexagon.display(x + i * cellWidth, y + j * cellHeight + (i % 2) * cellHeight / 2, a, true);

            glColor3f(lineColor.getR(), lineColor.getG(), lineColor.getB());
            for (int i = (int) starti; i < columns + starti; i++)
                for (int j = (int) startj; j < rows + startj; j++)
                    Hexagon.display(x + i * cellWidth, y + j * cellHeight + (i % 2) * cellHeight / 2, a, false);

            if (mouseX > gridX && mouseX < (gridX + gridWidth)) {
                if (mouseY > gridY && mouseY < (gridY + gridHeight)) {
                    int i = (int) ((mouseX - x) / cellWidth);
                    int j = (int) ((mouseY - y - (i % 2) * cellHeight / 2) / cellHeight);

                    double xM = x + i * cellWidth + a / 2;
                    double xxxR = xM + cellWidth;
                    double yyy = y + j * cellHeight + (i % 2) * cellHeight / 2 + a * Math.sqrt(3) / 2;
                    double yUS = yyy - cellHeight / 2;
                    double yDS = yyy + cellHeight / 2;
                    double rM = radius(mouseX, mouseY, xM, yyy);
                    double rRDS = radius(mouseX, mouseY, xxxR, yDS);
                    double rRUS = radius(mouseX, mouseY, xxxR, yUS);
                    double min = Math.min(Math.min(rRDS, rRUS), rM);

                    if (rM != min) {
                        if (rRDS == min) {
                            int t = i % 2;
                            int temp = (j + i) % 2;
                            j += (j + i) % 2;
                            i++;
                            if (temp == 0) {
                                j++;
                            }
                            if (t == 0) {
                                j--;
                            }

                        } else if (rRUS == min) {
                            int t = i % 2;
                            int temp = (j + i) % 2;
                            j -= (j + i) % 2;
                            i++;
                            if (temp == 1) {
                                j++;
                            }
                            if (t == 0) {
                                j--;
                            }
                        }
                    }

                    glColor3f(0, 1, 0);
                    Hexagon.display(x + i * cellWidth, y + j * cellHeight + (i % 2) * cellHeight / 2, a, false);
                    codedPosition = Game.GRIDSIZE * i + j;

                    grid.setHighlightedI(i);
                    grid.setHighlightedJ(j);
                    grid.setOldX(x + i * cellWidth);
                    grid.setOldY(y + j * cellHeight + (i % 2) * cellHeight / 2);

                    glColor3f(1, 1, 0);

                    if (selection)
                        //t.setTxt("" + sel.getX() + " " + sel.getY() + " " + sel.getWidth() + " " + sel.getHeight());
                        Hexagon.displaySelected(x, y, a, cellWidth, cellHeight, sel.getX(), sel.getY(), sel.getWidth(), sel.getHeight());

                    glColor3f(0.4f, 0.4f, 0.4f);

                    float clipXoff = 0;
                    float clipYoff = 0;

                    if ((i - clipboard.getWidth() / 2) < 0) clipXoff = clipboard.getWidth() / 2 - i;
                    else if ((i + clipboard.getWidth() / 2) >= Game.GRIDSIZE)
                        clipXoff = Game.GRIDSIZE - clipboard.getWidth() / 2 - 1 - i;

                    if ((j - clipboard.getHeight() / 2) < 0) clipYoff = clipboard.getHeight() / 2 - j;
                    else if ((j + clipboard.getHeight() / 2) >= Game.GRIDSIZE)
                        clipYoff = Game.GRIDSIZE - clipboard.getHeight() / 2 - 1 - j;

                    for (int ii = 0; ii < clipboard.getWidth(); ii++)
                        for (int jj = 0; jj < clipboard.getHeight(); jj++)
                            if (clipboard.isCellAlive(ii,jj))
                                Hexagon.display(x + (i + ii - clipboard.getWidth() / 2 + clipXoff) * cellWidth, y + (j + jj - clipboard.getHeight() / 2 + clipYoff) * cellHeight + ((i + ii - clipboard.getWidth() / 2 + clipXoff) % 2) * cellHeight / 2, a - 1, true);
                }
            }

        }
        glColor3f(1, 1, 1);
        return codedPosition;
    }


    private double radius(double x1, double y1, double x2, double y2)
    {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }



    public boolean shouldRun() {
        return window.isOpen();
    }

    public void closeWindow() {
        window.close();
    }

}