package graphics.Input;

import org.lwjgl.glfw.GLFWKeyCallback;
import static org.lwjgl.glfw.GLFW.*;

public class KeyboardHandler extends GLFWKeyCallback
{
    public static boolean[] keys = new boolean[GLFW_KEY_LAST];
    @Override
    public void invoke(long window, int key, int scancode, int action, int mods)
    {
        keys[key] = action != GLFW_RELEASE;

    }
    // returns true if a given key is pressed
    public static boolean isKeyDown(int keycode)
    {
        return keys[keycode];
    }
}