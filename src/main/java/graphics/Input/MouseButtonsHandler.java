package graphics.Input;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

import static org.lwjgl.glfw.GLFW.*;


public class MouseButtonsHandler extends GLFWMouseButtonCallback {
    private static boolean[] Clicked = new boolean[GLFW_MOUSE_BUTTON_LAST];
    public static boolean[] buttons = new boolean[GLFW_MOUSE_BUTTON_LAST];

    @Override
    public void invoke(long window, int button, int action, int mods) {
        Clicked[button] = action == GLFW_PRESS;
        buttons[button] = action != GLFW_RELEASE;
    }

    // returns true if a given key is pressed
    public static boolean isKeyDown(int buttoncode) {
        return buttons[buttoncode];
    }

    public static boolean isKeyClicked(int buttoncode) {
        return (buttons[buttoncode] && Clicked[buttoncode]);
    }

    public static void clear(){
        for(int i=0;i<GLFW_MOUSE_BUTTON_LAST;i++) Clicked[i] = false;
    }
}
