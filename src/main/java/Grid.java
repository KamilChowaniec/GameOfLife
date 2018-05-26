public interface Grid {
    Cell[][] cells = null;

    void countNeighbors();

    void draw();

    void drawOnGrind();

    void mechanic();
}