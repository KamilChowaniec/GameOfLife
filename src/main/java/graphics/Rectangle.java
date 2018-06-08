package graphics;

import static org.lwjgl.opengl.GL11.*;

public class Rectangle extends Shape
{
    float width, height;

    public Rectangle(float x, float y, float width, float height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.c = new Color(1f, 1f, 1f, 1f);
    }

    @Override
    public void display()
    {
        glColor3f(c.getR(), c.getG(), c.getB());
        display(x, y, width, height, false);
    }

    public static void display(float x, float y, float width, float height, boolean filled)
    {
        if (filled) glBegin(GL_QUADS);
        else glBegin(GL_LINE_LOOP);
        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);
        glEnd();
    }
}
