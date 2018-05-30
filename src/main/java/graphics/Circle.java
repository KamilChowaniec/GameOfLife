package graphics;

import static org.lwjgl.opengl.GL11.*;

public class Circle extends Shape
{
    float r;

    public Circle(float x, float y, float r)
    {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    @Override
    public void display()
    {
        int i;
        int lineAmount = 100; //# of triangles used to draw circle

        float twicePi = 2.0f * (float) Math.PI;

        glBegin(GL_LINE_LOOP);
        for (i = 0; i <= lineAmount; i++)
        {
            glVertex2f(x + (r * (float) Math.cos(i * twicePi / lineAmount)), y + (r * (float) Math.sin(i * twicePi / lineAmount)));
        }
        glEnd();
    }
}