package graphics;

import static org.lwjgl.opengl.GL11.*;

public class Triangle extends Shape
{
    float a;

    public Triangle(float x, float y, float a)
    {
        this.x = x;
        this.y = y;
        this.a = a;
        this.c = new Color(1f, 1f, 1f, 1f);
    }

    @Override
    public void display()
    {
        display(x, y, a, false, false);
    }

    public static void display(float x, float y, float a, boolean upsideDown, boolean filled)
    {
        if (filled) glBegin(GL_TRIANGLES);
        else glBegin(GL_LINE_LOOP);
        if (upsideDown)
        {
            glVertex2f(x - a / 2, y);
            glVertex2f(x + a / 2, y);
            glVertex2f(x, y + a * (float) Math.sqrt(3) / 2);

        } else
        {
            glVertex2f(x, y);
            glVertex2f(x + a / 2, y + a * (float) Math.sqrt(3) / 2);
            glVertex2f(x - a / 2, y + a * (float) Math.sqrt(3) / 2);
        }
        glEnd();
    }

    private static void display(float x, float y, float a, boolean upsideDown, char side)
    {
        if(upsideDown)
        {
            switch (side)
            {
                case 'B':
                    glBegin(GL_LINES);
                    glVertex2f(x - a / 2, y);
                    glVertex2f(x + a / 2, y);
                    glEnd();
                    break;
                case 'L':
                    glBegin(GL_LINES);
                    glVertex2f(x, y + a * (float) Math.sqrt(3) / 2);
                    glVertex2f(x - a / 2, y);
                    glEnd();
                    break;
                case 'R':
                    glBegin(GL_LINES);
                    glVertex2f(x + a / 2, y);
                    glVertex2f(x, y + a * (float) Math.sqrt(3) / 2);
                    glEnd();
                    break;
            }
        }else
        {
            switch (side)
            {
                case 'B':
                    glBegin(GL_LINES);
                    glVertex2f(x - a / 2, y + a * (float) Math.sqrt(3) / 2);
                    glVertex2f(x + a / 2, y + a * (float) Math.sqrt(3) / 2);
                    glEnd();
                    break;
                case 'L':
                    glBegin(GL_LINES);
                    glVertex2f(x - a / 2, y + a * (float) Math.sqrt(3) / 2);
                    glVertex2f(x, y);
                    glEnd();
                    break;
                case 'R':
                    glBegin(GL_LINES);
                    glVertex2f(x, y);
                    glVertex2f(x + a / 2, y + a * (float) Math.sqrt(3) / 2);
                    glEnd();
                    break;
            }

        }
    }



