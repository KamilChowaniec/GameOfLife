import graphics.Rectangle;

public enum Area {
    grid(new Rectangle(404, 34, 1114, 1044)),
    preview(new Rectangle(1520, 2, 400, 400)),
    clipboard(new Rectangle(1520, 540 - 135, 400, 537 + 135)),
    tools(new Rectangle(2, 2, 400, 1076)),
    cards(new Rectangle(404, 2, 1520 - 406, 30));

    Rectangle area;

    Area(Rectangle rect) {
        area = rect;
    }

    public int getX() {
        return (int) area.getX();
    }


    public int getY() {
        return (int) area.getY();
    }


    public int getWidth() {
        return (int) area.getWidth();
    }


    public int getHeight() {
        return (int) area.getHeight();
    }

    public boolean isFocused(int mx, int my){
        return (mx > area.getX() && mx < area.getX() + area.getWidth() && my > area.getY() && my < area.getY() + area.getHeight());
    }
}