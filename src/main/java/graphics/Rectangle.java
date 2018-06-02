package graphics;

import static org.lwjgl.opengl.GL11.*;

public class Rectangle extends Shape
{
    float width,height;

    public Rectangle(float x, float y, float width, float height)
    {
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.c=new Color(1f,1f,1f,1f);
    }
    @Override
    public void display()
    {
        glColor3f(c.getR(), c.getG(), c.getB());
        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);
        glEnd();
    }
    public static void display(float x, float y, float width, float height)
    {
        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);
        glEnd();
    }
}
