package graphics;

import graphics.Input.*;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    long handle = NULL;
    private int width;
    private int height;
    private String title;
    private Vector<Displayable> toDispaly;

    private GLFWKeyCallback keyCallback;
    private GLFWCursorPosCallback mouseCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;

    public Window(int windowWidth, int windowHeight, String windowTitle) {
        init1();
        width = windowWidth;
        height = windowHeight;
        title = windowTitle;
        handle = glfwCreateWindow(width, height, title, NULL, NULL);
        init2();
    }

    private void init1() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
    }

    private void init2() {
        if (handle == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        /* Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(handle, (window, key, scancode, action, mods) ->
        {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });
        */

        glfwSetKeyCallback(handle, keyCallback = new KeyboardHandler());
        glfwSetCursorPosCallback(handle, mouseCallback = new MouseHandler());
        glfwSetMouseButtonCallback(handle, mouseButtonCallback = new MouseButtonsHandler());
        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*
            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(handle, pWidth, pHeight);
            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            // Center the window
            glfwSetWindowPos(
                    handle,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically
        // Make the OpenGL context current
        glfwMakeContextCurrent(handle);
        // Enable v-sync
        glfwSwapInterval(1);
        // Make the window visible
        glfwShowWindow(handle);


        // bindings available for use.
        GL.createCapabilities();
        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, width, height, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
    }

    public void setPosition(int x, int y) {
        glfwSetWindowPos(handle, x, y);
    }

    public boolean isOpen() {
        return !glfwWindowShouldClose(handle);
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
    }

    public void display(Displayable d) {
        d.display();
    }

    public void update() {
        glfwSwapBuffers(handle); // swap the color buffers
        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents();

    }

    public long getHandle() {
        return handle;
    }

    public double[] getMousePosition() {
        return MouseHandler.getMousePosition();
    }
}