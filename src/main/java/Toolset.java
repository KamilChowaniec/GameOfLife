import graphics.Input.MouseButtonsHandler;
import graphics.Input.MouseHandler;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class Toolset {
    private Tool state;
    private int bIndex;
    private Button[] tools;

    public Toolset() {
        int x = 2, y = 2, width = 400, height = 1076;
        state = Tool.Draw;
        tools = new Button[Tool.values().length];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < tools.length / 2 + (tools.length % 2); j++) {

                int I = i * 2 + j;
                if (I >= tools.length) break;

                tools[I] = new Button(x + i * (width / 2) + (width / 8),
                        y + j * (height / 4) + (height / 16),
                        width / 4,
                        height / 8,
                        Tool.values()[I].toString(),
                        () -> {
                            bIndex = I;
                            setTool(Tool.values()[I]);
                        },
                        0.8f, 0.8f, 0.8f);

            }
        }
    }

    public Tool getTool() {
        return state;
    }

    public void setTool(Tool tool) {
        state = tool;
    }

    public void handleTools() {
        if (MouseButtonsHandler.isKeyClicked(GLFW_MOUSE_BUTTON_LEFT))
            for (int i = 0; i < tools.length; i++)
                if (tools[i].isFocused((int) MouseHandler.xPos(), (int) MouseHandler.yPos())) {
                    tools[bIndex].setColor(0.8f,0.8f,0.8f);
                    tools[i].press();
                    tools[bIndex].setColor(0,0,1);
                }
    }

    public void display() {
        for (Button tool : tools)
            tool.display();
    }
}

//2, 2, 400, 1076