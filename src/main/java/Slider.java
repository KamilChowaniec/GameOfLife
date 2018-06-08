import graphics.Rectangle;

public class Slider
{
    private int x,y;
    private int xR,yR;
    private int width,height, widthR,heightR;
    private int color;
    private boolean grabbed;

    public Slider(int x, int y, int width,int height)
    {
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.widthR=width/30;
        this.heightR=height+10;
        this.xR=x;
        this.yR=y-5;
    }

    public void draw()
    {
        Rectangle.display(x,y,width,height,false);
        Rectangle.display(xR,yR,widthR,heightR,true);
    }

    public boolean isFocused(int mouseX, int mouseY)
    {
        return (mouseX > x && mouseX < (x + width) && mouseY > y && mouseY < (y + height));
    }

    public void slide(int xR)
    {
        this.xR = xR - widthR/2;
    }

    public int getPercent()
    {
        return 100*(xR-x)/(width-widthR);
    }

}