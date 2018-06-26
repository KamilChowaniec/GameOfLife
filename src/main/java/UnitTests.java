import org.junit.Assert;
import org.junit.Test;
public class UnitTests
{
    @Test
    public void testSquaredAlive()
    {
        Grid o = new Squared();
        boolean[][] rules = new boolean[2][9];
        rules[1][1]=true;
        o.drawOnGrid(5, 4, true);
        o.mechanic(rules);
        Assert.assertEquals(true, o.isCellAlive(5,5));
    }

    @Test
    public void testSquaredDead()
    {
        Grid o = new Squared();
        boolean[][] rules = new boolean[2][9];
        rules[1][0] = true;
        o.drawOnGrid(5, 5, true);
        o.drawOnGrid(5, 6, true);
        o.mechanic(rules);
        Assert.assertEquals(false, o.isCellAlive(5,5));
    }

    @Test
    public void testTriangularAlive()
    {
        Grid o = new Triangular();
        boolean[][] rules = new boolean[2][9];
        rules[1][1]=true;
        rules[1][2]=true;
        o.drawOnGrid(5, 4, true);
        o.drawOnGrid(3, 4, true);
        o.mechanic(rules);
        Assert.assertEquals(true, o.isCellAlive(4,4));
    }

    @Test
    public void testTriangularDead()
    {
        Grid o = new Triangular();
        boolean[][] rules = new boolean[2][9];
        o.drawOnGrid(4, 5, true);
        o.mechanic(rules);
        Assert.assertEquals(false, o.isCellAlive(4,5));
    }

    @Test
    public void testHexagonalAlive()
    {
        Grid o = new Hexagonal();
        boolean[][] rules = new boolean[2][9];
        rules[1][2]=true;
        rules[1][3]=true;
        o.drawOnGrid(5, 4, true);
        o.drawOnGrid(6, 4, true);
        o.drawOnGrid(4, 5, true);
        o.mechanic(rules);
        Assert.assertEquals(true, o.isCellAlive(4,4));
    }

    @Test
    public void testHexagonalDead()
    {
        Grid o = new Hexagonal();
        boolean[][] rules = new boolean[2][9];
        rules[0][2]=true;
        rules[0][1]=true;
        o.drawOnGrid(4, 5, true);
        o.drawOnGrid(5, 5, true);
        o.drawOnGrid(5, 4, true);
        o.mechanic(rules);
        Assert.assertEquals(false, o.isCellAlive(4,4));
    }
}