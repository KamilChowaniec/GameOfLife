import graphics.*;
import graphics.Input.MouseButtonsHandler;
import graphics.Input.MouseHandler;

import java.util.Vector;

import static org.lwjgl.opengl.GL11.*;

public class View
{
    private Window window;
    private Button buttons;
    private Vector<Shape> shapes;
    private Vector<Checkbox> checkboxesS;
    private Text t;
    public static int gridX = 404, gridY = 34, gridWidth = 1114, gridHeight = 1044, delaySlider = 0;   //zoom w przedziale [0,100] -  ustawiany na sliderze
    private int rulesX = 1520, rulesY = 2, rulesWidth = 400, rulesHeight = 535;
    private Slider zoomSlider;

//    private double xoff = 0;
//    private double yoff = 0;


    public View()
    {
        window = new Window(1920, 1080, "GOL", true);
        Text.load_font("sansation.ttf");
        //TODO implement me
        shapes = new Vector<Shape>();
        createLayout();
        checkboxesS = new Vector<Checkbox>();
        //createCheckboxes();

        zoomSlider = new Slider(rulesX + 50, rulesY + 400, 300, 10);


        t = new Text(100, 100, "Lubie placki", 1.0f, 0f, 0f);
    }


    private void createLayout()
    {
        shapes.add(new Rectangle(2, 2, 400, 1076));//tools
        shapes.add(new Rectangle(404, 2, 150, 30));//card
        shapes.add(new Rectangle(556, 2, 150, 30));//card
        shapes.add(new Rectangle(708, 2, 150, 30));//card
        shapes.add(new Rectangle(860, 2, 150, 30));//card
        shapes.add(new Rectangle(1012, 2, 150, 30));//card
        shapes.add(new Rectangle(gridX, gridY, gridWidth, gridHeight));//grid
        shapes.add(new Rectangle(rulesX, rulesY, rulesWidth, rulesHeight));//rules
        shapes.add(new Rectangle(1520, 540, 400, 537));//clipboard
    }

    private void displayLayout()
    {
        for (Shape s : shapes)
            window.display(s);
    }

    private void createCheckboxes()
    {
        int x = 35;
        int y = 100;
        for (int i = 1; i < 3; i++)
            for (int j = 1; j < 9; j++) {
                //checkboxesS.add(new Checkbox(rulesX + j * x, rulesY + i * y, 25));
            }
    }

    private void displayCheckboxes()
    {


        double mouseX = MouseHandler.xPos();
        double mouseY = MouseHandler.yPos();
        if (mouseX > rulesX && mouseX < (rulesX + rulesWidth) && mouseY > rulesY && mouseY < (rulesY + rulesHeight))
        {
            for (Checkbox c : checkboxesS)
                if (c.isFocused((int) mouseX, (int) mouseY) && MouseButtonsHandler.isKeyDown(0))
                {
                    c.changeState();
                    break;
                }
        }

        for (Checkbox c : checkboxesS)
            c.draw();

    }

    public double[] getMousePosition()
    {
        return window.getMousePosition();
    }

    public void display()
    {
        // TODO display everything here: window.display(something); example below
        //shapes.elementAt(1).setColor(0, 0, 0, 1);
        //shapes.elementAt(2).setColor(0.5f, 0.5f, 0.5f, 0.5f);



        displayLayout();

        //displayCheckboxes();

        delaySlider = displaySlider();

        //glClear(GL_COLOR_BUFFER_BIT);
        /* select white for all lines  */
        //glColor3f(1.0f, 1.0f, 1.0f);

        /* in 1st row, 3 lines, each with a different stipple  */
        //glEnable(GL_LINE_STIPPLE);

        //glLineStipple(5, (short)(255/2));  /*  dotted  */
        //drawOneLine(0.0f, 125.0f, 500.0f, 500.0f);


        //glDisable(GL_LINE_STIPPLE);
        //glFlush();
        t.display();
        window.update();

    }

    private int displaySlider()
    {
        double mouseX = MouseHandler.xPos();
        double mouseY = MouseHandler.yPos();
        if (zoomSlider.isFocused((int) mouseX, (int) mouseY) && MouseButtonsHandler.isKeyDown(0) && !zoomSlider.state())
        {
            zoomSlider.changeState();
        }
        if (zoomSlider.state())
        {
            if (!MouseButtonsHandler.isKeyDown(0)) zoomSlider.changeState();
            zoomSlider.slide((int) mouseX);
        }
        zoomSlider.draw();
        return zoomSlider.getPercent();
    }

