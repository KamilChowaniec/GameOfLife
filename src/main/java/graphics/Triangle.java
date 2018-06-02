package graphics;

import static org.lwjgl.opengl.GL11.*;

public class Triangle extends Shape
{
    float a;
    public Triangle(float x, float y, float a)
    {
        this.x=x;
        this.y=y;
        this.a=a;
        this.c=new Color(1f,1f,1f,1f);
    }
    @Override
    public void display()
    {
        glBegin(GL_TRIANGLES);
        glVertex2f(x, y);
        glVertex2f(x + a/2, y+a*(float)Math.sqrt(3)/2);
        glVertex2f(x - a/2, y+a*(float)Math.sqrt(3)/2);
        glEnd();
    }

    public static void display(float x, float y, float a, boolean upsideDown)
    {
        glBegin(GL_TRIANGLES);
        if(upsideDown)
        {
            glVertex2f(x-a/2, y);
            glVertex2f(x+a/2,y);
            glVertex2f(x,y+a*(float)Math.sqrt(3)/2);

        }
        else
        {
            glVertex2f(x, y);
            glVertex2f(x + a/2, y+a*(float)Math.sqrt(3)/2);
            glVertex2f(x - a/2, y+a*(float)Math.sqrt(3)/2);
        }
        glEnd();

    }
    public static void display2(float x, float y, float a, boolean upsideDown)
    {
        glBegin(GL_LINE_LOOP);
        if(upsideDown)
        {
            glVertex2f(x-a/2, y);
            glVertex2f(x+a/2,y);
            glVertex2f(x,y+a*(float)Math.sqrt(3)/2);


        }
        else
        {
            glVertex2f(x, y);
            glVertex2f(x + a/2, y+a*(float)Math.sqrt(3)/2);
            glVertex2f(x - a/2, y+a*(float)Math.sqrt(3)/2);
        }
        glEnd();

    }
}
