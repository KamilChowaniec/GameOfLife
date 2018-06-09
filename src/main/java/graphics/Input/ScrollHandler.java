package graphics.Input;

import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWScrollCallbackI;

public class ScrollHandler implements GLFWScrollCallbackI {

    private static double yOffset;
    @Override
    public void invoke(long window, double xoffset, double yoffset) {
        yOffset = yoffset;
    }

    public static double wheelMovement(){
        return yOffset;
    }

    public static void clear(){
        yOffset=0;
    }
}
