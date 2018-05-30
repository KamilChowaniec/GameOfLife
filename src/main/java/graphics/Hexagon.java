package graphics;

import static org.lwjgl.opengl.GL11.*;

public class Hexagon extends Shape
{
    float a;
    public Hexagon(float x, float y, float a)
    {
        this.x=x;
        this.y=y;
        this.a=a;
        this.c=new Color(1f,1f,1f,1f);
    }

    @Override
    public void display()
    {
        glColor3f(c.getR(), c.getG(), c.getB());
        glBegin(GL_POLYGON);
        glVertex2f(x, y);
        glVertex2f(x + a, y);
        glVertex2f(x + 3.0f / 2 * a, y + (float)Math.sqrt(3)*a / 2.0f);
        glVertex2f(x + a, y + (float)Math.sqrt(3)*a);
        glVertex2f(x, y + (float)Math.sqrt(3)*a);
        glVertex2f(x - 0.5f*a, y + (float)Math.sqrt(3)*a / 2.0f);
        glEnd();
    }
}
