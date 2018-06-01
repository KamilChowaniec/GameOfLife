package graphics.Input;

import org.lwjgl.glfw.GLFWMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.*;

public class MouseButtonsHandler extends GLFWMouseButtonCallback
{
    public static boolean[] buttons = new boolean[2];
    @Override
    public void invoke(long window, int button, int action, int mods)
    {
        buttons[button] = action != GLFW_RELEASE;
    }
    // returns true if a given key is pressed
    public static boolean isKeyDown(int buttoncode)
    {
        return buttons[buttoncode];
    }

    public static boolean[] getMouseButtons()
    {
        return buttons;
    }
}
