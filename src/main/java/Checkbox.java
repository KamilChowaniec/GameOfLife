import graphics.Rectangle;

import static org.lwjgl.opengl.GL11.glColor3f;

public class Checkbox implements Positionable
{
    private int x,y;
    private int size;
    private boolean state;
    private int color;
    private boolean focused;
    private CheckboxHandler handler;

    public Checkbox(int x, int y, int size, CheckboxHandler handler )
    {
        this.handler = handler;
        this.x=x;
        this.y=y;
        this.size=size;
        this.state=false;
        if(size<10) this.size =10;
    }

    public void draw()
    {
        Rectangle.display(x,y,size,size,false);
        glColor3f(0,0,0);
        Rectangle.display(x-1,y-1,size+2,size+2,false);
        glColor3f(1,1,1);
        Rectangle.display(x-2,y-2,size+4,size+4,false);
        if(state) Rectangle.display(x+1,y+1,size-3,size-2,true);
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

    public void setState(boolean state){
        this.state = state;
    }

    public void press(){
        changeState();
        handler.invoke(state);
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

}