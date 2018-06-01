import graphics.Color;
public class CellProperties {

    private int lived;
    private Color color;

    public CellProperties() {
        lived = 0;
        color = new Color(1,1,1,1);
    }

    public void setLived(int lived) {
        this.lived = lived;
    }

    public void incLived(){
        lived++;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getLived() {
        return lived;
    }

    public Color getColor() {
        return color;
    }
}