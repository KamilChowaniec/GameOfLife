public class Game
{
    private static Model model;
    private static View view;
    private static Controller controller;

    public static void main(String[] args)
    {
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);
        controller.run();
    }
}