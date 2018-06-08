import graphics.Color;

public class Button implements Positionable {
    private int x, y, width, height;
    private String text;
    private Color color;
    private boolean focused;

    public Button(int x, int y, int width, int height, String text, Color color) {
        focused = false;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
    }

    public boolean isFocused(int mx, int my) {
        return focused = (mx > x && mx < x + width && my > y && my < y + height);
    }

    public boolean isFocused() {
        return focused;
    }

    public void setColor(Color color) {
        this.color = color;
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
    }



}