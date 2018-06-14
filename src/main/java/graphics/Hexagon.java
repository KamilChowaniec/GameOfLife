package graphics;

import static org.lwjgl.opengl.GL11.*;

public class Hexagon extends Shape
{
    float a;

    public Hexagon(float x, float y, float a)
    {
        this.x = x;
        this.y = y;
        this.a = a;
        this.c = new Color(1f, 1f, 1f, 1f);
    }

    @Override
    public void display()
    {
        glColor3f(c.getR(), c.getG(), c.getB());
        display(x, y, a, false);
    }


    public static void display(float x, float y, float a, boolean filled)
    {
//        glEnable( GL_LINE_SMOOTH );
//        glEnable( GL_POLYGON_SMOOTH );
//        glHint( GL_LINE_SMOOTH_HINT, GL_NICEST );
//        glHint( GL_POLYGON_SMOOTH_HINT, GL_NICEST );

        if (filled) glBegin(GL_POLYGON);
        else glBegin(GL_LINE_LOOP);
        /*1*/  glVertex2f(x, y);
        /*2*/  glVertex2f(x + a, y);
        /*3*/  glVertex2f(x + 3.0f / 2 * a, y + (float) Math.sqrt(3) * a / 2.0f);
        /*4*/  glVertex2f(x + a, y + (float) Math.sqrt(3) * a);
        /*5*/  glVertex2f(x, y + (float) Math.sqrt(3) * a);
        /*6*/  glVertex2f(x - 0.5f * a, y + (float) Math.sqrt(3) * a / 2.0f);
        glEnd();
    }

    public static void display(float x, float y, float a, char side)
    {
        switch (side)
        {
            case '1':
                glBegin(GL_LINES);
                /*1*/
                glVertex2f(x, y);
                /*2*/
                glVertex2f(x + a, y);
                glEnd();
                break;
            case '2':
                glBegin(GL_LINES);
                /*2*/
                glVertex2f(x + a, y);
                /*3*/
                glVertex2f(x + 3.0f / 2 * a, y + (float) Math.sqrt(3) * a / 2.0f);
                glEnd();
                break;
            case '3':
                glBegin(GL_LINES);
                /*3*/
                glVertex2f(x + 3.0f / 2 * a, y + (float) Math.sqrt(3) * a / 2.0f);
                /*4*/
                glVertex2f(x + a, y + (float) Math.sqrt(3) * a);
                glEnd();
                break;
            case '4':
                glBegin(GL_LINES);
                /*4*/
                glVertex2f(x + a, y + (float) Math.sqrt(3) * a);
                /*5*/
                glVertex2f(x, y + (float) Math.sqrt(3) * a);
                glEnd();
                break;
            case '5':
                glBegin(GL_LINES);
                /*5*/
                glVertex2f(x, y + (float) Math.sqrt(3) * a);
                /*6*/
                glVertex2f(x - 0.5f * a, y + (float) Math.sqrt(3) * a / 2.0f);
                glEnd();
                break;
            case '6':
                glBegin(GL_LINES);
                /*6*/
                glVertex2f(x - 0.5f * a, y + (float) Math.sqrt(3) * a / 2.0f);
                /*1*/
                glVertex2f(x, y);
                glEnd();
                break;

        }
    }

}
