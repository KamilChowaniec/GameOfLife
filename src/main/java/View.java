import graphics.*;
import graphics.Input.KeyboardHandler;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.opengl.GL11.*;

public class View {
    private Window window;
    private Button buttons;
    private Checkbox checkboxes;
    private Vector<Shape> shapes;
    private Text t;

    public View() {
        window = new Window(1920, 1080, "GOL", false);
        Text.load_font("sansation.ttf");
        //TODO implement me

        shapes = new Vector<Shape>();
        shapes.add(new Rectangle(60, 20, 250, 1040));//tools
        shapes.add(new Rectangle(440, 20, 150, 30));//card
        shapes.add(new Rectangle(615, 20, 150, 30));//card
        shapes.add(new Rectangle(790, 20, 150, 30));//card
        shapes.add(new Rectangle(965, 20, 150, 30));//card
        shapes.add(new Rectangle(1140, 20, 150, 30));//card
        shapes.add(new Rectangle(440, 60, 1000, 1000));//grid
        shapes.add(new Rectangle(1540, 20, 300, 500));//rules
        shapes.add(new Rectangle(1540, 540, 300, 500));//clipboard

       // t=new Text(600,300,"Lubie placki", 1.0f,0f,0f);
    }

    public double[] getMousePosition() {
        return window.getMousePosition();
    }

    public void display() {
        // TODO implement me
        //window.clear();
        // TODO display everything here: window.display(something); example below
        //shapes.elementAt(1).setColor(0, 0, 0, 1);
        //shapes.elementAt(2).setColor(0.5f, 0.5f, 0.5f, 0.5f);


        for (Shape s : shapes)
            window.display(s);

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

    public void drawOneLine(float x1,float y1,float x2,float y2) {
        glBegin(GL_LINES);
        glVertex2f((x1), (y1));
        glVertex2f((x2), (y2));
        glEnd();
    }

    public void display(Grid grid) {
        if (grid instanceof Squared) displaySquared(grid);
        else if (grid instanceof Triangular) displayTriangular(grid);
        else if (grid instanceof Hexagonal) displayHexagonal(grid);


    }

    public void displaySquared(Grid grid)
    {
        glColor3f(0.8f, 0.8f, 0.8f);
        int size = 10;
        for (int i = 0; i < Game.GRIDSIZE; i++)
            for (int j = 0; j < Game.GRIDSIZE; j++)
                Rectangle.display(i * size, j * size, size, size, grid.isCellAlive(i, j));
    }

    public void displayHexagonal(Grid grid)
    {
        glColor3f(0.8f, 0.8f, 0.8f);
        float x = 0;
        float y = 0;
        float a = 10;
        float s = (float) Math.sqrt(3);
        for (int i = 0; i < Game.GRIDSIZE / a; i++)
            for (int j = 0; j < Game.GRIDSIZE / a; j++)
                Hexagon.display(x + 3 * i * a / 2, y + j * a * s + ((i) % 2) * a * s / 2, a, grid.isCellAlive(i, j));
    }

    public void displayTriangular(Grid grid)
    {
        glColor3f(0.8f, 0.8f, 0.8f);
        float x = 0;
        float y = 0;
        float a = 20;
        float s = (float) Math.sqrt(3);
        for (int i = 0; i < Game.GRIDSIZE ; i++)
            for (int j = 0; j < Game.GRIDSIZE / a; j++)
                Triangle.display(x + i * a / 2 + (j % 2) * a / 2, y + j * a * s / 2, a, (i % 2) > 0, grid.isCellAlive((i + (j % 2)) % Game.GRIDSIZE, j));
    }

    public boolean shouldRun() { return window.isOpen(); }

    public void closeWindow() { window.close(); }

    public void clearScreen() { window.clear(); }
}