    private void displayMask()
    {
        glColor3f(0, 0, 0);
        Rectangle.display(0, 0, gridX, 1080, true);
        Rectangle.display(gridX, 0, gridWidth, gridY, true);
        Rectangle.display(gridX + gridWidth, 0, 1920 - gridX - gridWidth, 1080, true);
        Rectangle.display(gridX, gridY + gridHeight, gridWidth, 1080 - gridY - gridHeight, true);
        glColor3f(1, 1, 1);
    }

    public void drawOneLine(float x1, float y1, float x2, float y2)
    {
        glBegin(GL_LINES);
        glVertex2f((x1), (y1));
        glVertex2f((x2), (y2));
        glEnd();
    }

    public int display(Grid grid)
    {
        int codedPosition = -1;
        if (grid instanceof Squared) codedPosition = displaySquared(grid);
        else if (grid instanceof Triangular) codedPosition = displayTriangular(grid);
        else if (grid instanceof Hexagonal) codedPosition = displayHexagonal(grid);
        displayMask();
        return codedPosition;
    }





    private int displayTriangular(Grid grid)
    {
        double mouseX = MouseHandler.xPos();
        double mouseY = MouseHandler.yPos();
        int codedPosition = -1;
        float a = 4 + grid.getZoom();
        float prevA = 4 + grid.getPrevZoom();
        float s = (float) Math.sqrt(3);

        float cellWidth = a / 2;
        float cellHeight = a * s / 2;

        int columns = (int) (gridWidth / cellWidth) + 1;
        int rows = (int) (gridHeight / cellHeight) + 1;


        double xoff = grid.getXoff();
        double yoff = grid.getYoff();

        double starti = ((-xoff) / cellWidth);
        double startj = ((-yoff) / cellHeight);



        float x = gridX + (float) (xoff);
        float y = gridY + (float) (yoff);


        if (prevA != a)
        {
            double oldX = grid.getOldX();
            double oldY = grid.getOldY();

            double newX = x + grid.getHighlightedI() * cellWidth + (grid.getHighlightedJ() % 2) * cellWidth;
            double newY = y + grid.getHighlightedJ() * cellHeight;

            grid.setDiffX(oldX - newX); // roznica miedzy starą a nową pozycją podswietlonego
            grid.setDiffY(oldY - newY);

            grid.setPrevZoom(a - 4);

        }


        x += grid.getDiffX();
        y += grid.getDiffY();

        starti -= grid.getDiffX() / cellWidth;
        startj -= grid.getDiffY() / cellHeight;

        if (starti < 0)
        {
            x += starti * cellWidth;
            starti = 0;
            //+ wyzerowac jeszcze offset scrolla
        } else if (starti + columns >= Game.GRIDSIZE)
        { // jesli ostatni index komorki przekracza ilosc komorek
            starti = Game.GRIDSIZE - columns;
            x = gridX + (float) -starti * cellWidth;
        }

        if (startj + rows >= Game.GRIDSIZE)
        {
            startj = Game.GRIDSIZE - rows;
            y = gridY + (float) -startj * cellHeight;
        } else if (startj < 0)
        {
            y += startj * cellHeight;
            startj = 0;
        }

        for (int i = (int) starti; i < columns + starti; i++)
            for (int j = (int) startj; j < rows + startj; j++)
                Triangle.display(x + i * cellWidth + (j % 2) * cellWidth, y + j * cellHeight, a, (i % 2) > 0, grid.isCellAlive((i + (j % 2)) % Game.GRIDSIZE, j));


        if (mouseX > gridX && mouseX < (gridX + gridWidth))
        {
            if (mouseY > gridY && mouseY < (gridY + gridHeight))
            {
                int j = (int) ((mouseY - y) / cellHeight);
                int i = (int) ((mouseX - x - (j % 2.) * cellWidth) /cellWidth);
                // srodek wybranego trojkata = xM , yyy
                double xM = x + i *cellWidth + (j % 2.) * cellWidth;
                double dh = (i % 2) == 1 ? cellHeight/3 : 2*cellHeight/3;
                double dh2 = (i % 2) == 0 ? cellHeight/3 : 2*cellHeight/3;
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


                if (rL < rR)
                {
                    if (rL < rM)
                        i--;
                } else if (rR < rM) i++;


                glColor3f(0, 1, 0);
                Triangle.display(x + i * cellWidth + (j % 2) * cellWidth, y + j * cellHeight, a, (i % 2) > 0, false/*grid.isCellAlive((i + (j % 2)) % Game.GRIDSIZE, j)*/);
                codedPosition = Game.GRIDSIZE * (i + j % 2) + j;

                grid.setHighlightedI(i);
                grid.setHighlightedJ(j);
                grid.setOldX(x + i * cellWidth + (j % 2) * cellWidth);
                grid.setOldY( y + j * cellHeight);
                t.setTxt(i + " " + j);
            }
        }
        return codedPosition;
    }



