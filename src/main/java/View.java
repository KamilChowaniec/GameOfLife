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
    public static int rulesX = 1520, rulesY = 2, rulesWidth = 400, rulesHeight = 535;
    private Slider zoomSlider;

    private float hexagonalPrevZoom = 1;
    private double hX = 0;
    private double hY = 0;
    private int ih = 0;
    private int jh = 0;
    private float oldhX = 0;
    private float oldhY = 0;
    private int hi = 0;
    private int hj = 0;




    private float triangularPrevZoom = 4;
    private double tX = 0;
    private double tY = 0;
    private int it = 0;
    private int jt = 0;
    private float oldtX = 0;
    private float oldtY = 0;
    private int ti = 0;
    private int tj = 0;


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

        //delaySlider = displaySlider();

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
        clearScreen();

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
        if (grid instanceof Squared) codedPosition = grid.display(gridX,gridY,gridWidth,gridHeight);
        else if (grid instanceof Triangular) codedPosition = displayTriangular(grid);
        else if (grid instanceof Hexagonal) codedPosition = displayHexagonal(grid);
        displayMask();
        return codedPosition;
    }

    private int displayTriangular(Grid grid)
    {
        double mouseX = MouseHandler.xPos();
        double mouseY = MouseHandler.yPos();
        double xoff = grid.getXoff();
        double yoff = grid.getYoff();
        int codedPosition = -1;
        glColor3f(0.8f, 0.8f, 0.8f);
        float a = 4 + grid.getZoom();
        float s = (float) Math.sqrt(3);


        int columns = (int) (Game.GRIDSIZE * 2 / a) + 5;
        int rows = (int) (Game.GRIDSIZE / a * s) + 5;

        double starti = ((-xoff) * 2 / a);
        double startj = ((-yoff) / a*s); //*s?
        if (starti < 0) starti = 0;
        if (startj < 0) startj = 0;
        if (starti + columns >= Game.GRIDSIZE)
        {
            starti = Game.GRIDSIZE - columns;
            xoff =  starti * a / 2; //*-1?
        }
        if (startj + rows >= Game.GRIDSIZE)
        {
            startj = Game.GRIDSIZE - rows;
            yoff =  startj * a/s; //*-1?
        }

        float x = gridX + (float) (xoff);
        float y = gridY - a * s / 2 + (float) (yoff); // -a*s/2 ?




        if (triangularPrevZoom != a)
        {

            double newX = x + it * a / 2 + (jt % 2) * a / 2;
            double newY = y + jt * a * s / 2;


            tX = (oldtX - newX);
            tY = (oldtY - newY);

            ti = (int) (tX * 2 / a);
            tj = (int) (tY * s / a);
            triangularPrevZoom = a;

        }
        x += tX;
        y += tY;

        starti -= ti;
        startj -= tj;

        if (starti < 0) starti = 0;
        if (startj < 0) startj = 0;
        if (starti + columns >= Game.GRIDSIZE)
        {
            starti = Game.GRIDSIZE - columns;
        }
        if (startj + rows >= Game.GRIDSIZE)
        {
            startj = Game.GRIDSIZE - rows;
        }


        if((x + starti * a / 2 +  a)>gridX) // jesli pierwszy wyswietlany cell ma wyswietlic sie za bardzo na prawo to
            x+=(gridX-x + starti * a / 2 + a); // przesun wszystkie na poczatek

        if((y + startj * a * s / 2)>gridY)
            y+=(gridY-y + startj * a * s / 2);

        for (int i = (int) starti; i < columns + starti; i++)
            for (int j = (int) startj; j < rows + startj; j++)
                Triangle.display(x + i * a / 2 + (j % 2) * a / 2, y + j * a * s / 2, a, (i % 2) > 0, grid.isCellAlive((i + (j % 2)) % Game.GRIDSIZE, j));


        //j
        // M =  y + j * a * s / 2
        // j = (M-y)*2/a/s


        //i
        // M = x + i * a / 2 + (j % 2) * a / 2
        // i = (M - x -(j % 2) * a / 2 ) * 2 /a


        if (mouseX > gridX && mouseX < (gridX + gridWidth))
        {
            if (mouseY > gridY && mouseY < (gridY + gridHeight))
            {
                int j = (int) ((mouseY - y) * 2. / a / s);
                int i = (int) ((mouseX - x - (j % 2.) * a / 2.) * 2. / a);
                // srodek wybranego trojkata = xM , yyy
                double xM = x + i * a / 2. + (j % 2.) * a / 2.;
                double dh = (i % 2) == 1 ? Math.sqrt(3.) * a / 6. : Math.sqrt(3.) * a / 3.;
                double dh2 = (i % 2) == 0 ? Math.sqrt(3.) * a / 6. : Math.sqrt(3.) * a / 3.;
                double yyy = (y + j * a * s / 2) + dh;
                double yyy2 = (y + j * a * s / 2) + dh2;
                // srodek lewego sasiada =p  xL , yyy
                double xL = xM - a / 2;
                // srodek prawego sasiada =  xR , yyy
                double xR = xM + a / 2;

                double rM = radius(mouseX, mouseY, xM, yyy);
                // odleglosc myszki od srodka wybranego
                double rL = radius(mouseX, mouseY, xL, yyy2); // odleglosc myszki od srodka lewego sasiada
                double rR = radius(mouseX, mouseY, xR, yyy2);// odleglosc myszki od srodka prawego sasiada

//                System.out.println((int)rM+" "+(int)rL+" "+(int)rR);
//
//                glColor3f(1, 0, 0);
//                drawOneLine((float)mouseX,(float)mouseY,(float)xM,(float)yyy);
//                glColor3f(0, 1, 0);
//                drawOneLine((float)mouseX,(float)mouseY,(float)xL,(float)yyy2);
//                glColor3f(0, 0, 1);
//                drawOneLine((float)mouseX,(float)mouseY,(float)xR,(float)yyy2);

                if (rL < rR)
                {
                    if (rL < rM)
                        i--;
                } else if (rR < rM) i++;


                glColor3f(0, 1, 0);
                Triangle.display(x + i * a / 2 + (j % 2) * a / 2, y + j * a * s / 2, a, (i % 2) > 0, false/*grid.isCellAlive((i + (j % 2)) % Game.GRIDSIZE, j)*/);
                codedPosition = Game.GRIDSIZE * (i + j % 2) + j;
                t.setTxt(i + " " + j + "  " + Double.toString(MouseHandler.xPos()) + "  " + Double.toString(MouseHandler.yPos()));
                it = i;
                jt = j;
                oldtX = x + i * a / 2 + (j % 2) * a / 2;
                oldtY = y + j * a * s / 2;

            }
        }
        return codedPosition;
    }

    private int displayHexagonal(Grid grid)
    {
        double mouseX = MouseHandler.xPos();
        double mouseY = MouseHandler.yPos();
        double xoff = grid.getXoff();
        double yoff = grid.getYoff();
        int codedPosition = -1;
        glColor3f(0.8f, 0.8f, 0.8f);
        float a = 1 + grid.getZoom();
        float s = (float) Math.sqrt(3);

        int columns = (int) ( Game.GRIDSIZE / a / s)+10;
        int rows = (int) (Game.GRIDSIZE / a / 2)+10;

        double starti =  ((-xoff) / a / s);
        double startj =  ((-yoff) / a / 2);
        if (starti < 0) starti = 0;
        if (startj < 0) startj = 0;
        if (starti + columns >= Game.GRIDSIZE)
        {
            starti = Game.GRIDSIZE - columns;
            xoff =  starti * a * s;
        }
        if (startj + rows >= Game.GRIDSIZE)
        {
            startj = Game.GRIDSIZE - rows;
            yoff = startj * a * 2;
        }

        float x = gridX + (float) (xoff);
        float y = gridY - a * s / 2 + (float) (yoff);


        if (hexagonalPrevZoom != a)
        {
            double newX = x + 3 * ih * a / 2;
            double newY = y + jh * a * s + (ih % 2) * a * s / 2;


            hX = (oldhX - newX);
            hY = (oldhY - newY);

            hi = (int) (hX /a/s);
            hj = (int) (hY /a/2);
            hexagonalPrevZoom = a;
        }
        x += hX;
        y += hY;


        starti -= hi;
        startj -= hj;

        starti=(int)starti*1.15;
        startj=(int)startj*1.15-3;

        columns=(int)(columns*1.2);
        rows=(int)(rows*1.2);

        if (starti < 0) starti = 0;
        if (startj < 0) startj = 0;
        if (starti + columns >= Game.GRIDSIZE)
        {
            starti = Game.GRIDSIZE - columns;
        }
        if (startj + rows >= Game.GRIDSIZE)
        {
            startj = Game.GRIDSIZE - rows;
        }

        if((x + 3 * starti * a / 2)>gridX) // jesli pierwszy wyswietlany cell ma wyswietlic sie za bardzo na prawo to
            x+=(gridX-x + 3 * starti * a / 2); // przesun wszystkie na poczatek

        if((y + startj * a * s + (starti % 2) * a * s / 2)>gridY)
            y+=(gridY-y + startj * a * s + (starti % 2) * a * s / 2);

        for (int i = (int) starti; i < columns + starti; i++)
            for (int j = (int) startj; j < rows + startj; j++)
                Hexagon.display(x + 3 * i * a / 2, y + j * a * s + (i % 2) * a * s / 2, a, grid.isCellAlive(i, j));
        //i
        // M = x + 3 * i * a / 2
        // i = (M-x) *2/3/a

        //j
        // M = y + j * a * s + (i % 2) * a * s / 2
        // j = ( M - y - (i % 2) * a * s / 2 ) /a/s


        if (mouseX > gridX && mouseX < (gridX + gridWidth))
        {
            if (mouseY > gridY && mouseY < (gridY + gridHeight))
            {
                int i = (int) ((mouseX - x) * 2 / 3 / a);
                int j = (int) ((mouseY - y - (i % 2) * a * s / 2) / a / s);


                double xM = x + 3 * i * a / 2 + a / 2;
                double xxxR = xM + 3 * a / 2;
                double yyy = y + j * a * s + (i % 2) * a * s / 2 + a * Math.sqrt(3) / 2;
                double yUS = yyy - a * Math.sqrt(3) / 2;
                double yDS = yyy + a * Math.sqrt(3) / 2;

//                glColor3f(1, 0, 0);
//                drawOneLine((float) mouseX, (float) mouseY, (float) xM, (float) yyy); //aktualny
//                glColor3f(1, 1, 0);
//                drawOneLine((float) mouseX, (float) mouseY, (float) xM, (float) yU); // powyżej
//                glColor3f(1, 1, 1);
//                drawOneLine((float) mouseX, (float) mouseY, (float) xM, (float) yD); // ponizej
//                glColor3f(0, 1, 0);
//                drawOneLine((float) mouseX, (float) mouseY, (float) xxxL, (float) yDS); // lewy dolny
//                glColor3f(0, 0, 1);
//                drawOneLine((float) mouseX, (float) mouseY, (float) xxxL, (float) yUS); // lewy gorny
//                glColor3f(0, 1, 1);
//                drawOneLine((float) mouseX, (float) mouseY, (float) xxxR, (float) yDS); // prawy dolny
//                glColor3f(1, 0, 1);
//                drawOneLine((float) mouseX, (float) mouseY, (float) xxxR, (float) yUS); // prawy gorny

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
                Hexagon.display(x + 3 * i * a / 2, y + j * a * s + (i % 2) * a * s / 2, a, false);
                codedPosition = Game.GRIDSIZE * i + j;
                t.setTxt(i + " " + j + "  " + Double.toString(MouseHandler.xPos()) + "  " + Double.toString(MouseHandler.yPos()));
                ih = i;
                jh = j;
                oldhX = x + 3 * i * a / 2;
                oldhY = y + j * a * s + (i % 2) * a * s / 2;
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