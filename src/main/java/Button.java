import graphics.Color;
import graphics.Rectangle;
import graphics.Text;

import static org.lwjgl.opengl.GL11.glColor3f;

public class Button implements Positionable {
    private int x, y, width, height;
    private String text;
    private Color color;
    private boolean focused;
    private Text t;
    private ButtonHandler handler;

    public Button(int x, int y, int width, int height, String text, ButtonHandler handler, float r, float g, float b) {
        color = new Color(r, g, b, 1);
        this.handler = handler;
        focused = false;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        t = new Text(x, y + height / 2 + (int) Text.getFontHeight() / 2, text, 1.0f, 1f, 1);
    }

    public boolean isFocused(int mx, int my) {
        return focused = (mx > x && mx < x + width && my > y && my < y + height);
    }

    public void setHandler(ButtonHandler handler) {
        this.handler = handler;
    }

    public boolean isFocused() {
        return focused;
    }

    public void press() {
        handler.invoke();
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setColor(float r, float g, float b) {
        this.color.setR(r);
        this.color.setG(g);
        this.color.setB(b);
    }

    public String getText() {
        return text;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        t.setX(x);
        t.setY(y + height / 2 + (int) Text.getFontHeight() / 2);
    }

    public void display() {
        glColor3f(color.getR(), color.getG(), color.getB());
        Rectangle.display(x, y, width, height, true);
        t.display();
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }
}