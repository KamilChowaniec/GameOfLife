package graphics;

public class Color {
    private float r, g, b, a;

    public Color(float r, float g, float b, float a) {
        if (r > 1.0f) this.r = 1.0f;
        else if (r < 0.0f) this.r = 0.0f;
        else this.r = r;
        if (g > 1.0f) this.g = 1.0f;
        else if (g < 0.0f) this.g = 0.0f;
        else this.g = g;
        if (b > 1.0f) this.b = 1.0f;
        else if (b < 0.0f) this.b = 0.0f;
        else this.b = b;
        if (a > 1.0f) this.a = 1.0f;
        else if (a < 0.0f) this.a = 0.0f;
        else this.a = a;
    }

    public float getA() {
        return a;
    }

    public float getB() {
        return b;
    }

    public float getG() {
        return g;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public void setG(float g) {
        this.g = g;
    }

    public void setB(float b) {
        this.b = b;
    }

    public void setA(float a) {
        this.a = a;
    }
}
