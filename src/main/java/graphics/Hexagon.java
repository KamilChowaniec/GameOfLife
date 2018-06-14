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
        /*1*/
        glVertex2f(x, y);
        /*2*/
        glVertex2f(x + a, y);
        /*3*/
        glVertex2f(x + 3.0f / 2 * a, y + (float) Math.sqrt(3) * a / 2.0f);
        /*4*/
        glVertex2f(x + a, y + (float) Math.sqrt(3) * a);
        /*5*/
        glVertex2f(x, y + (float) Math.sqrt(3) * a);
        /*6*/
        glVertex2f(x - 0.5f * a, y + (float) Math.sqrt(3) * a / 2.0f);
        glEnd();
    }

    private static void display(float x, float y, float a, char side)
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

    public static void displaySelected(float x, float y, float a, float cellWidth, float cellHeight, int iSelection, int jSelection, int widthSelection, int heightSelection)
    {
        // L U
        Hexagon.display(x + iSelection * cellWidth, y + (jSelection) * cellHeight + (iSelection % 2) * cellHeight / 2, a, '5');
        Hexagon.display(x + iSelection * cellWidth, y + (jSelection) * cellHeight + (iSelection % 2) * cellHeight / 2, a, '6');
        Hexagon.display(x + iSelection * cellWidth, y + (jSelection) * cellHeight + (iSelection % 2) * cellHeight / 2, a, '1');
        if (iSelection % 2 == 0)
            Hexagon.display(x + iSelection * cellWidth, y + (jSelection) * cellHeight + (iSelection % 2) * cellHeight / 2, a, '2');

        // R U
        Hexagon.display(x + (iSelection + widthSelection - 1) * cellWidth, y + jSelection * cellHeight + ((iSelection + widthSelection - 1) % 2) * cellHeight / 2, a, '1');
        Hexagon.display(x + (iSelection + widthSelection - 1) * cellWidth, y + jSelection * cellHeight + ((iSelection + widthSelection - 1) % 2) * cellHeight / 2, a, '2');
        Hexagon.display(x + (iSelection + widthSelection - 1) * cellWidth, y + jSelection * cellHeight + ((iSelection + widthSelection - 1) % 2) * cellHeight / 2, a, '3');
        if ((iSelection + widthSelection - 1) % 2 == 0)
            Hexagon.display(x + (iSelection + widthSelection - 1) * cellWidth, y + jSelection * cellHeight + ((iSelection + widthSelection - 1) % 2) * cellHeight / 2, a, '6');

        // L D
        Hexagon.display(x + iSelection * cellWidth, y + (jSelection + heightSelection - 1) * cellHeight + (iSelection % 2) * cellHeight / 2, a, '4');
        Hexagon.display(x + iSelection * cellWidth, y + (jSelection + heightSelection - 1) * cellHeight + (iSelection % 2) * cellHeight / 2, a, '5');
        Hexagon.display(x + iSelection * cellWidth, y + (jSelection + heightSelection - 1) * cellHeight + (iSelection % 2) * cellHeight / 2, a, '6');
        if (iSelection % 2 == 1)
            Hexagon.display(x + iSelection * cellWidth, y + (jSelection + heightSelection - 1) * cellHeight + (iSelection % 2) * cellHeight / 2, a, '3');

        // R D
        Hexagon.display(x + (iSelection + widthSelection - 1) * cellWidth, y + (jSelection + heightSelection - 1) * cellHeight + ((iSelection + widthSelection - 1) % 2) * cellHeight / 2, a, '2');
        Hexagon.display(x + (iSelection + widthSelection - 1) * cellWidth, y + (jSelection + heightSelection - 1) * cellHeight + ((iSelection + widthSelection - 1) % 2) * cellHeight / 2, a, '3');
        Hexagon.display(x + (iSelection + widthSelection - 1) * cellWidth, y + (jSelection + heightSelection - 1) * cellHeight + ((iSelection + widthSelection - 1) % 2) * cellHeight / 2, a, '4');
        if ((iSelection + widthSelection - 1) % 2 == 1)
            Hexagon.display(x + (iSelection + widthSelection - 1) * cellWidth, y + (jSelection + heightSelection - 1) * cellHeight + ((iSelection + widthSelection - 1) % 2) * cellHeight / 2, a, '5');


        for (int is = iSelection + (iSelection % 2); is < iSelection + widthSelection + (iSelection % 2) - 1; is += 2) // U
        {
            // U
            Hexagon.display(x + is * cellWidth, y + (jSelection - (is % 2)) * cellHeight + (is % 2) * cellHeight / 2, a, '6');
            Hexagon.display(x + is * cellWidth, y + (jSelection - (is % 2)) * cellHeight + (is % 2) * cellHeight / 2, a, '1');
            Hexagon.display(x + is * cellWidth, y + (jSelection - (is % 2)) * cellHeight + (is % 2) * cellHeight / 2, a, '2');
            if (is != (iSelection + widthSelection + (iSelection % 2) - 2))
                Hexagon.display(x + (is + 1) * cellWidth, y + (jSelection - ((is + 1) % 2)) * cellHeight + ((is + 1) % 2) * cellHeight / 2, a, '4');
        }

        for (int is = iSelection + (iSelection % 2); is < iSelection + widthSelection; is += 2) // D
        {
            // D
            if (is > iSelection + (iSelection % 2))
                Hexagon.display(x + is * cellWidth, y + ((jSelection + heightSelection) - (is % 2)) * cellHeight + (is % 2) * cellHeight / 2, a, '6');
            Hexagon.display(x + is * cellWidth, y + ((jSelection + heightSelection) - (is % 2)) * cellHeight + (is % 2) * cellHeight / 2, a, '1');
            if (is != (iSelection + widthSelection - 1))
                Hexagon.display(x + is * cellWidth, y + ((jSelection + heightSelection) - (is % 2)) * cellHeight + (is % 2) * cellHeight / 2, a, '2');
            if (is != (iSelection + widthSelection - 1))
                Hexagon.display(x + (is + 1) * cellWidth, y + ((jSelection + heightSelection) - ((is + 1) % 2)) * cellHeight + ((is + 1) % 2) * cellHeight / 2, a, '4');
        }


        for (int js = jSelection; js < jSelection + heightSelection; js++)
        {
            // L
            Hexagon.display(x + iSelection * cellWidth, y + js * cellHeight + (iSelection % 2) * cellHeight / 2, a, '5');
            Hexagon.display(x + iSelection * cellWidth, y + js * cellHeight + (iSelection % 2) * cellHeight / 2, a, '6');
            // R
            Hexagon.display(x + (iSelection + widthSelection - 1) * cellWidth, y + js * cellHeight + ((iSelection + widthSelection - 1) % 2) * cellHeight / 2, a, '2');
            Hexagon.display(x + (iSelection + widthSelection - 1) * cellWidth, y + js * cellHeight + ((iSelection + widthSelection - 1) % 2) * cellHeight / 2, a, '3');
        }

    }
}


