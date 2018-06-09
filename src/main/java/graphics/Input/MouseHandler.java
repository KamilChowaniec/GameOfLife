package graphics.Input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class MouseHandler extends GLFWCursorPosCallback {
    public static double mousePos[] = new double[2];
    public static double xrel=0;
    public static double yrel=0;


    @Override
    public void invoke(long window, double xpos, double ypos) {
        xrel = xpos - mousePos[0];
        yrel = ypos - mousePos[1];
        mousePos[0] = xpos;
        mousePos[1] = ypos;
    }

    public static double[] getMousePosition() {
        return mousePos;
    }

    public static double xPos() {
        return mousePos[0];
    }

    public static double yPos() {
        return mousePos[1];
    }

    public static double xRel() {
        return xrel;
    }

    public static double yRel() {
        return yrel;
    }
}
