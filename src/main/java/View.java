import graphics.*;
import java.util.Vector;

import static org.lwjgl.opengl.GL11.*;

public class View
{
    private Window window;
    private Button buttons;
    private Checkbox checkboxes;
    private Vector<Shape> shapes;

    public View()
    {
        window = new Window(1280,720,"GOL");
        //TODO implement me

        shapes = new Vector<Shape>();
        shapes.add(new Rectangle(100,100,100,100));
        shapes.add(new Rectangle(105,105,90,90));
        shapes.add(new Hexagon(300,300,100));
        shapes.add(new Circle(200,200,50));
        shapes.add(new Triangle(500,500,100));
    }

    public double[] getMousePosition()
    {
        return window.getMousePosition();
    }
    public boolean[] getKeys()
    {
        return window.getKeys();
    }


    public void display()
    {
        // TODO implement me
        window.clear();
        // TODO display everything here: window.display(something); example below
        shapes.elementAt(1).setColor(0,0,0,1);
        shapes.elementAt(2).setColor(0.5f,0.5f,0.5f,0.5f);


        for (Shape s : shapes)
            window.display(s);

        window.update();
    }

    public boolean shouldRun()
    {
        return window.isOpen();
    }
}