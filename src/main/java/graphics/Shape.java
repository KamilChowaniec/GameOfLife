package graphics;

public abstract class Shape implements Displayable
{
    float x, y;
    Color c;
    public void setColor(float r, float g, float b, float a)
    {
        c.setR(r);
        c.setG(g);
        c.setB(b);
        c.setA(a);
    }
}