    public static void displaySelected(float x, float y, float a, float cellWidth, float cellHeight, int iSelection, int jSelection, int widthSelection, int heightSelection)
    {


        // U
//        for (int is = iSelection+1  ; is < iSelection + widthSelection-1 ; is++) // U
//            if ((is % 2) > 0) Triangle.display(x + is * cellWidth + (jSelection % 2) * cellWidth, y + jSelection * cellHeight, a, (is % 2) > 0, 'B');
//        // D
//        for (int is = iSelection +1 ; is < iSelection + widthSelection -1; is++) // U
//            if ((is % 2) > 0) Triangle.display(x + is * cellWidth + ((jSelection+heightSelection) % 2) * cellWidth, y + (jSelection+heightSelection) * cellHeight, a, (is % 2) > 0, 'B');


        // D
   //     for (int is = iSelection+1 ; is < iSelection + widthSelection ; is+=2) // U
     //       if (!((is % 2) > 0))
          //      Triangle.display(x + is * cellWidth + (jSelection % 2) * cellWidth, y + (jSelection+heightSelection-1) * cellHeight, a, (is % 2) > 0, 'B');

/*

        Triangle.display(x + iSelection * cellWidth - (js % 2) * cellWidth, y + js * cellHeight, a, (iSelection % 2) > 0, (js % 2) > 0 ? 'R' : 'L');
        // R
        Triangle.display(x + (iSelection+widthSelection) * cellWidth - (js % 2) * cellWidth, y + js * cellHeight, a, ((iSelection+widthSelection) % 2) > 0, (js % 2) > 0 ? 'R' : 'L');

*/
        // L U
      //  if (((iSelection%2==0)&&(jSelection%2==1)) || ((iSelection%2==1)&&(jSelection%2==0)))
      //      Triangle.display(x + iSelection * cellWidth + (jSelection % 2) * cellWidth, y + jSelection * cellHeight, a, (iSelection % 2) > 0, 'B');
//        if((iSelection%2==1)&&(jSelection%2==1))
//            Triangle.display(x + (iSelection-1) * cellWidth + (jSelection % 2) * cellWidth, y + jSelection * cellHeight, a, ((iSelection-1) % 2) > 0, 'B');
//
//        if(iSelection%2==1)
//            Triangle.display(x + (iSelection) * cellWidth + (jSelection % 2) * cellWidth, y + jSelection * cellHeight, a, (iSelection % 2) > 0, 'B');


        if(((iSelection+jSelection) % 2) == 0 && iSelection%2==1) {
            //if((((iSelection+widthSelection+jSelection+heightSelection) % 2) == 1)) {
            glBegin(GL_LINES);
            glVertex2f(x + iSelection * cellWidth, y + jSelection * cellHeight );
            glVertex2f(x + (iSelection + widthSelection) * cellWidth - ((((iSelection+widthSelection)%2))==0?a/2:0), y + jSelection * cellHeight );
            glEnd();
            //}
        }
        else if(((iSelection+jSelection) % 2) == 0 && iSelection%2==0){
            glBegin(GL_LINES);
            glVertex2f(x + iSelection * cellWidth, y + jSelection * cellHeight );
            glVertex2f(x + (iSelection + widthSelection) * cellWidth + ((((iSelection+widthSelection)%2))==0?a/2:0) - a/2, y + jSelection * cellHeight );
            glEnd();
        }
        else if(((iSelection+jSelection) % 2) == 1 && iSelection%2==1) {
            //if((((iSelection+widthSelection+jSelection+heightSelection) % 2) == 1)) {
            glBegin(GL_LINES);
            glVertex2f(x + iSelection * cellWidth - a/2, y + jSelection * cellHeight );
            glVertex2f(x + (iSelection + widthSelection) * cellWidth - ((((iSelection+widthSelection)%2))==1?a/2:0), y + jSelection * cellHeight );
            glEnd();
            //}
        }
        else if(((iSelection+jSelection) % 2) == 1 && iSelection%2==0){
            glBegin(GL_LINES);
            glVertex2f(x + iSelection * cellWidth - a/2, y + jSelection * cellHeight );
            glVertex2f(x + (iSelection + widthSelection) * cellWidth + ((((iSelection+widthSelection)%2))==1?a/2:0) - a/2, y + jSelection * cellHeight );
            glEnd();
        }


        if(((iSelection+jSelection) % 2) == 0 && iSelection%2==1) {
            //if((((iSelection+widthSelection+jSelection+heightSelection) % 2) == 1)) {
            glBegin(GL_LINES);
            glVertex2f(x + iSelection * cellWidth, y + (jSelection + heightSelection) * cellHeight );
            glVertex2f(x + (iSelection + widthSelection) * cellWidth - ((((iSelection+widthSelection)%2))==0?a/2:0), y + (jSelection + heightSelection) * cellHeight );
            glEnd();
            //}
        }
        else if(((iSelection+jSelection) % 2) == 0 && iSelection%2==0){
            glBegin(GL_LINES);
            glVertex2f(x + iSelection * cellWidth, y + (jSelection + heightSelection) * cellHeight );
            glVertex2f(x + (iSelection + widthSelection) * cellWidth + ((((iSelection+widthSelection)%2))==0?a/2:0) - a/2, y + (jSelection + heightSelection) * cellHeight );
            glEnd();
        }
        else if(((iSelection+jSelection) % 2) == 1 && iSelection%2==1) {
            //if((((iSelection+widthSelection+jSelection+heightSelection) % 2) == 1)) {
            glBegin(GL_LINES);
            glVertex2f(x + iSelection * cellWidth - a/2, y + (jSelection + heightSelection) * cellHeight );
            glVertex2f(x + (iSelection + widthSelection) * cellWidth - ((((iSelection+widthSelection)%2))==1?a/2:0), y + (jSelection + heightSelection) * cellHeight );
            glEnd();
            //}
        }
        else if(((iSelection+jSelection) % 2) == 1 && iSelection%2==0){
            glBegin(GL_LINES);
            glVertex2f(x + iSelection * cellWidth - a/2, y + (jSelection + heightSelection) * cellHeight );
            glVertex2f(x + (iSelection + widthSelection) * cellWidth + ((((iSelection+widthSelection)%2))==1?a/2:0) - a/2, y + (jSelection + heightSelection) * cellHeight );
            glEnd();
        }

        for (int js = jSelection; js < jSelection + heightSelection; js++)
        {
            // L
            Triangle.display(x + iSelection * cellWidth - (js % 2) * cellWidth, y + js * cellHeight, a, (iSelection % 2) > 0, (js % 2) > 0 ? 'R' : 'L');
            // R
            Triangle.display(x + (iSelection+widthSelection) * cellWidth - (js % 2) * cellWidth, y + js * cellHeight, a, ((iSelection+widthSelection) % 2) > 0, (js % 2) > 0 ? 'R' : 'L');

        }

    }
}