    private int displayHexagonal(Grid grid)
    {
        double mouseX = MouseHandler.xPos();
        double mouseY = MouseHandler.yPos();
        int codedPosition = -1;
        double xoff = grid.getXoff();
        double yoff = grid.getYoff();
        float a = 1 + grid.getZoom();
        float prevA = 1 + grid.getPrevZoom();
        float s = (float) Math.sqrt(3);

        float cellWidth = a * 1.5f;
        float cellHeight = a * s;

        int columns = (int) (gridWidth / cellWidth) + 1;
        int rows = (int) (gridHeight / cellHeight) + 1;

        double starti = ((-xoff) / cellWidth);
        double startj = ((-yoff) / cellHeight);

        if (starti + columns >= Game.GRIDSIZE - 1)
        {
            starti = Game.GRIDSIZE - columns;
            xoff = -starti * cellWidth;
        }
        if (startj + rows >= Game.GRIDSIZE - 1)
        {
            startj = Game.GRIDSIZE - rows;
            yoff = -startj * cellHeight;
        }

        float x = gridX + (float) (xoff);
        float y = gridY + (float) (yoff - cellHeight / 2);

        if (prevA != a)
        {
            double oldX = grid.getOldX();
            double oldY = grid.getOldY();


            double newX = x + grid.getHighlightedI() * cellWidth;
            double newY = y + grid.getHighlightedJ() * cellHeight + (grid.getHighlightedI() % 2) * cellHeight / 2;

            grid.setDiffX(oldX - newX); // roznica miedzy starą a nową pozycją podswietlonego
            grid.setDiffY(oldY - newY);

            grid.setPrevZoom(a - 1);

        }

        x += grid.getDiffX();
        y += grid.getDiffY();

        starti -= grid.getDiffX() / cellWidth;
        startj -= grid.getDiffY() / cellHeight;

        if (starti < 0)
        {
            x += starti * cellWidth;
            starti = 0;
            //+ wyzerowac jeszcze offset scrolla
        } else if (starti + columns >= Game.GRIDSIZE)
        { // jesli ostatni index komorki przekracza ilosc komorek
            starti = Game.GRIDSIZE - columns;
            x = gridX + (float) -starti * cellWidth;
        }

        if (startj + rows >= Game.GRIDSIZE)
        {
            startj = Game.GRIDSIZE - rows;
            y = gridY + (float) -startj * cellHeight;
        } else if (startj < 0)
        {
            y += startj * cellHeight;
            startj = 0;
        }


        for (int i = (int) starti; i < columns + starti; i++)
            for (int j = (int) startj; j < rows + startj; j++)
                Hexagon.display(x + i * cellWidth, y + j * cellHeight + (i % 2) * cellHeight / 2, a, grid.isCellAlive(i, j));

        if (mouseX > gridX && mouseX < (gridX + gridWidth))
        {
            if (mouseY > gridY && mouseY < (gridY + gridHeight))
            {
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

                if (rM != min)
                {
                    if (rRDS == min)
                    {
                        int t = i % 2;
                        int temp = (j + i) % 2;
                        j += (j + i) % 2;
                        i++;
                        if (temp == 0)
                        {
                            j++;
                        }
                        if (t == 0)
                        {
                            j--;
                        }

                    } else if (rRUS == min)
                    {
                        int t = i % 2;
                        int temp = (j + i) % 2;
                        j -= (j + i) % 2;
                        i++;
                        if (temp == 1)
                        {
                            j++;
                        }
                        if (t == 0)
                        {
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
                t.setTxt(i + " " + j);

            }
        }
        return codedPosition;
    }




    private int displaySquared(Grid grid)
    {
        double mouseX = MouseHandler.xPos();
        double mouseY = MouseHandler.yPos();
        int codedPosition = -1;
        float a = 2 + grid.getZoom();
        float prevA = 2 + grid.getPrevZoom();

        int columns = (int) (gridWidth / a)+1; // liczba kolumn = szerokosc pola/dlugosc boku
        int rows = (int) (gridHeight / a)+1 ;

        double xoff=grid.getXoff();
        double yoff=grid.getYoff();

        double starti = -xoff / a; // poczatkowy indeks komorki = przesuniecie/dlugosc boku
        double startj = -yoff / a;

        float x = gridX + (float) (xoff); // punkt startowy drukowania grida
        float y = gridY + (float) (yoff);

        if(prevA != a) // jesli zoom zostal zmieniony
        {
            double oldX = grid.getOldX(); // pozycja podswietlonego ze starym zoomem
            double oldY = grid.getOldY();

            double newX = x + grid.getHighlightedI() * a; // pozycja podswietlonego z nowym zoomem
            double newY = y + grid.getHighlightedJ() * a;

            grid.setDiffX(oldX - newX); // roznica miedzy starą a nową pozycją podswietlonego
            grid.setDiffY(oldY - newY);

            grid.setPrevZoom(a-2);
        }

        xoff+=grid.getDiffX();
        yoff+=grid.getDiffY();

        starti-=grid.getDiffX()/a;
        startj-=grid.getDiffY()/a;

        if(starti<0)
        {

            xoff+=starti*a;
            starti=0;
            grid.setXoff(-grid.getDiffX());
            //+ wyzerowac jeszcze offset scrolla
        }
        else if (starti + columns >= Game.GRIDSIZE)
        { // jesli ostatni index komorki przekracza ilosc komorek
            starti = Game.GRIDSIZE - columns;
            xoff = (float)-starti * a;
            grid.setXoff(-(Game.GRIDSIZE-columns)*a-grid.getDiffX());

        }

        if (startj + rows >= Game.GRIDSIZE)
        {
            startj = Game.GRIDSIZE - rows;
            yoff = (float)-startj * a;
            grid.setYoff(-(Game.GRIDSIZE-rows)*a-grid.getDiffY());
        } else if(startj<0)
        {
            yoff += startj * a;
            startj = 0;
            grid.setYoff(-grid.getDiffY());

        }


        x = gridX + (float) (xoff); // punkt startowy drukowania grida
        y = gridY + (float) (yoff);

        for (int i = (int) starti; i < columns + starti; i++)
            for (int j = (int) startj; j < rows + startj; j++)
                Rectangle.display(x + i * a, y + j * a, a, a, grid.isCellAlive(i, j));

        if (mouseX > gridX && mouseX < (gridX + gridWidth)) {
            if (mouseY > gridY && mouseY < (gridY + gridHeight)) {
                int i = (int) ((mouseX - x) / a);
                int j = (int) ((mouseY - y) / a);
                glColor3f(0, 1, 0);
                Rectangle.display(x + i * a, y + j * a, a, a, false);
                codedPosition = Game.GRIDSIZE * i + j;

                grid.setHighlightedI(i);
                grid.setHighlightedJ(j);
                grid.setOldX(x + i * a);
                grid.setOldY(y + j * a);
                t.setTxt(i + " " + j);
            }
        }
        return codedPosition;
    }




    private double radius(double x1, double y1, double x2, double y2)
    {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }



//
//
//        if (upsideDown)
//        {
//            glVertex2f(x - a / 2, y);
//            glVertex2f(x + a / 2, y);
//            glVertex2f(x, y + a * (float) Math.sqrt(3) / 2);
//
//        } else
//        {
//            glVertex2f(x, y);
//            glVertex2f(x + a / 2, y + a * (float) Math.sqrt(3) / 2);
//            glVertex2f(x - a / 2, y + a * (float) Math.sqrt(3) / 2);
//        }
//
//
//


    public boolean shouldRun() {
        return window.isOpen();
    }

    public void closeWindow() {
        window.close();
    }

    public void clearScreen() {
        window.clear();
    }

}