import graphics.Rectangle;

public class Checkbox implements Positionable
{
    private int x,y;
    private int size;
    private boolean state;
    private int color;
    private boolean focused;

    public Checkbox(int x, int y, int size )
    {
        this.x=x;
        this.y=y;
        this.size=size;
        this.state=false;
        if(size<10) this.size =10;
    }

    public void draw()
    {
        Rectangle.display(x,y,size,size,false);
        if(state) Rectangle.display(x+3,y+3,size-7,size-7,true);
    }

    public boolean isFocused(int mouseX, int mouseY)
    {
        return (mouseX > x && mouseX < (x + size) && mouseY > y && mouseY < (y + size));
    }

    public boolean isClicked() {
        return state;
    }

    public void changeState() {
        state=!state;
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

}