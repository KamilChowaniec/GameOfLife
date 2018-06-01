package graphics.Input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class MouseHandler extends GLFWCursorPosCallback
{
    public static double mousePos[]=new double[2];

    @Override
    public void invoke(long window, double xpos, double ypos)
    {
        mousePos[0]=xpos;
        mousePos[1]=ypos;

    }

    public static double[] getMousePosition()
    {
        return mousePos;
    }
}