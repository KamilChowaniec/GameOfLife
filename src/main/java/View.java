import graphics.*;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

import static org.lwjgl.opengl.GL11.*;

public class View {
    private Window window;
    private Button buttons;
    private Checkbox checkboxes;
    private Vector<Shape> shapes;
    private Text t;

    public View() {
        window = new Window(1280, 720, "GOL");
        Text.load_font("sansation.ttf");
        //TODO implement me

        shapes = new Vector<Shape>();
        shapes.add(new Rectangle(100, 100, 100, 100));
        shapes.add(new Rectangle(105, 105, 90, 90));
        shapes.add(new Hexagon(300, 300, 100));
        shapes.add(new Circle(200, 200, 50));
        shapes.add(new Triangle(500, 500, 100));

        t=new Text(600,300,"Lubie placki", 1.0f,0f,0f);
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


        //for (Shape s : shapes)
        //    window.display(s);



        t.display();
        window.update();
    }

    public void display(Grid grid) {
        if (grid instanceof Squared) displayTriangular(grid);
        else if (grid instanceof Triangular) displayTriangular(grid);
        else if (grid instanceof Hexagonal) displayHexagonal(grid);


    }

    public void displaySquared(Grid grid)
    {
        glColor3f(0.8f, 0.8f, 0.8f);

        int size = 10;
        for (int i = 0; i < Game.GRIDSIZE/size; i++)
        {
            for (int j = 0; j < Game.GRIDSIZE/size; j++)
            {
                if (grid.isCellAlive(i, j))
                {
                    Rectangle.display(i * size, j * size, size, size);
                }
            }
        }
    }

    public void displayHexagonal(Grid grid)
    {
        glColor3f(0.8f, 0.8f, 0.8f);
        float x = 0;
        float y = 0;
        float a = 10;
        float s = (float) Math.sqrt(3);
        for (int i = 0; i < Game.GRIDSIZE / a; i++)
        {
            for (int j = 0; j < Game.GRIDSIZE / a; j++)
            {

                if (grid.isCellAlive(i, j))
                {
                    Hexagon.display(x+3*j*a/2, y+i*a*s+(j%2)*a*s/2, a);
                }
                else Hexagon.display2(x+3*j*a/2, y+i*a*s+(j%2)*a*s/2, a);
               // window.update();
            }
        }
    }

    public void displayTriangular(Grid grid)
    {
        glColor3f(0.8f, 0.8f, 0.8f);
        float x = 0;
        float y = 0;
        float a = 20;
        float s = (float) Math.sqrt(3);
        for (int i = 0; i < Game.GRIDSIZE; i++)
        {
            for (int j = 0; j < Game.GRIDSIZE; j += 2)
            {
                if (grid.isCellAlive(i, j))
                {
                    Triangle.display(x + j * a/2+(i%2)*a/2, y + i * a * s / 2, a, false);
                } else Triangle.display2(x + j * a/2+(i%2)*a/2, y + i * a * s / 2, a, false);
                if (grid.isCellAlive(i, j + 1))
                {
                    Triangle.display(x + j * a/2-(i%2)*a/2, y + i * a * s / 2, a, true);
                } else Triangle.display2(x + j * a/2-(i%2)*a/2, y + i * a * s / 2, a, true);
            }
        }
    }

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