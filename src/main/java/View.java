import graphics.*;
import graphics.Input.KeyboardHandler;
import graphics.Input.MouseButtonsHandler;
import graphics.Input.MouseHandler;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.opengl.GL11.*;

public class View {
    private Window window;
    private Button buttons;
    private Vector<Shape> shapes;
    private Vector<Checkbox> checkboxesS;
    private Text t;
    private int gridX=404, gridY=34, gridWidth=1114, gridHeight=1044, gridZoom=0;   //zoom w przedziale [0,100] -  ustawiany na sliderze
    private int rulesX=1520, rulesY = 2,rulesWidth= 400, rulesHeight= 535;
    private Slider zoomSlider;


    public View() {
        window = new Window(1920, 1080, "GOL", true);
        Text.load_font("sansation.ttf");
        //TODO implement me
        shapes = new Vector<Shape>();
        createLayout();
        checkboxesS=new Vector<Checkbox>();
        createCheckboxes();

        zoomSlider=new Slider(rulesX+50,rulesY+400, 300, 10);



       // t=new Text(600,300,"Lubie placki", 1.0f,0f,0f);
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
        int x=35;
        int y=100;
        for(int i=1;i<3;i++)
            for (int j = 1; j < 9; j++)
                checkboxesS.add(new Checkbox(rulesX + j * x, rulesY + i * y, 25));
    }

    private void displayCheckboxes()
    {


        double mouseX = MouseHandler.xPos();
        double mouseY = MouseHandler.yPos();
        if (mouseX > rulesX && mouseX < (rulesX + rulesWidth)&&mouseY > rulesY && mouseY < (rulesY + rulesHeight))
        {
            for (Checkbox c : checkboxesS)
                if(c.isFocused((int)mouseX, (int)mouseY)&& MouseButtonsHandler.isKeyDown(0))
                {
                    c.changeState();
                    break;
                }
        }

        for (Checkbox c : checkboxesS)
            c.draw();

    }

    public double[] getMousePosition() {
        return window.getMousePosition();
    }

    public void display()
    {
        // TODO display everything here: window.display(something); example below
        //shapes.elementAt(1).setColor(0, 0, 0, 1);
        //shapes.elementAt(2).setColor(0.5f, 0.5f, 0.5f, 0.5f);

        displayMask();

        displayLayout();

        displayCheckboxes();

        gridZoom=displaySlider();

        //glClear(GL_COLOR_BUFFER_BIT);
        /* select white for all lines  */
        //glColor3f(1.0f, 1.0f, 1.0f);

        /* in 1st row, 3 lines, each with a different stipple  */
        //glEnable(GL_LINE_STIPPLE);

        //glLineStipple(5, (short)(255/2));  /*  dotted  */
        //drawOneLine(0.0f, 125.0f, 500.0f, 500.0f);


        //glDisable(GL_LINE_STIPPLE);
        //glFlush();
        //t.display();
        window.update();

    }

    private int displaySlider()
    {
        double mouseX = MouseHandler.xPos();
        double mouseY = MouseHandler.yPos();
        if(zoomSlider.isFocused((int)mouseX, (int)mouseY)&& MouseButtonsHandler.isKeyDown(0) && !zoomSlider.state())
        {
            zoomSlider.changeState();
        }
        if(zoomSlider.state())
        {
            if(!MouseButtonsHandler.isKeyDown(0)) zoomSlider.changeState();
            zoomSlider.slide((int) mouseX);
        }
        zoomSlider.draw();
        return zoomSlider.getPercent();
    }

    private void displayMask()
    {
        glColor3f(0,0,0);
        Rectangle.display(0,0,gridX,1080,true);
        Rectangle.display(gridX,0, gridWidth,gridY,true);
        Rectangle.display(gridX+gridWidth,0,1920-gridX-gridWidth, 1080,true);
        Rectangle.display(gridX,gridY+gridHeight,gridWidth,1080-gridY-gridHeight,true);
        glColor3f(1,1,1);
    }

    public void drawOneLine(float x1,float y1,float x2,float y2) {
        glBegin(GL_LINES);
        glVertex2f((x1), (y1));
        glVertex2f((x2), (y2));
        glEnd();
    }

    public int display(Grid grid) {
        int codedPosition=-1;
        if (grid instanceof Squared) codedPosition = displaySquared(grid);
        else if (grid instanceof Triangular) codedPosition =displayTriangular(grid);
        else if (grid instanceof Hexagonal) codedPosition =displayHexagonal(grid);
        return codedPosition;
    }

