package graphics;

import static org.lwjgl.opengl.GL11.*;

public class Line
{
    public static void drawOneLine(float x1, float y1, float x2, float y2)
    {
        glBegin(GL_LINES);
        glVertex2f((x1), (y1));
        glVertex2f((x2), (y2));
        glEnd();
    }
}
