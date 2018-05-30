package graphics;

class Color
{
    private float r,g,b,a;
    Color(float r, float g, float b, float a)
    {
        if(r>1.0f) this.r=1.0f;
        else if(r<0.0f) this.r=0.0f;
        else this.r=r;
        if(g>1.0f) this.g=1.0f;
        else if(g<0.0f) this.g=0.0f;
        else this.g=g;
        if(b>1.0f) this.b=1.0f;
        else if(b<0.0f) this.b=0.0f;
        else this.b=b;
        if(a>1.0f) this.a=1.0f;
        else if(a<0.0f) this.a=0.0f;
        else this.a=a;
    }

    float getA()
    {
        return a;
    }

    float getB()
    {
        return b;
    }

    float getG()
    {
        return g;
    }

    float getR()
    {
        return r;
    }
    void setR(float r)
    {
        this.r=r;
    }
    void setG(float g)
    {
        this.g=g;
    }
    void setB(float b)
    {
        this.b=b;
    }
    void setA(float a)
    {
        this.a=a;
    }
}
