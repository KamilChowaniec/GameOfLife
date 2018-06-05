package graphics.Input;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardHandler extends GLFWKeyCallback {
    private static boolean[] Clicked = new boolean[GLFW_KEY_LAST+1];
    public static boolean[] keys = new boolean[GLFW_KEY_LAST+1];

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if(key<0 || key>=GLFW_KEY_LAST) return;
        Clicked[key] = action == GLFW_PRESS;
        keys[key] = action != GLFW_RELEASE;
    }

    // returns true if a given key is pressed
    public static boolean isKeyDown(int keycode) {
        return keys[keycode];
    }

    public static boolean isKeyClicked(int keycode) {
        return (keys[keycode] && Clicked[keycode]);
    }

    public static void clear() {
        for (int i = 0; i < GLFW_KEY_LAST+1; i++) Clicked[i] = false;
    }
}