    private int displaySquared(Grid grid)
    {
        int codedPosition=-1;
        glColor3f(0.8f, 0.8f, 0.8f);
        float size = 2 + gridZoom;
        float x = gridX;
        float y = gridY;
        int columns = (int) (Game.GRIDSIZE / size);
        int rows = (int) (Game.GRIDSIZE / size);
        for (int i = 0; i < columns; i++)
            for (int j = 0; j < rows; j++)
                Rectangle.display(x + i * size, y + j * size, size, size, grid.isCellAlive(i, j));
        
        //i
        // M = x + i * size
        // i = (M-x)/size

        //j
        // M = y + j * size
        // j = (M-x)/size


        double mouseX = MouseHandler.xPos();
        double mouseY = MouseHandler.yPos();
        if (mouseX > gridX && mouseX < (gridX + gridWidth))
        {
            if (mouseY > gridY && mouseY < (gridY + gridHeight))
            {
                int i=(int)((mouseX-x)/size);
                int j=(int)((mouseY-y)/size);
                glColor3f(0, 1, 0);
                Rectangle.display(x + i * size, y + j * size, size, size, grid.isCellAlive(i, j));
                if(MouseButtonsHandler.isKeyDown(0))codedPosition=Game.GRIDSIZE*i+j;
            }
        }
        return codedPosition;
    }

    private int displayHexagonal(Grid grid)
    {
        int codedPosition=-1;
        glColor3f(0.8f, 0.8f, 0.8f);
        float a = 1 + gridZoom;
        float s = (float) Math.sqrt(3);
        float x = gridX;
        float y = gridY-a*s/2;


        int columns = (int)(Game.GRIDSIZE / a/1.6)+1;
        int rows = (int)(Game.GRIDSIZE / a/2)+1;
        for (int i = 0; i < columns; i++)
            for (int j = 0; j < rows; j++)
                Hexagon.display(x + 3 * i * a / 2, y + j * a * s + (i % 2) * a * s / 2, a, grid.isCellAlive(i, j));
        //i
        // M = x + 3 * i * a / 2
        // i = (M-x) *2/3/a

        //j
        // M = y + j * a * s + (i % 2) * a * s / 2
        // j = ( M - y - (i % 2) * a * s / 2 ) /a/s


        double mouseX = MouseHandler.xPos();
        double mouseY = MouseHandler.yPos();
        if (mouseX > gridX && mouseX < (gridX + gridWidth))
        {
            if (mouseY > gridY && mouseY < (gridY + gridHeight))
            {
                int i = (int) ((mouseX-x) *2/3/a);
                int j = (int) ((mouseY - y - (i % 2) * a * s / 2 ) /a/s);
                glColor3f(0, 1, 0);
                Hexagon.display(x + 3 * i * a / 2, y + j * a * s + (i % 2) * a * s / 2, a, grid.isCellAlive(i, j));
                if(MouseButtonsHandler.isKeyDown(0))codedPosition=Game.GRIDSIZE*i+j;
            }
        }
        return codedPosition;
    }

    private int displayTriangular(Grid grid)
    {
        int codedPosition=-1;
        glColor3f(0.8f, 0.8f, 0.8f);
        float a = 4 + gridZoom;
        float s = (float) Math.sqrt(3);
        float x = gridX;
        float y = gridY - a * s / 2;
        int columns = (int) (Game.GRIDSIZE * 2 / a) + 1;
        int rows = (int) (Game.GRIDSIZE / a) + 5;
        for (int i = 0; i < columns; i++)
            for (int j = 0; j < rows; j++)
                Triangle.display(x + i * a / 2 + (j % 2) * a / 2, y + j * a * s / 2, a, (i % 2) > 0, grid.isCellAlive((i + (j % 2)) % Game.GRIDSIZE, j));


        //j
        // M =  y + j * a * s / 2
        // j = (M-y)*2/a/s

        //i
        // M = x + i * a / 2 + (j % 2) * a / 2
        // i = (M - x -(j % 2) * a / 2 ) * 2 /a


        double mouseX = MouseHandler.xPos();
        double mouseY = MouseHandler.yPos();
        if (mouseX > gridX && mouseX < (gridX + gridWidth))
        {
            if (mouseY > gridY && mouseY < (gridY + gridHeight))
            {
                int j = (int) ((mouseY - y) * 2 / a / s);
                int i = (int) ((mouseX - x - (j % 2) * a / 2) * 2 / a);
                glColor3f(0, 1, 0);
                Triangle.display(x + i * a / 2 + (j % 2) * a / 2, y + j * a * s / 2, a, (i % 2) > 0, grid.isCellAlive((i + (j % 2)) % Game.GRIDSIZE, j));
                if(MouseButtonsHandler.isKeyDown(0))codedPosition=Game.GRIDSIZE*i+j;
            }
        }
        return codedPosition;
    }

    public boolean shouldRun() { return window.isOpen(); }

    public void closeWindow() { window.close(); }

    public void clearScreen() { window.clear(); }
}