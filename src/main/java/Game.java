public class Game
{
    public static int GRIDSIZE = 1200;
    private Model model;
    private View view;
    private Controller controller;

    Game()
    {
        model = new Model();
        view = new View();
        controller = new Controller(model, view);
        controller.run();
    }

    public static void main(String[] args)
    {
        new Game();
    }
}