public class Cell
{

    private boolean alive;
    private int lived;
    private int color;

    public Cell()
    {
        alive=false;
        lived=0;
    }

    public void update()
    {
        lived++;
    }



    public boolean isDead()
    {
        return !alive;
    }

    public void changeState()
    {
       alive=!alive;
       lived=0;
    }
}