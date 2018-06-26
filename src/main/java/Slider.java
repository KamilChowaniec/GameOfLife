import graphics.Rectangle;

public class Slider
{
    private int xTrack;
    private int yTrack;
    private int xThumb;
    private int yThumb;
    private int widthTrack;
    private int heightTrack;
    private int widthThumb;
    private int heightThumb;
    private boolean state;

    public Slider(int xTrack, int yTrack, int widthTrack,int heightTrack)
    {
        this.xTrack=xTrack;
        this.yTrack=yTrack;
        this.widthTrack=widthTrack;
        this.heightTrack=heightTrack;
        this.widthThumb=widthTrack/15;
        this.heightThumb=heightTrack+10;
        this.xThumb=xTrack;
        this.yThumb=yTrack-5;
    }

    private Slider(Builder builder)
    {
        this.xTrack=builder.xTrack;
        this.yTrack=builder.yTrack;
        this.xThumb=xTrack;
        this.yThumb=yTrack-(builder.heightThumb-builder.heightTrack)/2;
        this.widthTrack=builder.widthTrack;
        this.heightTrack=builder.heightTrack;
        this.widthThumb=builder.widthThumb;
        this.heightThumb=builder.heightThumb;
    }

    public static class Builder
    {
        private int xTrack;
        private int yTrack;
        private int widthTrack;
        private int heightTrack;
        private int widthThumb;
        private int heightThumb;

        public Builder xTrack(int xTrack)
        {
            this.xTrack=xTrack;
            return this;
        }


        public Builder yTrack(int yTrack)
        {
            this.yTrack=yTrack;
            return this;
        }

        public Builder widthTrack(int widthTrack)
        {
            this.widthTrack=widthTrack;
            return this;
        }

        public Builder heightTrack(int heightTrack)
        {
            this.heightTrack=heightTrack;
            return this;
        }

        public Builder widthThumb(int widthThumb)
        {
            this.widthThumb=widthThumb;
            return this;
        }

        public Builder heightThumb(int heightThumb)
        {
            this.heightThumb=heightThumb;
            return this;
        }

        public Slider build()
        {
            return  new Slider(this);
        }
    }


    public void draw()
    {
        Rectangle.display(xTrack,yTrack,widthTrack,heightTrack,false);
        Rectangle.display(xThumb,yThumb,widthThumb,heightThumb,true);
    }

    public boolean isFocused(int mouseX, int mouseY)
    {
        return (mouseX > xTrack && mouseX < (xTrack + widthTrack) && mouseY > yTrack && mouseY < (yTrack + heightTrack));
    }

    public void slide(int xThumb)
    {
        this.xThumb = xThumb - widthThumb/2;
        if(xThumb<xTrack) this.xThumb=xTrack;
        else if(xThumb>(xTrack+widthTrack-widthThumb)) this.xThumb=xTrack+widthTrack-widthThumb;
    }

    public int getPercent()
    {
        return 100*(xTrack+widthTrack-xThumb)/(widthTrack-widthThumb);
    }

    public void setPercent(int percent){
        xThumb = percent*(widthTrack - widthThumb)/100 + xTrack;
    }

    public void changeState()
    {
        state=!state;
    }

    public boolean state()
    {
        return state;
    }
}